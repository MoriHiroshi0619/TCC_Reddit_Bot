#ifndef DATABASECONNECTION_H
#define DATABASECONNECTION_H

#include <pqxx/pqxx>

class DatabaseConnection {
public:
    // Construtor
    DatabaseConnection(const std::string& connectionString);

    //return: instancia de pqxx::result
    pqxx::result executeQuery(const std::string& query);

    // Destrutor
    ~DatabaseConnection();

private:
    pqxx::connection *conn;  // Ponteiro para a conexão do banco de dados
};

#endif
