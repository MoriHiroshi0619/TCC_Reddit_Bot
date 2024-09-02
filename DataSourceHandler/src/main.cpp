#include "DatabaseConnection.cpp"

int main() {
    try {
        DatabaseConnection db;
        db.executeQuery("select sub_reddit_name from subreddit_subreddit ss");
    } catch (const std::exception &e) {
        std::cerr << e.what() << std::endl;
    }

    return 0;
}