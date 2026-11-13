# Catálogo Simples de Livros, Séries e Filmes

Aplicação Java Web MVC sem framework, empacotada como WAR, com Java 17, Jakarta Servlet, JSP/JSTL, JDBC, PostgreSQL e Bootstrap 5.

## Banco de dados

Instale PostgreSQL 14+, copie `.env.example` para `.env`, configure os dados do banco e crie a tabela executando no `psql`:

```bash
psql -U postgres -d midias -f schema.sql
```

O banco configurado é `midias` em `localhost:5432`.
O arquivo `.env` é usado localmente e não deve ser enviado ao GitHub. Variáveis de ambiente do sistema (`DB_URL`, `DB_USER` e `DB_PASSWORD`) têm prioridade sobre o arquivo `.env`.

## Executar no Tomcat

Instale JDK 17, Maven 3.9+ e Apache Tomcat 10.1 ou superior. Gere o WAR:

```bash
mvn clean package
```

Copie `target/catalogo-midias.war` para a pasta `webapps` do Tomcat e acesse `http://localhost:8080/catalogo-midias/`.

## Arquitetura

`ItemMidiaServlet` é o controlador único em `/catalogo`; `ItemMidiaService` concentra validações; `ItemMidiaDAO` executa persistência JDBC; `ItemMidia` é o modelo; e as páginas JSP em `WEB-INF/views` são as views. As ações são `listar`, `novo`, `editar`, `detalhes`, `buscar`, `salvar` e `excluir`.

## Segurança e qualidade

- Todas as operações SQL usam `PreparedStatement` e try-with-resources, prevenindo SQL Injection e vazamentos de conexão.
- Saídas fornecidas pelo usuário são renderizadas com `<c:out>`, com escape XML para reduzir risco de XSS.
- O serviço valida campos obrigatórios, limites de tamanho, tipos permitidos e ano de lançamento.
- Execute `mvn test` para os testes unitários de regras de negócio.
