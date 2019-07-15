package main.java.com.todo;

import java.util.*;

public class Todo {
    private int id;
    private String action;
    private String categoryName;
    private List<String> tags;
    boolean check;

    public Todo(String act, String cat, List<String> tag, boolean check) {
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

    public String getAction() {
        return action;
    }

    public String getCategory() {
        return categoryName;
    }

    public List<String> getTags() {
        return tags;
    }

    public int getId() {
        return id;
    }
}

