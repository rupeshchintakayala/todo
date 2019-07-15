package com.todo;

import java.util.*;

public class Todo {
    private int id;
    private String action;
    private String categoryName;
    private List<String> tags;
    boolean check;

    Todo(String act, String cat, List<String> tag, boolean check) {
        action = act;
        categoryName = cat;
        tags = tag;
        this.check = check;
    }

    void setId(int id) {
        this.id = id;
    }

    void setAction(String act) {
        action = act;
    }

    String getAction() {
        return action;
    }

    String getCategory() {
        return categoryName;
    }

    List<String> getTags() {
        return tags;
    }

    int getId() {
        return id;
    }
}

