# 💼 Payroll System — Sistema de Folha de Pagamento

Sistema backend desenvolvido em **Java + Spring Boot** para gerenciamento de funcionários, cargos e geração de folha de pagamento com cálculo de horas extras.  
O projeto simula um cenário real de empresa, aplicando boas práticas como **arquitetura em camadas**, **soft delete (inativação)** e regras de negócio na camada de serviço.

---

## 🚀 Funcionalidades

### 👤 Funcionários
- Cadastro de funcionários
- Atualização de dados
- Listagem de funcionários
- ❌ Exclusão física não permitida
- ✅ Funcionário pode ser inativado (soft delete)
- Validações:
    - CPF duplicado
    - Cargo ativo

---

### 🏷️ Cargos
- Cadastro de cargos
- Atualização de cargos
- Listagem de cargos
- ❌ Exclusão física não permitida
- ✅ Cargo pode ser inativado
- Validações:
    - Nome duplicado

---

### 💰 Folha de Pagamento
- Geração de folha por funcionário e período (mês/ano)
- Validação para evitar duplicidade de folha no mesmo período
- Cálculo automático de:
    - Valor da hora com base no salário do cargo
    - Horas extras com adicional de 50%
- Consulta de folhas por:
    - CPF do funcionário
    - Listagem geral

> 🔹 O sistema atualmente foca no cálculo de horas extras, com arquitetura preparada para expansão futura.

---

## 🛠️ Tecnologias utilizadas
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Postman

---

## 🧱 Arquitetura

O projeto utiliza **arquitetura em camadas** para organização interna do código e expõe suas funcionalidades por meio de uma **API RESTful**:
- Controller → Service → Mapper → Repository → Database


- **Controller:** expõe a API REST
- **Service:** regras de negócio
- **Mapper:** conversão DTO ↔ Entity
- **Repository:** acesso ao banco
- **Database:** PostgreSQL

---

## 🔒 Regras de negócio importantes
- Funcionários e cargos **não são excluídos do sistema**
- Registros são apenas **inativados**, garantindo:
    - histórico
    - rastreabilidade
    - integridade dos dados

---

## ⚙️ Como rodar o projeto

1. Criar o banco de dados:

```sql
CREATE DATABASE payroll_db;
```

2. Configurar o arquivo application.properties em src/main/resources/:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/payroll_db
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Rodar o projeto:
```
mvn spring-boot:run
```

---

## 🧪 Testes da API

As funcionalidades podem ser testadas via Postman.

Endpoints:

👤 Funcionários (/api/funcionarios)
```
POST /api/funcionarios → cadastrar funcionário

PUT /api/funcionarios/{id} → atualizar funcionário

PATCH /api/funcionarios/{id}/inativar → inativar funcionário

GET /api/funcionarios → listar todos os funcionários

GET /api/funcionarios/{id} → buscar funcionário pelo ID

GET /api/funcionarios/cpf?cpf={cpf} → buscar funcionário pelo CPF
```

🏷️ Cargos (/api/cargos)
```
POST /api/cargos → cadastrar cargo

PUT /api/cargos/{id} → atualizar cargo

PATCH /api/cargos/{id}/inativar → inativar cargo

GET /api/cargos → listar todos os cargos

GET /api/cargos/{id} → buscar cargo pelo ID

GET /api/cargos/nome?nome={nome} → buscar cargo pelo nome
```

💰 Folha de Pagamento (/folhas)
```
POST /folhas → gerar folha

GET /folhas/cpf/{cpf} → buscar folhas por CPF

GET /folhas → listar todas as folhas
```
---

## 👨 Autor

[Yuri Vieira](https://github.com/yuriivieirac)  
Desenvolvedor Backend Java
