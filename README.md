Faturamento de Fretes API
https://www.linkedin.com/in/sandro-rogerio-madona-filho/ – Projeto Backend

API REST para gerenciamento de fretes e cálculo de faturamento, permitindo registrar fretes, listar por período, calcular totais mensais e quinzenais, além de exclusão por ID.

Este projeto foi desenvolvido com foco em boas práticas de backend, separação de responsabilidades e regras de negócio bem definidas, servindo como base sólida para integração com um front-end em TypeScript + Vite.

🛠 Tecnologias

Java 17

Spring Boot

Spring MVC

Spring Data JPA

PostgreSQL

Lombok

SpringDoc OpenAPI 3 (Swagger)

✅ Práticas adotadas

SOLID, DRY, KISS

API RESTful

DTOs para entrada e saída de dados

Regras de negócio isoladas no Service / Entity

Consultas com Spring Data JPA

Ordenação e filtros por período

Injeção de dependências

Tratamento de erros básicos

Persistência de faturamento já calculado

Preparação para consumo por Front-end

▶️ Como Executar
1️⃣ Clonar o repositório
git clone https://github.com/seu-usuario/faturamento-frete.git
cd faturamento-frete

2️⃣ Configurar o banco de dados (PostgreSQL)

No arquivo application.properties ou application.yml:

spring.datasource.url=jdbc:postgresql://localhost:5432/faturamento_frete
spring.datasource.username=postgres
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update

3️⃣ Construir o projeto
./mvnw clean package

4️⃣ Executar a aplicação
java -jar target/FaturamentoFrete-0.0.1-SNAPSHOT.jar

🌐 Acesso

API: http://localhost:8080

Swagger:
👉 http://localhost:8080/swagger-ui.html

🔗 API Endpoints

Para os exemplos abaixo foi utilizada a ferramenta httpie.

➕ Criar Frete
http POST :8080/frete \
rota="São Paulo - Campinas" \
valorRota=500 \
data="2025-01-01" \
combustivel=150 \
pedagio=50


Resposta

{
  "id": 1,
  "rota": "São Paulo - Campinas",
  "valorRota": 500,
  "data": "2025-01-01",
  "combustivel": 150,
  "pedagio": 50,
  "faturamento": 300
}

📋 Listar Todos os Fretes
http GET :8080/frete

📅 Listar Fretes por Período
http GET :8080/frete/periodo inicio=="2025-01-01" fim=="2025-01-31"

💰 Faturamento Mensal
http GET :8080/frete/mes/total ano==2025 mes==1


Resposta

3000

💵 Faturamento do Mês Atual
http GET :8080/frete/mes/atual/total

📆 Faturamento por Quinzena
http GET :8080/frete/quinzena/total \
ano==2025 \
mes==1 \
inicio==1 \
fim==15

❌ Remover Frete por ID
http DELETE :8080/frete/1


Resposta

204 No Content

📌 Observações Importantes

O faturamento é calculado no momento do cadastro e salvo no banco.

Custos de combustível e pedágio não são recalculados, garantindo histórico correto.

Os totais (mensal / quinzena) somam valores já persistidos, não fazem contas dinâmicas.

Projeto preparado para consumo por Front-end moderno (Vite + TypeScript).
