# NubankUrlShortener

App Android simples para encurtar URLs.

## O que o app faz

1. Recebe uma URL, como `nubank.com.br`.
2. Ajusta para um formato valido, como `https://nubank.com.br`.
3. Envia a URL para a API.
4. Mostra o alias gerado na lista de links recentes.
5. Permite copiar o link retornado pela API.
6. Permite abrir a URL original que ja veio na resposta.
7. Mostra erro amigavel quando a URL e invalida ou quando a API falha.

A API usada para encurtar e:

```text
https://url-shortener-server.onrender.com/api/alias
```

Observacao: a API gera um alias curto, mas o dominio publico da API é longo. Por isso o link completo retornado pela API pode ficar maior do que algumas URLs originais.

O que e curto no retorno e o alias:

```text
609299506
```

No app, o botao **Abrir URL** abre a URL original que ja veio na resposta do encurtamento.

## Arquitetura

O projeto usa Single Activity, Jetpack Compose, MVVM com `StateFlow`, Koin, Ktor e modulos separados por responsabilidade.

Na camada de apresentação, estou utilizando MVVM:

- a View é a tela em Compose;
- o ViewModel recebe as ações do usuário;
- o ViewModel chama os use cases;
- o estado da tela fica em um `ShortenerUiState`;
- a tela observa esse estado e se redesenha.

```text
:app
:navigation
:designsystem
:observability
:network
:feature:splash
:feature:shortener:domain
:feature:shortener:data
:feature:shortener:presentation
```

Hoje a divisao é:

- modulos Android: `:app`, `:navigation`, `:designsystem`, `:feature:splash`, `:feature:shortener:presentation`;
- modulos Kotlin puros: `:observability`, `:network`, `:feature:shortener:domain`, `:feature:shortener:data`.

Isso ajuda a manter regra, dados, rede e tela separados.

Tambem estou utilizando Clean Architecture dentro da feature:

```text
  -> presentation
  -> domain
  -> data
```

A tela depende do ViewModel, o ViewModel depende do use case, e o use case depende de uma abstração de repository.

## Modulos

### `:app`

Entrada do aplicativo.

Responsável por:

- abrir a `MainActivity`;
- aplicar o tema;
- iniciar o Koin;
- configurar o Sentry quando existir DSN;
- chamar o `NuNavHost`.

### `:navigation`

Grafo de navegação do app.

Responsável por:

- definir as rotas principais;
- iniciar pela splash;
- navegar da splash para o encurtador;
- manter o `app` sem chamar telas de feature diretamente.

Hoje ele registra:

- `splash`;
- `shortener`.

### `:feature:splash`

Feature da splash.

Aqui ficam:

- tela de abertura;
- textos da splash;
- tempo de exibicao;
- callback para avisar que a navegacao pode continuar.

### `:feature:shortener:presentation`

Parte visual e estado da tela.

Aqui ficam:

- tela em Compose;
- `ShortenerViewModel`;
- estado da tela com `StateFlow`;
- mensagens que aparecem para o usuario;
- ligacao entre a tela e o use case.

### `:feature:shortener:domain`

Regras principais.

Aqui o app decide:

- se a URL esta vazia;
- se a URL é válida;
- se precisa adicionar `https://`;
- se o resultado e sucesso;
- se deve retornar erro quando a API falha.

Esse modulo não conhece Compose, Android, Ktor ou Sentry.

### `:feature:shortener:data`

Dados da feature.

Responsável por:

- chamar o remoto por meio de `AliasRemoteDataSource`;
- transformar a resposta da API em `ShortenedUrl`;
- devolver `Result.failure` quando a API falha.

O repository depende de uma abstração remota, então conseguimos testar sem Ktor e sem rede real.

### `:network`

Configuração de rede.

Centraliza:

- base URL;
- timeouts;
- cliente Ktor;
- engine OkHttp;
- serializacao JSON;
- logs completos de request e response;
- erros de rede.

### `:designsystem`

Componentes visuais reutilizaveis.

Aqui ficam:

- tema;
- cores;
- tipografia;
- componentes;

### `:observability`

Logs e provedores de observabilidade.

O app usa `AppLogger` nas features. Esse contrato não conhece Sentry, Datadog ou New Relic.

Dentro do modulo, o log vira um `LogEvent` com:

- nivel;
- mensagem;
- erro opcional;
- atributos simples, como `alias`, `path` ou `errorType`.

Depois o `CompositeAppLogger` envia esse evento para destinos configurados.

Hoje existem:

- `ConsoleReport`: escreve logs no console com a tag `NuLogs`;
- `SentryReport`: envia breadcrumbs e erros para o Sentry.

Se amanha precisarmos trocar ou adicionar New Relic/Datadog, a ideia é criar outro report de log e trocar a configuracão no Koin:

```kotlin
CompositeAppLogger(
    reports = listOf(
        ConsoleReport(),
        SentryReport(),
    ),
)
```

A inicializacao Android do Sentry fica no `:app`. O DSN entra por `local.properties` ou variável de ambiente.

## Fluxo

Quando entra:


```text
App
  -> Navigation
  -> Splash
  -> Shortener
      -> Screen
      -> ViewModel
      -> UseCase
      -> Repository
      -> RemoteDataSource
      -> Network
      -> API
```

Quando volta:

