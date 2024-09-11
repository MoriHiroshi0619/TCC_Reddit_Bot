#ifndef REDDITSPOSTS_H
#define REDDITSPOSTS_H

#include "DatabaseConnection.h"
#include <string>

class RedditsPosts {
public:
    RedditsPosts(DatabaseConnection& dbConn);  // Construtor que recebe uma conexão já aberta
    void fetchPosts();  // Método para executar a query e processar os dados

private:
    DatabaseConnection& dbConnection;
};

#endif
