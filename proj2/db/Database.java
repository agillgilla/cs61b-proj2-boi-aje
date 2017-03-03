package db;

import query_handler.Parse;

public class Database {
    public Database() {
        // YOUR CODE HERE
    }

    public String transact(String query) {
        return Parse.parse(query);
    }
}
