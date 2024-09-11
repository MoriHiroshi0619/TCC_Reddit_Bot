#ifndef REDDITSPOSTSREPOSITORY_H
#define REDDITSPOSTSREPOSITORY_H

#include <vector>
#include "DatabaseConnection.h"
#include "Posts.h"

class RedditsPostsRepository {
public:
    explicit RedditsPostsRepository(DatabaseConnection& dbConnection);

    std::vector<Posts> fetchPosts(int total = -1);

    std::vector<Posts> fetchPostsByCategoryId(int categoryId, int total = 0);

    std::vector<Posts> fetchPostsByDateRange(const std::string& startDate, const std::string& endDate, int total = 0);
private:
    DatabaseConnection& dbConnection;
};

#endif
