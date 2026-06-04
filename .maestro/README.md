# Maestro UI tests

Flows de UI/E2E para o app.

## Rodar no device fisico

Instale o app debug no device pelo Android Studio ou Gradle:

```bash
./gradlew :app:installDebug
```

Depois rode os fluxos estaveis:

```bash
maestro test .maestro/launch_screen.yaml
maestro test .maestro/empty_url_error.yaml
maestro test .maestro/invalid_url_error.yaml
```

Ou rode todos os fluxos:

```bash
maestro test .maestro
```

## Fluxo com API real

O arquivo `shorten_success_real_api.yaml` chama a API real:

```bash
maestro test .maestro/shorten_success_real_api.yaml
```

Esse teste pode falhar se a API estiver fria, lenta ou fora do ar. Por isso os fluxos de erro local sao mais estaveis para CI.
