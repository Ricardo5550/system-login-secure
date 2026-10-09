
# ThinDesk — Sistema de Login Seguro

Projeto acadêmico desenvolvido para a disciplina de Desenvolvimento de Software Corporativo.

O ThinDesk utiliza Java, Spring Boot, Spring Security, Thymeleaf e MongoDB Atlas para implementar autenticação e autorização de usuários, com arquitetura modular e possibilidade de adaptação visual para outros projetos.

## Tecnologias utilizadas

- Java 23
- Spring Boot 3.4.0
- Spring Security
- Spring Data MongoDB
- MongoDB Atlas
- Spring Session MongoDB
- Thymeleaf
- Maven
- HTML, CSS e Bootstrap

## Funcionalidades

- Cadastro de usuários com validação básica.
- Armazenamento de senhas utilizando hash BCrypt.
- Autenticação por e-mail e senha.
- Login e logout com Spring Security.
- Proteção CSRF em formulários.
- Controle de acesso baseado em perfis (RBAC).
- Persistência de usuários e sessões no MongoDB Atlas.
- Interface modular utilizando fragmentos Thymeleaf.

## Perfis de usuário

O projeto define três perfis:

- `ADMIN`: perfil administrativo.
- `SUPORTE`: perfil de suporte técnico.
- `CLIENTE`: perfil atribuído automaticamente ao cadastro público.

As rotas `/clientes/**` e `/api/chamados/**` estão restritas aos perfis ADMIN e SUPORTE.

## Estrutura do projeto

- `controller`: tratamento das requisições HTTP.
- `service`: regras de negócio e cadastro de usuários.
- `repository`: acesso aos dados do MongoDB.
- `entity`: modelos de dados.
- `templates`: páginas HTML processadas pelo Thymeleaf.
- `templates/fragments`: componentes visuais reutilizáveis.
- `SecurityConfig.java`: configuração de autenticação e autorização.
- `UsuarioDetailsService.java`: integração do Spring Security com os usuários do MongoDB.

## Requisitos para execução

- JDK 23
- Acesso ao MongoDB Atlas
- IntelliJ IDEA ou outra IDE compatível
- Maven Wrapper incluído no projeto

## Configuração do MongoDB Atlas

1. Criar um cluster no MongoDB Atlas.
2. Criar um usuário de banco de dados com permissões adequadas.
3. Configurar o acesso de rede no Atlas.
4. Obter a URI de conexão do driver Java.
5. Configurar a variável de ambiente `MONGODB_URI`.

O arquivo `application.properties` utiliza:

```properties
spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.auto-index-creation=true

spring.session.store-type=mongodb
spring.session.mongodb.collection-name=sessions
server.servlet.session.timeout=30m
```

A variável `MONGODB_URI` deve conter a URI completa do Atlas, incluindo as credenciais locais. Senhas reais não devem ser publicadas no repositório.

## Como executar

No IntelliJ IDEA:

1. Abrir a pasta que contém `pom.xml`.
2. Aguardar a importação das dependências Maven.
3. Configurar `MONGODB_URI` nas variáveis de ambiente da execução.
4. Executar a classe `com.pfc.ThindeskApplication`.

Alternativamente, pelo terminal do Windows, na pasta do `pom.xml`:

```powershell
.\mvnw.cmd spring-boot:run
```

Neste caso, a variável `MONGODB_URI` precisa estar disponível no ambiente do terminal.

A aplicação utiliza, por padrão, a porta 8080.

Endereços:

- Login: `http://localhost:8080/login`
- Cadastro: `http://localhost:8080/cadastro`
- Página inicial: `http://localhost:8080/`

## Segurança implementada

As senhas são armazenadas como hashes BCrypt, em vez de texto puro.

O cadastro público atribui obrigatoriamente o perfil CLIENTE, impedindo a escolha direta de privilégios administrativos.

A autenticação consulta usuários persistidos no MongoDB Atlas através de um serviço compatível com `UserDetailsService`.

O Spring Security controla o acesso às rotas protegidas e realiza o logout por requisição POST com proteção CSRF.

O Spring Session MongoDB é utilizado para armazenar as sessões HTTP no banco de dados.

## Testes manuais realizados

- Inicialização do Spring Boot.
- Cadastro de usuário no MongoDB Atlas.
- Verificação do hash BCrypt e do perfil CLIENTE.
- Login com conta cadastrada.
- Bloqueio HTTP 403 de CLIENTE em `/clientes`.
- Bloqueio HTTP 403 de CLIENTE em `/api/chamados`.
- Logout com redirecionamento para a página de login.
- Verificação de acesso protegido após logout.
- Verificação da criação de documentos na coleção `sessions`.

## Adaptação para outros temas

A lógica de autenticação foi separada em serviços, repositórios e configurações do Spring Security.

A interface utiliza templates e fragmentos Thymeleaf, permitindo substituir elementos visuais sem reescrever a lógica central de autenticação.

## Finalidade

Projeto acadêmico desenvolvido para demonstração de autenticação, autorização, proteção de credenciais e persistência de sessões.

Não se destina a ambientes de produção sem testes, revisão de segurança e aprimoramento das regras de acesso.
