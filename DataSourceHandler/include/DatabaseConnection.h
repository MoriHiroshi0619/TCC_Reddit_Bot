#ifndef DATABASECONNECTION_H
#define DATABASECONNECTION_H

#include <pqxx/pqxx>

class DatabaseConnection {
public:
    DatabaseConnection(const std::string& connectionString);


    void executeQuery(const std::string& query);


    ~DatabaseConnection();

private:
    pqxx::connection *conn;  // Ponteiro para a conexão do banco de dados
};

#endif
