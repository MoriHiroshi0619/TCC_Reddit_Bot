#include <pqxx/pqxx>
#include <iostream>
#include <DatabaseSecretConstants.h>

class DatabaseConnection {
public:
    // Construtor que usa uma string de conexão fornecida
    DatabaseConnection(const std::string& connection_str)
        : conn(connection_str) {
        if (conn.is_open()) {
            std::cout << "Conectado ao banco de dados: " << conn.dbname() << std::endl;
        } else {
            std::cerr << "Não foi possível conectar ao banco de dados." << std::endl;
        }
    }

    // Novo construtor que usa constantes para a conexão
    DatabaseConnection()
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

    // Destruidor
    ~DatabaseConnection() {
        if (conn.is_open()) {
            std::cout << "Desconectando do banco de dados: " << conn.dbname() << std::endl;
        }
    }

    void executeQuery(const std::string& query) {
        try {
            pqxx::work transaction(conn); //inicia uma transação
            pqxx::result resultado = transaction.exec(query); //executa a consulta
            for (auto row : resultado) {
                std::cout << "Resultado: ";
                for (auto field : row) {
                    std::cout << field.c_str() << " ";
                }
                std::cout << std::endl;
            }
            transaction.commit(); //finaliza a transação
        } catch (const std::exception& e) {
            std::cerr << "Erro ao executar consulta: " << e.what() << std::endl;
        }
    }

private:
    pqxx::connection conn;
};
