#include <iostream>
#include "DatabaseConnection.h"
#include "RedditsPosts.h"

int main() {
    try {
        DatabaseConnection dbConn;

        RedditsPosts redditPosts(dbConn);

        redditPosts.fetchPosts();

    } catch (const std::exception& e) {
        std::cerr << "Erro: " << e.what() << std::endl;
    }

    return 0;
}
