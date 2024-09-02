#cmake_minimum_required(VERSION 3.29)
#project(DataSourceHandler)
#
#set(CMAKE_CXX_STANDARD 20)
#
#add_executable(DataSourceHandler main.cpp)


cmake_minimum_required(VERSION 3.29)
project(DataSourceHandler)

set(CMAKE_CXX_STANDARD 20)

# Encontre a biblioteca libpqxx
find_package(PkgConfig REQUIRED)
pkg_check_modules(LIBPQXX REQUIRED libpqxx)
include_directories(${LIBPQXX_INCLUDE_DIRS})

# Inclua o diretório onde os arquivos de cabeçalho estão localizados
include_directories(${PROJECT_SOURCE_DIR}/include)

# Lista de todos os arquivos .cpp
set(SOURCES
        src/main.cpp
        src/DatabaseConnection.cpp
)

# Adiciona o executável
add_executable(DataSourceHandler ${SOURCES})

# Linka a biblioteca libpqxx
target_link_libraries(DataSourceHandler ${LIBPQXX_LIBRARIES})