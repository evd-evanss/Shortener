# Shortener

Aplicativo Android para encurtar, copiar e compartilhar URLs, desenvolvido com **Kotlin e Jetpack Compose**.

O projeto explora arquitetura modular, tratamento de falhas de rede e observabilidade em um fluxo pequeno e fácil de acompanhar.

## Funcionalidades

- Validação e normalização de URLs antes da chamada à API.
- Lista de links recentes e compartilhamento pelo Android.
- Cópia do link gerado e abertura da URL original.
- Estados de carregamento, sucesso e erro.

## Decisões técnicas

| Decisão | Objetivo |
| --- | --- |
| Módulos API/Impl | Separar contratos públicos da implementação de cada feature. |
| MVVM com StateFlow | Representar o estado da tela e observar suas transições. |
| Contrato AppLogger | Permitir que as features registrem eventos sem depender diretamente do Sentry. |
| Ktor e Koin | Centralizar comunicação HTTP e composição das dependências. |

**Stack:** Compose, Material 3, Navigation 3, Kotlin Coroutines, Ktor, Koin e Sentry opcional.

## Executar

Requisitos: Android Studio, JDK 17, Android SDK e dispositivo ou emulador com internet.

```bash
git clone https://github.com/evd-evanss/Shortener.git
cd Shortener
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

Também é possível abrir a pasta no Android Studio, sincronizar o Gradle e executar o módulo `app`.

## Testes

O repositório contém testes de normalização de URLs, mapeamento de respostas, estados do ViewModel e distribuição de eventos de log, além de teste instrumentado e fluxos Maestro.

```bash
./gradlew :core:observability:impl:test :feature:shortener:impl:testDebugUnitTest
```

Consulte os [cenários e comandos de validação](docs/technical-guide.md#testes) e os [fluxos Maestro](.maestro/README.md).

## Limitações e configuração

- A API pública pode demorar a responder por cold start. Os testes que a utilizam dependem da disponibilidade do serviço.
- O alias gerado é curto, mas o domínio da API pode fazer o link completo ficar maior que a URL original.
- A ação **Abrir URL** abre a URL original retornada pela API.
- O app funciona sem Sentry. A configuração opcional do DSN está no [guia técnico](docs/technical-guide.md#sentry).

## Documentação

[Guia técnico completo](docs/technical-guide.md): arquitetura, navegação, módulos, observabilidade, testes e comandos.
