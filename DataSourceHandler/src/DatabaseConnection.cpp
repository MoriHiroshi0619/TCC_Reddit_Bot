#include "DatabaseConnection.h"
#include "DatabaseSecretConstants.h"

DatabaseConnection::DatabaseConnection()
    : conn("user=" + std::string(DB_USER) +
           " dbname=" + std::string(DB_NAME) +
           " password=" + std::string(DB_PASSWORD) +
           " host=" + std::string(DB_HOST) +
           " port=" + std::string(DB_PORT)) {}

pqxx::connection& DatabaseConnection::getConnection() {
    return conn;
}
