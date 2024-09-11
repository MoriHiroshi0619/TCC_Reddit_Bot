#ifndef REDDITSPOSTSREPOSITORY_H
#define REDDITSPOSTSREPOSITORY_H

#include <vector>
#include "DatabaseConnection.h"
#include "Posts.h"

class RedditsPostsRepository {
public:
    explicit RedditsPostsRepository(DatabaseConnection& dbConnection);

    std::vector<Posts> fetchPosts(int total = -1);

private:
    DatabaseConnection& dbConnection;
};

#endif
