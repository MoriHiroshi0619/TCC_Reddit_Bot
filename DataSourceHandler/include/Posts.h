#ifndef POSTS_H
#define POSTS_H

#include <string>

class Posts {
public:
    Posts(double latitude, double longitude, int categoriaId, const std::string& categoriaNome, const std::string& criadoEm);

    double getLatitude() const;
    double getLongitude() const;
    int getCategoriaId() const;
    std::string getCategoriaNome() const;
    std::string getCriadoEm() const;

private:
    double latitude;
    double longitude;
    int categoriaId;
    std::string categoriaNome;
    std::string criadoEm;
};
struct CategoriaPopular {
    int categoria_id;
    std::string categoria_nome;
    int total;

    CategoriaPopular(int id, const std::string& nome, int count)
        : categoria_id(id), categoria_nome(nome), total(count) {}
};
#endif
