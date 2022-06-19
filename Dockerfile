FROM node:16.15.1 as frontend-builder

WORKDIR /builder

COPY src_front/public public
COPY src_front/src src
COPY src_front/package.json .
COPY src_front/package-lock.json .
COPY src_front/tsconfig.json .

RUN npm install
RUN npm run build

FROM gradle:7.4.2-jdk17 as builder

WORKDIR /builder

COPY src src
COPY --from=frontend-builder /builder/build src/main/resources/public
COPY build.gradle .
COPY settings.gradle .

RUN gradle bootWar --no-daemon

FROM openjdk:17.0.2-slim

WORKDIR /app
COPY --from=builder /builder/build/libs/*.war app.war

ENV PG_URL=CHANGEME
ENV PG_USER=CHANGEME
ENV PG_PASSWORD=CHANGEME
ENV FLYWAY_PG_URL=CHANGEME
ENV FLYWAY_PG_USER=CHANGEME
ENV FLYWAY_PG_PASSWORD=CHANGEME

ENTRYPOINT java -jar app.war
EXPOSE 8080