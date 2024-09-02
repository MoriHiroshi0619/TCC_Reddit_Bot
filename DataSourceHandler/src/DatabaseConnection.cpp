#include <pqxx/pqxx>
#include <iostream>

class DatabaseConnection {
public:
    // Construtor que estabelece a conexão com o banco de dados
    DatabaseConnection(const std::string& connection_str)
        : conn(connection_str) {
        if (conn.is_open()) {
            std::cout << "Conectado ao banco de dados: " << conn.dbname() << std::endl;
        } else {
            std::cerr << "Não foi possível conectar ao banco de dados." << std::endl;
        }
    }

    // Destruidor que fecha a conexão automaticamente
    ~DatabaseConnection() {
        // A conexão é fechada automaticamente quando o objeto sai de escopo
        if (conn.is_open()) {
            std::cout << "Desconectando do banco de dados: " << conn.dbname() << std::endl;
        }
    }

    // Exemplo de método para realizar uma consulta
    void performQuery(const std::string& query) {
        try {
            pqxx::work txn(conn);
            pqxx::result res = txn.exec(query);
            for (auto row : res) {
                std::cout << "Resultado: ";
                for (auto field : row) {
                    std::cout << field.c_str() << " ";
                }
                std::cout << std::endl;
            }
            txn.commit();
        } catch (const std::exception& e) {
            std::cerr << "Erro ao executar consulta: " << e.what() << std::endl;
        }
    }

private:
    pqxx::connection conn;
};
