#include "DatabaseConnection.h"
#include "RedditsPostsRepository.h"
#include "Posts.h"
#include <iostream>

int main() {
    try {
        DatabaseConnection db;

        RedditsPostsRepository repository(db);
        //std::vector<Posts> posts = repository.fetchPosts(10);
        //std::vector<Posts> posts = repository.fetchPostsByCategoryId(3, 5);
        std::vector<Posts> posts = repository.fetchPostsByDateRange("2021-01-01", "2021-01-31", 5);

        for (const auto& post : posts) {
            std::cout << "Post [Categoria: " << post.getCategoriaNome()
                      << ", Categoria ID: " << post.getCategoriaId()
                      << ", Latitude: " << post.getLatitude()
                      << ", Longitude: " << post.getLongitude()
                      << ", Criado em: " << post.getCriadoEm() << "]"
                      << std::endl;
        }
    } catch (const std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}
