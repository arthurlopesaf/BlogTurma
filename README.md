# Blog da Turma (Console)

Aplicação Java de terminal com MariaDB, organizada em `models`, `views`, `controllers`, `services` e `data`.

## Requisitos
- JDK 17 ou superior
- Maven 3.8 ou superior
- MariaDB em execução

## 1. Criar o banco
Execute no MariaDB o arquivo `src/main/resources/BlogTurma.sql`.

O projeto mantém a configuração usada anteriormente:
- URL: `jdbc:mariadb://localhost:3308/BlogTurma`
- Usuário: `root`
- Senha: `root`

Se seu MariaDB usar outros dados, configure as variáveis de ambiente `BLOG_DB_URL`, `BLOG_DB_USER` e `BLOG_DB_PASSWORD`. Exemplo de URL: `jdbc:mariadb://localhost:3306/BlogTurma`.

## 2. Executar
Na pasta que contém o `pom.xml`, execute:

```bash
mvn clean compile
mvn exec:java
```

No IntelliJ, abra a pasta do projeto como projeto Maven, aguarde o download das dependências e execute `org.example.Main`.

## Funcionalidades
- Cadastro com validação básica e e-mail único
- Login com hash de senha Argon2
- Sessão e logout
- Listagem de publicações com autor e data
- Visualização individual e comentários
- Criação de publicações
- Comentários em publicações
- Edição e exclusão somente das próprias publicações
- Exclusão dos comentários associados junto com a publicação