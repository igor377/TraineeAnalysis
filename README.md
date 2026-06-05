# Plataforma de Análise de Recrutamento com IA

Uma API REST desenvolvida para a otimização e análise automatizada de processos seletivos de trainees. O sistema integra inteligência artificial para transformar dados brutos de candidatos em insights estruturados para recrutadores.

## Sobre o Projeto

A plataforma resolve uma dor real do setor de Recursos Humanos: o tempo gasto na triagem de perfis. Através da integração com o modelo de linguagem **Gemini (LLM)** via API, o sistema realiza o processamento de linguagem natural (NLP) para ler dados de candidatos e gerar resumos analíticos automatizados, padronizando a avaliação inicial.

## Tecnologias Utilizadas

- **Linguagem Principal:** [Java](https://www.oracle.com/java/)
- **Framework:** [Spring Boot](https://spring.io/projects/spring-boot) (Spring Web, Spring Data JPA)
- **Inteligência Artificial:** [API do Gemini](https://ai.google.dev/) (Google AI SDK / REST Integration)
- **Banco de Dados:** [PostgreSQL](https://www.postgresql.org/)
- **Versionamento:** Git & GitHub

## Principais Funcionalidades

- **Processamento Automatizado:** Leitura e interpretação de dados textuais de candidatos enviados à API.
- **Resumos Analíticos com IA:** Integração direta com o modelo Gemini para geração de relatórios de perfil, destacando pontos fortes e fit cultural do candidato.
- **Arquitetura Escalável:** Estrutura backend robusta seguindo boas práticas de desenvolvimento para garantir facilidade na manutenção e novos acoplamentos de IA.

## Roadmap de Evolução

O projeto possui um planejamento focado em engenharia de dados e inteligência artificial preditiva:
- [ ] Modelagem de um pipeline de dados dedicado.
- [ ] Implementação de análise preditiva para sucesso de candidatos nas etapas avançadas.
- [ ] Sistema de cruzamento estatístico de perfis com os requisitos de vagas específicas.

## Como Executar o Projeto

### Pré-requisitos
- Java 17 ou superior instalado.
- Maven instalado.
- Banco de dados PostgreSQL rodando localmente ou via container.
- Uma **API Key do Google Gemini** (obtida no Google AI Studio).

### 🛠️ Passo a Passo

1. Clone o repositório:
   git clone https://github.com/igor377/TraineeAnalysis.git
   cd TraineeAnalysis

2. Configure as Variáveis de Ambiente:
   Crie ou ajuste o arquivo `src/main/resources/application.properties` (ou configure no ambiente do seu sistema):
   
   spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   GEMINI_API_KEY=sua_chave_da_api_aqui

3. Instale as dependências e compile o projeto:
   mvn clean install

4. Execute a aplicação:
   mvn spring-boot:run

   A API estará disponível em http://localhost:8080.
