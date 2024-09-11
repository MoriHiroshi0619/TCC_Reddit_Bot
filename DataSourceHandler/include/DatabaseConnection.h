#ifndef DATABASECONNECTION_H
#define DATABASECONNECTION_H

#include <pqxx/pqxx>
#include <string>

class DatabaseConnection {
public:
    DatabaseConnection();


    pqxx::result executeQuery(const std::string& query);

    ~DatabaseConnection();
private:
    pqxx::connection conn;  // Instância da conexão (não mais ponteiro)
};

#endif
