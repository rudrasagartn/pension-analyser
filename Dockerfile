#FROM eclipse-temurin:17-jdk-jammy
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.pfm.PensionAnalyserApplication"]



##Below is the multi stage docker build
FROM maven:3.9.6-eclipse-temurin-17 as pfm-build
WORKDIR /pfm

### Step 1 - Copy pom.xml and download project dependencies

# Dividing copy into two steps to ensure that we download dependencies 
# only when pom.xml changes
COPY pom.xml .
# dependency:go-offline - Goal that resolves all project dependencies, 
# including plugins and reports and their dependencies. -B -> Batch mode
RUN mvn dependency:go-offline -B

### Step 2 - Copy source and build "deployable package"
COPY src src
RUN mvn install -DskipTests

# Unzip
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

##### Stage 2 - Let's build a minimal image with the "deployable package"
FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
ARG DEPENDENCY=/pfm/target/dependency
COPY --from=pfm-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=pfm-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=pfm-build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.pfm.PensionAnalyserApplication"]



