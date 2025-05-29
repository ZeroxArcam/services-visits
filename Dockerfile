# Etapa 1: Construcción con Gradle
# Usamos una imagen base que ya tiene JDK 17 y Gradle.
# 'jammy' se refiere a Ubuntu 22.04 LTS, una base común y estable.
FROM gradle:8.5-jdk17-jammy AS builder

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos los archivos de Gradle primero para aprovechar el cache de Docker
# si las dependencias no han cambiado.
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle/

# Descargamos las dependencias (esto también se cachea si build.gradle no cambia)
# El comando --no-daemon es bueno para entornos CI/contenedores.
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# Copiamos el resto del código fuente de la aplicación
COPY src ./src/

# Construimos la aplicación, omitiendo las pruebas
# El JAR se generará en build/libs/
RUN ./gradlew build -x test --no-daemon


# Etapa 2: Ejecución
# Usamos una imagen base más ligera solo con Java Runtime Environment (JRE)
# ya que no necesitamos JDK completo ni Gradle para ejecutar la aplicación.
FROM eclipse-temurin:17-jre-jammy

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR construido desde la etapa 'builder'
# El nombre del JAR puede variar, así que usamos un comodín.
# Asegúrate de que solo haya un JAR principal en build/libs o ajusta esto.
COPY --from=builder /app/build/libs/*.jar app.jar

# Exponemos el puerto en el que la aplicación Spring Boot correrá.
# Spring Boot por defecto usa 8080. Render inyectará una variable PORT.
# Tu aplicación (con server.port=${PORT:8080}) escuchará en el puerto de Render.
# EXPOSE solo documenta el puerto, no lo publica realmente.
# EXPOSE 8082 actualmente sin puerto para confirmar si logra encontrarlo

# Comando para ejecutar la aplicación cuando el contenedor inicie.
# Spring Boot usará la variable de entorno PORT si está disponible.
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]