```text
API funcionou
  -> mapeia resposta para ShortenedUrl
  -> adiciona no historico
  -> mostra mensagem de sucesso

API falhou
  -> mostra mensagem amigável

URL invalida
  -> nao chama a API
  -> mostra mensagem amigavel
```

## Bibliotecas utilizadas nesse app

- Jetpack Compose: telas.
- Navigation Compose: navegacao entre features.
- Material 3: base visual.
- Koin: injecao de dependencias e ViewModel.
- Ktor: chamadas HTTP.
- OkHttp: engine do Ktor.
- Kotlinx Serialization: JSON.
- Sentry: observabilidade.
- JUnit: testes unitarios.
- Turbine: testes de `StateFlow` no ViewModel.
- Compose UI Test: teste instrumentado basico da tela.
- Maestro: testes E2E/UI por YAML.

## Sentry

O DSN do Sentry nao fica no codigo.

Configure no `local.properties`:

```properties
SENTRY_DSN=https://seu-dsn@sentry.io/projeto
```

Ou por variavel de ambiente:

```bash
export SENTRY_DSN="https://seu-dsn@sentry.io/projeto"
```

Se o DSN estiver vazio, o app nao inicia o Sentry.

## Testes

### Unitarios de observability

Arquivo:

```text
observability/src/test/java/.../CompositeAppLoggerTest.kt
```

Cobre:

- log de info enviado para todos os destinos;
- log de erro mantendo `Throwable` e atributos;
- falha em um destino sem impedir envio para os outros destinos.

Rodar:

```bash
./gradlew :observability:test
```

### Unitarios de domain

Arquivo:

```text
feature/shortener/domain/src/test/java/.../ShortenUrlUseCaseTest.kt
```

Cobre:

- URL sem `https://` e normalizada antes de chamar o repository;
- falha do repository retornando `ServiceUnavailable`;
- URL invalida sem chamar o repository.

Rodar:

```bash
./gradlew :feature:shortener:domain:test
```

### Unitarios de data

Arquivo:

```text
feature/shortener/data/src/test/java/.../UrlShortenerRepositoryImplTest.kt
```

Cobre:

- sucesso remoto mapeando `AliasResponse` para `ShortenedUrl`;
- falha remota retornando `Result.failure`;
- `_links.self` virando `originalUrl`;
- `_links.short` virando `shortUrl`;
- chamada ao logger quando existe erro remoto.

Rodar:

```bash
./gradlew :feature:shortener:data:test
```

### Unitarios de presentation

Arquivo:

```text
feature/shortener/presentation/src/test/java/.../ShortenerViewModelTest.kt
```

Usa Turbine para observar o `StateFlow`.

Cobre:

- sucesso ao encurtar, com loading, historico e mensagem;
- falha do repository mostrando mensagem de indisponibilidade;
- URL invalida sem chamar repository;
- abrir URL usando a URL original ja disponivel no estado.

Rodar:

```bash
./gradlew :feature:shortener:presentation:testDebugUnitTest
```

### Teste instrumentado

Arquivo:

```text
app/src/androidTest/java/.../MainActivityTest.kt
```

Cobre:

- app abre;
- splash termina;
- tela principal aparece;
- titulo e secao de links recentes aparecem.

Gerar APK de teste:

```bash
./gradlew :app:assembleDebugAndroidTest
```

### Maestro

Flows:

```text
.maestro/launch_screen.yaml
.maestro/empty_url_error.yaml
.maestro/invalid_url_error.yaml
.maestro/shorten_success_real_api.yaml
```

Cobre:

- tela inicial depois da splash;
- estado vazio;
- erro de URL vazia;
- erro de URL invalida;
- smoke de sucesso com API real.

O flow `shorten_success_real_api.yaml` depende da API real e pode falhar se a API estiver fria, lenta ou fora.

Instalar o app debug:

```bash
./gradlew :app:installDebug
```

Rodar flows estaveis:

```bash
maestro test .maestro/launch_screen.yaml
maestro test .maestro/empty_url_error.yaml
maestro test .maestro/invalid_url_error.yaml
```

Rodar o smoke com API real:

```bash
maestro test .maestro/shorten_success_real_api.yaml
```

## Comandos principais

Gerar o app:

```bash
./gradlew :app:assembleDebug
```

Rodar todos os testes unitarios atuais:

```bash
./gradlew :observability:test :feature:shortener:domain:test :feature:shortener:data:test :feature:shortener:presentation:testDebugUnitTest
```

Rodar validacao principal:

```bash
./gradlew :app:assembleDebug :observability:test :feature:shortener:domain:test :feature:shortener:data:test :feature:shortener:presentation:testDebugUnitTest :app:assembleDebugAndroidTest
```

Rodar lint:

```bash
./gradlew lintDebug
```

## Guards do projeto

- Texto visivel para o usuario fica em `strings.xml`.
- Texto tecnico de log pode ficar no codigo.
- Componentes visuais compartilhados ficam no `:designsystem`.
- Configuracao de rede fica no `:network`.
- Logs e Sentry ficam no `:observability`.
- Navegacao principal fica no `:navigation`.
- Regras ficam no `:domain`.
- Dados ficam no `:data`.
- Tela e estado ficam no `:presentation`.
- Testes seguem o padrao Given, When, Then.
