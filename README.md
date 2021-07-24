# jaya-convert

Este repositório contém um projeto desenvolvido na linguagem Kotlin com o objetivo de disponibilização de uma API para conversão entre duas moedas. A taxa de conversão utilizada neste projeto é obtida através de uma [API](http://api.exchangeratesapi.io/latest?base=EUR) externa, onde é utilizada somente o recurso de obtenção da taxa base em EURO. 

## Tecnologias utilizadas

  - [Kotlin](https://github.com/JetBrains/kotlin) para linguagem de programação
  - [Javalin](https://github.com/tipsy/javalin) para frwamework web
  - [Koin](https://github.com/InsertKoinIO/koin) para injeção de 
  - [Exposed](https://github.com/JetBrains/Exposed) para frameword de persistência de dados
  - [H2](https://github.com/h2database/h2database) para bando de dados em memória

#### Testes:

  - [junit](https://github.com/junit-team/junit4) para testes funcionais

#### Arquitetura do projeto
      + config
          Configurações gerais como inicialização da aplicação, injeção de dependências e configurações de banco de 
      + domain
        + repository
            Camada de persistência e mapeamento de entidades
        + service
            Lógica de negócios da aplicação
      + web
        + controller
            Mapeamento de rotas como controle de requisições

# Executando a API

`*É necessário ter uma JVM previamente configurada.`

O servidor é inicializado na porta [7000](http://localhost:7000/api) como padrão,  mas poderá ser modificado no arquivo `koin.properties`.

#### Build:
> ./gradlew clean build

#### Inicializando a API:
> ./gradlew run

A documentação da API está acessível em [swagger](http://localhost:7000/api/)
