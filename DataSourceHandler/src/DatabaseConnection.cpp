#include "DatabaseConnection.h"
#include <iostream>
#include "DatabaseSecretConstants.h"

DatabaseConnection::DatabaseConnection()
    : conn("user=" + std::string(DB_USER) +
           " dbname=" + std::string(DB_NAME) +
           " password=" + std::string(DB_PASSWORD) +
           " host=" + std::string(DB_HOST) +
           " port=" + std::string(DB_PORT)) {

    if (conn.is_open()) {
        std::cout << "Conectado ao banco de dados: " << conn.dbname() << std::endl;
    } else {
        std::cerr << "Não foi possível conectar ao banco de dados." << std::endl;
    }
}

DatabaseConnection::~DatabaseConnection() {
    if (conn.is_open()) {
        std::cout << "Desconectando do banco de dados: " << conn.dbname() << std::endl;
    }
}

pqxx::result DatabaseConnection::executeQuery(const std::string& query) {
    try {
        pqxx::work txn(conn);
        pqxx::result res = txn.exec(query);
        txn.commit();
        return res;
    } catch (const std::exception& e) {
        std::cerr << "Erro ao executar consulta: " << e.what() << std::endl;
        throw;
    }
}
