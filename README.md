API de Tarefas com Javalin

Este é um projeto Java com uma API REST simples usando [Javalin](https://javalin.io/), que permite criar, listar e consultar tarefas com armazenamento em memória.

---

Como buildar o projeto
Use o Gradle ou o wrapper incluído (`./gradlew`) para compilar o projeto:

``bash
./gradlew build

---

Como rodar a API
Execute o servidor com:

``bash
./gradlew run

---

A API será iniciada em:

http://localhost:7070

---

Como rodar os testes
Execute todos os testes automatizados com:

``bash
./gradlew test

Os testes cobrem os seguintes endpoints:

1. GET /hello

2. POST /tarefas

3. GET /tarefas

4. GET /tarefas/{id}

---

Requisitos
Java 17 ou superior

Gradle instalado (ou use o wrapper ./gradlew incluso)
