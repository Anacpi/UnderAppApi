    # --- Estágio 1: Build ---
    FROM eclipse-temurin:25-jdk-alpine AS build
    WORKDIR /app
    
    # Copia apenas os arquivos de configuração do Maven primeiro para aproveitar o cache de camadas
    COPY .mvn/ .mvn
    COPY mvnw pom.xml ./
    RUN chmod +x mvnw
    # Baixa as dependências sem compilar o código ainda
    RUN ./mvnw dependency:go-offline
    
    # Copia o código fonte e gera o jar
    COPY src ./src
    RUN ./mvnw clean package -DskipTests
    
    # --- Estágio 2: Runtime (Leve) ---
    FROM eclipse-temurin:25-jre-alpine
    WORKDIR /app
    
    # Criar um usuário não-root para segurança e menor overhead
    RUN addgroup -S spring && adduser -S spring -G spring
    USER spring:spring
    
    # Copia o JAR executável gerado pelo Spring Boot
    COPY --from=build /app/target/underapp-api-0.0.1-SNAPSHOT.jar app.jar
    
    # Configurações de Memória para ambiente de desenvolvimento (simulando restrição)
    # -XX:+UseSerialGC: Ideal para economizar RAM em máquinas pequenas
    # -Xmx512M: Limite para o container local não "roubar" toda a RAM da sua máquina
    ENV JAVA_OPTS="-Xms256M -Xmx512M -XX:+UseSerialGC -XX:+UseCompactObjectHeaders"
    
    EXPOSE 8080
    
    # Executa a aplicação Spring Boot
    ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]