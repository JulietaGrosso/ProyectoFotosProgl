# =========================
# Etapa 1: Build con Maven
# =========================
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar el pom.xml primero para cachear dependencias
COPY pom.xml ./

# Copiar todo el código fuente
COPY src ./src

# Compilar el proyecto y generar el WAR (sin tests)
RUN mvn clean package -DskipTests

# =========================
# Etapa 2: Runtime con Tomcat
# =========================
FROM tomcat:10.1-jdk17

# Limpiar las apps por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Crear directorio para subir fotos
RUN mkdir -p /app/uploads

# Copiar el WAR generado desde la etapa build
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Variable de entorno: ruta donde se guardan las fotos subidas
ENV UPLOAD_DIR=/app/uploads

# Exponer puerto
EXPOSE 8080

# Comando de inicio
CMD ["catalina.sh", "run"]
