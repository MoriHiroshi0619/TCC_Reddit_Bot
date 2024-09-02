cmake_minimum_required(VERSION 3.18)

# Nome do projeto
project(DataSourceHandler)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Incluir diretórios de headers
include_directories(${PROJECT_SOURCE_DIR}/include)

# Definir caminhos para as bibliotecas e headers do PostgreSQL
set(PostgreSQL_INCLUDE_DIR "/usr/local/opt/libpq/include")
set(PostgreSQL_LIBRARY "/usr/local/opt/libpq/lib/libpq.dylib")

# Adicionar o diretório da libpqxx ao projeto
add_subdirectory(lib/libpqxx build-pqxx)

# Adicionar o executável principal do projeto
add_executable(DataSourceHandler
        src/main.cpp
        src/DatabaseConnection.cpp
        include/DatabaseSecretConstants.h
        # Adicione outros arquivos fonte se necessário
)

# Linkar a biblioteca pqxx e libpq
target_link_libraries(DataSourceHandler PRIVATE pqxx ${PostgreSQL_LIBRARY})

# Incluir os diretórios adicionais necessários
include_directories(${PostgreSQL_INCLUDE_DIR})


