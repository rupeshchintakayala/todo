package com.todo;

public class Search {
    private String Query;
    private String type;

    public Search(String query, String type) {
        Query = query;
        this.type = type;
    }

    public String getQuery() {
        return Query;
    }

    public void setQuery(String query) {
        Query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
