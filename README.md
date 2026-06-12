# Shortener

App Android simples para encurtar URLs.

## O que o app faz

1. Recebe uma URL, como `example.com`.
2. Ajusta para um formato valido, como `https://example.com`.
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

## Como rodar o projeto

### Requisitos

- Android Studio recente;
- JDK 17;
- Android SDK instalado;
- device fisico ou emulador com internet.

O projeto usa Gradle Wrapper, entao nao precisa instalar Gradle manualmente.

### Pelo Android Studio

1. Descompacte o `.zip`.
2. Abra a pasta do projeto no Android Studio.
3. Aguarde o Gradle Sync terminar.
4. Selecione a configuracao `app`.
5. Escolha um device fisico ou emulador.
6. Clique em **Run**.

### Pelo terminal

Gerar o APK debug:

```bash
./gradlew :app:assembleDebug
```

Instalar no device conectado:

```bash
./gradlew :app:installDebug
```

Rodar os testes unitarios principais:

```bash
./gradlew :observability:test :feature:shortener:impl:testDebugUnitTest
```

Rodar a validacao principal:

```bash
./gradlew :app:assembleDebug :observability:test :feature:shortener:impl:testDebugUnitTest :app:assembleDebugAndroidTest
```

### Sentry opcional

O app roda sem configurar Sentry.

Se quiser testar envio para Sentry, crie um arquivo `local.properties` na raiz do projeto e adicione:

```properties
SENTRY_DSN=https://seu-dsn@sentry.io/projeto
```

Esse arquivo nao deve ser enviado junto com credenciais reais.

### Observacao sobre a API

O app usa uma API publica hospedada no Render. Em alguns momentos ela pode demorar para responder por cold start. Quando isso acontece, o app mostra uma mensagem amigavel de erro.

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
:feature:shortener:api
:feature:shortener:impl
```

Hoje a divisao é:

- modulos Android: `:app`, `:navigation`, `:designsystem`, `:feature:splash`, `:feature:shortener:impl`;
- modulos Kotlin puros: `:observability`, `:network`, `:feature:shortener:api`.

Isso ajuda a manter contrato publico, implementacao, rede e tela separados.

Na feature principal, estou usando o padrao API/Implementation:

```text
:app  -> :feature:shortener:impl
:impl -> :feature:shortener:api
```

O modulo `api` expoe apenas contrato e modelos publicos em Kotlin puro. O modulo `impl` guarda a tela, ViewModel, regra de negocio, repository e chamada de rede.

Dentro do `impl`, a organizacao continua separada:

```text
presentation -> domain <- data
```

A tela depende do ViewModel, o ViewModel depende do use case, e o use case depende de uma abstracao de repository.

## Modulos

### `:app`

Entrada do aplicativo.

Responsável por:

- abrir a `MainActivity`;
- aplicar o tema;
- iniciar o Koin;
- configurar o Sentry quando existir DSN;
- chamar o `AppNavHost`.

### `:navigation`

Grafo de navegação do app.

Responsável por:

- definir as rotas principais;
- iniciar pela splash;
- navegar da splash para o encurtador;
- receber o conteudo da feature por parametro, sem depender da implementacao dela.

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

### `:feature:shortener:api`

Contrato publico da feature de encurtador.

Aqui ficam:

- interface publica `Shortener`;
- resultado publico `ShortenUrlResult`;
- modelos publicos `ShortenedUrl` e `ShortenerError`;
- nenhum detalhe de Compose, Android, Ktor, repository, ViewModel ou regra interna.

Esse modulo deve continuar leve e estavel. Ele nao tem Manifest, resources, Compose ou dependencia Android.

### `:feature:shortener:impl`

Implementacao privada da feature de encurtador.

Aqui ficam:

- tela em Compose;
- `ShortenerViewModel`;
- estado da tela com `StateFlow`;
- mensagens que aparecem para o usuario;
- use case de encurtamento;
- regras de validacao e normalizacao da URL;
- repository;
- remoto da API;
- DI interno da feature.

Outros modulos que precisarem da capacidade de encurtar URL devem enxergar apenas o contrato do `:feature:shortener:api`. A tela concreta fica no `impl` e é conectada pelo `:app`.

Dentro do `impl`, a feature continua organizada em partes menores:

```text
ui: tela, estado e ViewModel
usecase: regra de encurtamento
repository: contrato privado e implementacao dos dados
remote: chamada da API
```

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

- `ConsoleReport`: escreve logs no console com a tag `AppLogs`;
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

### Unitarios da regra de negocio

Arquivo:

```text
feature/shortener/impl/src/test/java/.../ShortenUrlUseCaseTest.kt
```

Cobre:

- URL sem `https://` e normalizada antes de chamar o repository;
- falha do repository retornando `ServiceUnavailable`;
- URL invalida sem chamar o repository.

Rodar:

```bash
./gradlew :feature:shortener:impl:testDebugUnitTest
```

### Unitarios de dados

Arquivo:

```text
feature/shortener/impl/src/test/java/.../UrlShortenerRepositoryImplTest.kt
```

Cobre:

- sucesso remoto mapeando `AliasResponse` para `ShortenedUrl`;
- falha remota retornando `Result.failure`;
- `_links.self` virando `originalUrl`;
- `_links.short` virando `shortUrl`;
- chamada ao logger quando existe erro remoto.

Rodar:

```bash
./gradlew :feature:shortener:impl:testDebugUnitTest
```

### Unitarios de ViewModel

Arquivo:

```text
feature/shortener/impl/src/test/java/.../ShortenerViewModelTest.kt
```

Usa Turbine para observar o `StateFlow`.

Cobre:

- sucesso ao encurtar, com loading, historico e mensagem;
- falha do repository mostrando mensagem de indisponibilidade;
- URL invalida sem chamar repository;
- abrir URL usando a URL original ja disponivel no estado.

Rodar:

```bash
./gradlew :feature:shortener:impl:testDebugUnitTest
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
./gradlew :observability:test :feature:shortener:impl:testDebugUnitTest
```

Rodar validacao principal:

```bash
./gradlew :app:assembleDebug :observability:test :feature:shortener:impl:testDebugUnitTest :app:assembleDebugAndroidTest
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
- Contrato publico de feature fica em `:feature:*:api`.
- Implementacao privada de feature fica em `:feature:*:impl`.
- Dentro do `impl`, regra, dados e tela continuam separados por package.
- Testes seguem o padrao Given, When, Then.
