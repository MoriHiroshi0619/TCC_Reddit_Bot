#include "DatabaseConnection.h"
#include "RedditsPostsRepository.h"
#include "Posts.h"
#include <iostream>

int main() {
    try {
        DatabaseConnection db;

        RedditsPostsRepository repository(db);
        std::vector<Posts> posts = repository.fetchPosts(10);

        for (const auto& post : posts) {
            std::cout << "Post [Categoria: " << post.getCategoriaNome()
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
