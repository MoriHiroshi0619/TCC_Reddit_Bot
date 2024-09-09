#include "src/DatabaseConnection.cpp"
#include <DatabaseSecretConstants.h>

int main() {
    try {
        std::string connectionString = "user=" + std::string(DB_USER) +
                                        " dbname=" + std::string(DB_NAME) +
                                        " password=" + std::string(DB_PASSWORD) +
                                        " host=" + std::string(DB_HOST) +
                                        " port=" + std::string(DB_PORT);

        pqxx::connection conn(connectionString);
        if (conn.is_open()) {
            std::cout << "Connected to database successfully." << std::endl;
        } else {
            std::cout << "Failed to connect to database." << std::endl;
        }
    } catch (const std::exception &e) {
        std::cerr << e.what() << std::endl;
    }
    return 0;
}