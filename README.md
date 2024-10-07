# TCC Reddit Bot

Este repositório contém o código-fonte para o projeto de conclusão de curso desenvolvido por Mori Hiroshi, que aborda a coleta de dados de redes sociais, com foco específico na plataforma Reddit. O projeto integra a coleta de dados à estrutura de dados Time Hierarchy Stream Cube (THSC) para análise e visualização em tempo real.

## Descrição do Projeto

O objetivo deste projeto era desenvolver um robô automatizado para coletar dados do Reddit em tempo real. Devido a restrições da API e penalidades por uso excessivo, o projeto foi adaptado para coletar dados de forma eficiente, respeitando os limites de requisições e evitando penalidades. A coleta de dados, embora não realizada em tempo real como inicialmente planejado, foi eficaz e forneceu insights valiosos através da análise de dados multidimensionais.

## Funcionalidades

- **Coleta de Dados**: Coleta automática de dados de subreddits específicos utilizando a API do Reddit.
- **Integração com THSC**: Os dados coletados são integrados com o Time Hierarchy Stream Cube para análise e visualização.
- **Visualização de Dados**: Implementação de mapas de calor e outras metáforas visuais para a representação dos dados coletados.
- **Respeito às Diretrizes da API**: Implementação de estratégias para coleta de dados que conformam com as diretrizes da API do Reddit para evitar bloqueios e penalidades.

## Tecnologias Utilizadas

- **Java**: Linguagem principal para o desenvolvimento do robô de coleta de dados.
- **Spring Boot**: Utilizado para facilitar a configuração e o setup do projeto.
- **PostgreSQL**: Banco de dados para armazenamento dos dados coletados.

## Como Usar

Para executar este projeto localmente, siga os passos abaixo:

1. **Clonar o Repositório**
`git clone https://github.com/MoriHiroshi0619/TCC_Reddit_Bot.git`

2. **Configurar Variáveis de Ambiente**
- Configure as variáveis de ambiente necessárias para a API do Reddit e o banco de dados PostgreSQL.
3. **Executar o Projeto**
- Utilize sua IDE favorita ou linha de comando para executar o projeto.
- Certifique-se de que o PostgreSQL está configurado e funcionando.
