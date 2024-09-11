#include "RedditsPostsRepository.h"
#include <pqxx/pqxx>
#include <iostream>

RedditsPostsRepository::RedditsPostsRepository(DatabaseConnection& dbConnection) : dbConnection(dbConnection) {}

std::vector<Posts> RedditsPostsRepository::fetchPosts(int total) {
    std::vector<Posts> posts;

    try {
        pqxx::work txn(dbConnection.getConnection());

        // Criação da query SQL
        std::string query =
            "WITH __posts AS ("
            "    SELECT "
            "        m.latitude AS latitude, "
            "        m.longitude AS longitude, "
            "        c.id AS categoria_id, "
            "        c.nome AS categoria_nome, "
            "        TO_CHAR(TO_TIMESTAMP(sp.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em "
            "    FROM subreddit_post sp "
            "    LEFT JOIN municipios m ON sp.municipio_id = m.geocodigo "
            "    LEFT JOIN subreddit_post_categoria spc ON sp.id = spc.post_id "
            "    LEFT JOIN categoria c ON c.id = spc.categoria_id "
            "    WHERE spc.peso IS NOT NULL "
            "    UNION "
            "    SELECT "
            "        m.latitude AS latitude, "
            "        m.longitude AS longitude, "
            "        c.id AS categoria_id, "
            "        c.nome AS categoria_nome, "
            "        TO_CHAR(TO_TIMESTAMP(spab.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em "
            "    FROM subreddit_post_arquivo_bruto spab "
            "    LEFT JOIN municipios m ON spab.municipio_id = m.geocodigo "
            "    LEFT JOIN subreddit_post_categoria_arquivo_bruto spcab ON spab.id = spcab.post_id "
            "    LEFT JOIN categoria c ON c.id = spcab.categoria_id "
            "    WHERE spcab.peso IS NOT NULL "
            ") "
            "SELECT * FROM __posts "
            "ORDER BY categoria_id ";

        if (total > 0) {
            query += "LIMIT " + std::to_string(total) + ";";
        }

        pqxx::result res = txn.exec(query);

        for (auto row : res) {
            Posts post(
                row["latitude"].as<double>(),
                row["longitude"].as<double>(),
                row["categoria_id"].as<int>(),
                row["categoria_nome"].as<std::string>(),
                row["criado_em"].as<std::string>()
            );
            posts.push_back(post);
        }

        txn.commit();
    } catch (const std::exception &e) {
        std::cerr << "Erro ao buscar posts: " << e.what() << std::endl;
    }

    return posts;
}

std::vector<Posts> RedditsPostsRepository::fetchPostsByCategoryId(int categoryId, int total) {
    std::vector<Posts> posts;

    try {
        pqxx::work txn(dbConnection.getConnection());

        // Criação da query SQL
        std::string query =
            "WITH __posts AS ("
            "    SELECT "
            "        m.latitude AS latitude, "
            "        m.longitude AS longitude, "
            "        c.id AS categoria_id, "
            "        c.nome AS categoria_nome, "
            "        TO_CHAR(TO_TIMESTAMP(sp.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em "
            "    FROM subreddit_post sp "
            "    LEFT JOIN municipios m ON sp.municipio_id = m.geocodigo "
            "    LEFT JOIN subreddit_post_categoria spc ON sp.id = spc.post_id "
            "    LEFT JOIN categoria c ON c.id = spc.categoria_id "
            "    WHERE spc.peso IS NOT NULL AND c.id = " + std::to_string(categoryId) + " "
            "    UNION "
            "    SELECT "
            "        m.latitude AS latitude, "
            "        m.longitude AS longitude, "
            "        c.id AS categoria_id, "
            "        c.nome AS categoria_nome, "
            "        TO_CHAR(TO_TIMESTAMP(spab.created_utc, 'DD/MM/YYYY HH24:MI:SS'), 'DD/MM/YYYY HH24:MI:SS') AS criado_em "
            "    FROM subreddit_post_arquivo_bruto spab "
            "    LEFT JOIN municipios m ON spab.municipio_id = m.geocodigo "
            "    LEFT JOIN subreddit_post_categoria_arquivo_bruto spcab ON spab.id = spcab.post_id "
            "    LEFT JOIN categoria c ON c.id = spcab.categoria_id "
            "    WHERE spcab.peso IS NOT NULL AND c.id = " + std::to_string(categoryId) + " "
            ") "
            "SELECT * FROM __posts "
            "ORDER BY categoria_id ";

        if (total > 0) {
            query += "LIMIT " + std::to_string(total) + ";";
        }

        pqxx::result res = txn.exec(query);

        for (auto row : res) {
            Posts post(
                row["latitude"].as<double>(),
                row["longitude"].as<double>(),
                row["categoria_id"].as<int>(),
                row["categoria_nome"].as<std::string>(),
                row["criado_em"].as<std::string>()
            );
            posts.push_back(post);
        }

        txn.commit();
    } catch (const std::exception &e) {
        std::cerr << "Erro ao buscar posts por categoria: " << e.what() << std::endl;
    }

    return posts;
}

