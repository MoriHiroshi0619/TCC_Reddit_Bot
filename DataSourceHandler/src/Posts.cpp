#include "Posts.h"

Posts::Posts(double latitude, double longitude, int categoriaId, const std::string& categoriaNome, const std::string& criadoEm)
    : latitude(latitude), longitude(longitude), categoriaId(categoriaId), categoriaNome(categoriaNome), criadoEm(criadoEm) {}

double Posts::getLatitude() const {
    return latitude;
}

double Posts::getLongitude() const {
    return longitude;
}

int Posts::getCategoriaId() const {
    return categoriaId;
}

std::string Posts::getCategoriaNome() const {
    return categoriaNome;
}

std::string Posts::getCriadoEm() const {
    return criadoEm;
}
