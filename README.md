# 🤖 TCC Reddit Bot

Este repositório contém o código-fonte para o projeto de conclusão de curso desenvolvido por Mori Hiroshi, que aborda a coleta de dados de redes sociais, com foco específico na plataforma Reddit. O projeto integra a coleta de dados à estrutura de dados Time Hierarchy Stream Cube (THSC) para análise e visualização em tempo real.

## 📚 Descrição do Projeto

O objetivo deste projeto era desenvolver um robô automatizado para coletar dados do Reddit em tempo real. Devido a restrições da API e penalidades por uso excessivo, o projeto foi adaptado para coletar dados de forma eficiente, respeitando os limites de requisições e evitando penalidades. A coleta de dados, embora não realizada em tempo real como inicialmente planejado, foi eficaz e forneceu insights valiosos através da análise de dados multidimensionais.

## 🗂️ Estrutura do Projeto
- DataSourceHandler: Diretório onde está o código C++ responsável pela integração com o Time Hierarchy Stream Cube (THSC).
  - **Importante**: O THSC é uma aplicação de terceiros criada pela Professora Dra. Raquel Marcia Müller, da Universidade Estadual de Mato Grosso do Sul. É necessário obter aprovação da professora para o uso da ferramenta.
- tcc_reddit: Diretório onde está a aplicação Java Spring.
- subreddits23: Contém postagens de subreddits específicos, salvos e comprimidos no formato `.zst`.

## 🚀 Funcionalidades
- 🔍 Coleta de Dados: Coleta automática de dados de subreddits específicos utilizando a API do Reddit.
- 🧩 Integração com THSC: Os dados coletados são integrados com o Time Hierarchy Stream Cube para análise e visualização.
- 📊 Visualização de Dados: Implementação de mapas de calor e outras metáforas visuais para a representação dos dados coletados.
- ✅ Respeito às Diretrizes da API: Implementação de estratégias para coleta de dados que conformam com as diretrizes da API do Reddit para evitar bloqueios e penalidades.

## 🛠️ Tecnologias Utilizadas
- Java 17: Linguagem principal para o desenvolvimento do robô de coleta de dados.
- Spring Boot 3.2.4: Utilizado para facilitar a configuração e o setup do projeto.
- PostgreSQL 16: Banco de dados para armazenamento dos dados coletados.
- C++: Utilizado na integração com o THSC.
- Docker: Para containerização da aplicação.

# 📝 Como Usar
## Pré-requisitos
- Docker instalado em sua máquina.
- Credenciais da API do Reddit: `client_id` e `secret_id`, que podem ser solicitadas no endereço [Reddit Apps](https://www.reddit.com/prefs/apps).
- Arquivo de Configuração: Necessário criar um arquivo `application.yml` com as configurações específicas.

## Passo a Passo

1. **🐙 Clonar o Repositório**:
  ```bash 
  git clone https://github.com/MoriHiroshi0619/TCC_Reddit_Bot.git
  ```
2. **📁 Navegar até o Diretório da Aplicação**:
  ```bash 
  cd TCC_Reddit_Bot/tcc_reddit
  ```
3. **🛠️ Criar o Arquivo `application.yml`**:
<br/> Crie um arquivo chamado `application.yml` no caminho `tcc_reddit/src/main/resources` com o seguinte conteúdo:
  ```yaml 
  spring:
    datasource:
      url: jdbc:postgresql://<HOST>:<PORTA>/<NOME_DO_BANCO>
      username: <USUARIO>
      password: <SENHA>
  reddit:
    client_id: <SEU_CLIENT_ID>
    secret_id: <SEU_SECRET_ID>
    username: <SEU_USUARIO_REDDIT>
    password: <SUA_SENHA_REDDIT>
  ```
Observação: Substitua os valores entre < > pelas suas informações:
- `<HOST>`: Endereço do seu banco de dados PostgreSQL.
- `<PORTA>`: Porta do PostgreSQL (geralmente 5432).
- `<NOME_DO_BANCO>`: Nome do banco de dados que será utilizado.
- `<USUARIO>` e `<SENHA>`: Credenciais do banco de dados.
- `<SEU_CLIENT_ID>` e `<SEU_SECRET_ID>`: Credenciais obtidas no [Reddit Apps](https://www.reddit.com/prefs/apps).
- `<SEU_USUARIO_REDDIT>` e `<SUA_SENHA_REDDIT>`: Credenciais da sua conta Reddit.
4. **🐳 Executar a Aplicação com Docker**:
<br/> Certifique-se de que o Docker está instalado e em execução. 
<br/>No diretório `/tcc_reddit`, execute os seguintes comandos:
- Construir a imagem Docker:
  ```bash 
  docker build -t tcc_reddit_bot .
  ```
- Executar o container:
  ```bash 
  docker run -d -p 8080:8080 --name tcc_reddit_bot tcc_reddit_bot
  ```
> **Nota:** Verifique se o PostgreSQL está acessível pelo container Docker. Se estiver rodando o PostgreSQL em sua máquina local, talvez seja necessário configurar a rede do Docker ou utilizar uma solução como o `docker-compose`.
5. **🔎 Verificar a Execução**:
- A aplicação estará disponível na porta 8080.
- Para acompanhar os logs:
  ```bash 
  docker logs -f tcc_reddit_bot
  ```
5. **🔗 Integração com o THSC**:
- Navegue até o diretório `DataSourceHandler` para acessar o código C++ responsável pela integração.
- 🛠️ Criar o Header `DatabaseSecretConstants.h`
<br/> Crie um arquivo chamado `DatabaseSecretConstants.h` no diretório `DataSourceHandler/include` com o seguinte conteúdo:
  ```cpp
  #ifndef DATABASESECRETCONSTANTS_H
  #define DATABASESECRETCONSTANTS_H
  
  constexpr auto DB_USER = "<USUARIO>";
  constexpr auto DB_NAME = "<NOME_DO_BANCO>";
  constexpr auto DB_PASSWORD = "<SENHA>";
  constexpr auto DB_HOST = "<HOST>";
  constexpr auto DB_PORT = "<PORTA>";
  
  #endif
  ```
> **Observação**: Substitua os valores entre < > pelas suas informações do banco de dados.
- Compilar e Executar
<br/> Navegue até o diretório `DataSourceHandler` e compile o programa:
  ```bash
  g++ main.cpp -o DataSourceHandler
  ```
  Execute o programa
  ```bash
  ./DataSourceHandler
  ```







  
 
