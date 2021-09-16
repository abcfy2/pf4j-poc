# pf4j-poc

This is only a poc project for [pf4j](https://pf4j.org/).

## Requirements

- Java 8 or above (LTS version is recommended, e.g: 8, 11, 17)

## How to run

```sh
./gradlew shadowjar
mkdir -p build/plugins
cp -v app/build/libs/app-1.0.0-all.jar build/
cp -v plugins/plugin1/build/libs/hello-plugin-0.0.1.jar build/plugins/
cd build
java -jar app-1.0.0-all.jar
```
