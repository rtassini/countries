# Project Documentation

# Projeto RESTful de Consulta de Países

## Descrição
Este projeto é uma aplicação RESTful desenvolvida em Java com Spring Boot, que permite consultar informações sobre países. 

## Rodando o projeto:
Java 17, Maven 3.8.6 ou superior.
A aplicação pode ser iniciada com o comando `mvn spring-boot:run`.

## Testes
Os testes podem ser executados com o comando `mvn test` ou similar.
 
## Foram implementados os seguintes endpoints:
- `GET countries/all`: Retorna uma lista de todos os países.
- `GET countries/filter/{filter}/value/{value}`: Retorna uma lista de países filtrados por um campo específico e um valor, sendo os campos válidos: `name`, `capital`, `region`, `subregion`.