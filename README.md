# cotacao-service

## Escopo do projeto

Criar um serviço responsável pelo fluxo de cotações de seguro. Os casos de uso que devem ser atendidos são:

1. Receber cotações de seguro.

    a. Validar se o produto e a oferta contidas na cotação existem, consultando a API de Catálogo;

    b. Validar se todas as assistências presentes na cotação são oferecidas pela oferta;
    
    c. Validar se todas as coberturas presentes na cotação são oferecidas pela oferta, e se o valor de cada cobertura fornecido não ultrapassa o valor máximo definido na oferta;
    
    d. Validar se o valor de cobertura total informado na cobertura corresponde à soma dos valores individuais de cada cobertura;
    
    e. Validar se o valor do prêmio mensal está dentro da faixa estabelecida pela oferta;
    
    f. Caso válido, inserir a cotação no banco de dados;
    
    g. E por fim publicar na fila `insurance-quote-received` o ID da cotação para que o serviço de Apólices possa gerar a apólice.

2. Disponibilizar consulta de cotações através do ID.

3. Receber na fila `insurance-policy-created` o ID da cotação + ID da apólice, e atualizar a Cotação no banco de dados, adicionando o ID da apólice.

## Metodologia

### API de Catálogo

Foi utilizado um Mock Server (`clue/json-server`) para mockar a API. A configuração de respostas pode ser encontrada no arquivo `db.json`, e o Mock server está publicado na porta 81 do localhost.

### Serviço de Apólices

Foi criado um serviço dentro da aplicação que realiza o consumo da fila `insurance-quote-received`, gera um ID de apólice aleatório para o ID de Cotação recebido, e os publica na fila `insurance-policy-created`. Seu código pode ser encontrado em `services/policy/`.

### Serviço de Cotações

O serviço de Cotações foi dividido em camadas, sendo as quais:

a. Controllers (camada de apresentação)

b. Errors (erros de regra de negócio)

c. External Services (acesso a serviços externos - API de Catálogo e publicação na fila `insurance-quote-received`)

d. Models (definição das entidades do banco de dados)

e. Repositories (acesso ao banco de dados)

f. Services (lógica de negócio)

#### Controllers

Foram criados 2 controllers:

1. QuoteController - expõe os casos de uso de criação de cotações e busca pelo ID através das chamadas `POST /quotes` e `GET /quotes/{id}`;
2. PolicyCreatedQueueListener - expõe o caso de uso de receber o ID da apólice e atualizar a cotação através do consumo da fila `insurance-policy-created`.

Além disso foi criado um error handler para transformar os erros de regra de negócio em HTTP 400 Bad Request. Seu código pode ser visualizado em `controllers/common/`

#### Errors

Dentro de `errors/`, está descrita a classe `BusinessError` que serve de base para todos os erros de regra de negócio que foram criados subsequentemente.

#### External Services

Dentro de `external_services`, estão dispostas as classes que acionam serviços externos. Em `external_services/catalog` estão as classes que abstraem a comunicação HTTP com a API de Catálogo, expondo os casos de uso de buscar produto e oferta atraves do ID. Em `external_service/policy` estão as classes que abstraem a publicação na fila `insurance-quote-received`, expondo o caso de uso de criar apólice para a cotação de forma assíncrona.

#### Models

Dentro de `models/` estão as entidades do escopo do projeto, sendo elas `Assistance`, `Coverage`, `Customer` e `Quote`. Os relacionamentos entre as entidades são:

a. Quote -> Customer: 1 -> 1;

b. Quote -> Assistance: 1 -> N;

c. Quote -> Coverage: 1 -> N.

#### Repositories

Dentro de `repositories/` estão dispostos os repositórios para manipulação de cada uma das entidades descritas em `models/`. A implementação apenas estende JPARepository.

#### Services

Em `services/quote/` está definida a classe `QuoteService`, que implementa os casos de uso de criação de Cotação, busca pelo ID e inserção do ID de apólice na Cotação. Também se encontram os DTOs utilizados nestes casos de uso, quando aplicável;

Em `services/policy` está descrito o serviço de apólices, responsável por receber o ID da Cotação na fila `insurance-quote-received`, gerar um ID de apólice, e devolver ambos IDs na fila `insurance-policy-created`.

