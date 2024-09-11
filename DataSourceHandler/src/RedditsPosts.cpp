#include "RedditsPosts.h"
#include <iostream>

RedditsPosts::RedditsPosts(DatabaseConnection& dbConn)
    : dbConnection(dbConn) {}

void RedditsPosts::fetchPosts() {
    std::string query = R"(
        WITH __posts AS (
            SELECT
                m.latitude AS latitude,
                m.longitude AS longitude,
                c.id AS categoria_id,
                c.nome AS categoria_nome,
                TO_CHAR(TO_TIMESTAMP(sp.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em
            FROM subreddit_post sp
            LEFT JOIN municipios m ON sp.municipio_id = m.geocodigo
            LEFT JOIN subreddit_post_categoria spc ON sp.id = spc.post_id
            LEFT JOIN categoria c ON c.id = spc.categoria_id
            WHERE spc.peso IS NOT NULL
            UNION
            SELECT
                m.latitude AS latitude,
                m.longitude AS longitude,
                c.id AS categoria_id,
                c.nome AS categoria_nome,
                TO_CHAR(TO_TIMESTAMP(spab.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em
            FROM subreddit_post_arquivo_bruto spab
            LEFT JOIN municipios m ON spab.municipio_id = m.geocodigo
            LEFT JOIN subreddit_post_categoria_arquivo_bruto spcab ON spab.id = spcab.post_id
            LEFT JOIN categoria c ON c.id = spcab.categoria_id
            WHERE spcab.peso IS NOT NULL
        )
        SELECT * FROM __posts ORDER BY categoria_id;
    )";

    pqxx::result res = dbConnection.executeQuery(query);

    for (const auto& row : res) {
        std::cout << "Latitude: " << row["latitude"].as<std::string>()
                  << ", Longitude: " << row["longitude"].as<std::string>()
                  << ", Categoria ID: " << row["categoria_id"].as<int>()
                  << ", Categoria Nome: " << row["categoria_nome"].as<std::string>()
                  << ", Criado em: " << row["criado_em"].as<std::string>()
                  << std::endl;
    }
}
