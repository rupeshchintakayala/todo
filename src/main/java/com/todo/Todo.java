package com.todo;

import java.util.*;

class Todo {
    private int todoId;
    private int categoryId;
    private String todoName;
    private String categoryName;
    private List<String> tagNames;
    private List<Integer> tagIdList;

    boolean check;

    Todo(String act, String cat, List<String> tag, boolean check) {
        todoName = act;
        categoryName = cat;
        tagNames = tag;
        this.check = check;
    }

    public Todo() {

    }

    public Todo(int todoId, int categoryId, String todoName, String categoryName, List<String> tagNames, List<Integer> tagIdList, boolean check) {
        this.todoId = todoId;
        this.categoryId = categoryId;
        this.todoName = todoName;
        this.categoryName = categoryName;
        this.tagNames = tagNames;
        this.tagIdList = tagIdList;
        this.check = check;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public List<Integer> getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(List<Integer> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    int setTodoId(int todoId) {
        this.todoId = todoId;
        return todoId;
    }

    void setTodoName(String act) {
        todoName = act;
    }

    public String getTodoName() {
        return todoName;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public int getTodoId() {
        return todoId;
    }
}

