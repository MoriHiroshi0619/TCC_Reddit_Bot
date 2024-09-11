#ifndef DATABASECONNECTION_H
#define DATABASECONNECTION_H

#include <pqxx/pqxx>

class DatabaseConnection {
public:
    DatabaseConnection();
    pqxx::connection& getConnection();

private:
    pqxx::connection conn;
};

#endif
