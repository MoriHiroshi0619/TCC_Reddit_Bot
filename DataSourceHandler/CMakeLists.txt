cmake_minimum_required(VERSION 3.18)

project(DataSourceHandler)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include_directories(${PROJECT_SOURCE_DIR}/include)

execute_process(COMMAND pg_config --includedir
        OUTPUT_VARIABLE PostgreSQL_INCLUDE_DIR
        OUTPUT_STRIP_TRAILING_WHITESPACE)
execute_process(COMMAND pg_config --libdir
        OUTPUT_VARIABLE PostgreSQL_LIBRARY_DIR
        OUTPUT_STRIP_TRAILING_WHITESPACE)

include_directories(${PostgreSQL_INCLUDE_DIR})

add_subdirectory(lib/libpqxx build-pqxx)

link_directories(${PostgreSQL_LIBRARY_DIR})

add_executable(DataSourceHandler
        main.cpp
        src/DatabaseConnection.cpp
        include/DatabaseConnection.h
        include/RedditsPosts.h
        src/RedditsPosts.cpp
)

target_link_libraries(DataSourceHandler PRIVATE pqxx pq)
