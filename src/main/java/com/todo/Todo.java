package com.todo;

import java.util.*;

class Todo {
    private int todoId;
    private int categoryId;
    private List<Integer> tagId;
    private String action;
    private String categoryName;
    private List<String> tags;
    private List<Integer> tagIdList;

    boolean check;

    Todo(String act, String cat, List<String> tag, boolean check) {
        action = act;
        categoryName = cat;
        tags = tag;
        this.check = check;
    }

    public Todo() {

    }

    public Todo(int todoId, int categoryId, List<Integer> tagId, String action, String categoryName, List<String> tags, List<Integer> tagIdList, boolean check) {
        this.todoId = todoId;
        this.categoryId = categoryId;
        this.tagId = tagId;
        this.action = action;
        this.categoryName = categoryName;
        this.tags = tags;
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

    public List<Integer> getTagId() {
        return tagId;
    }

    public void setTagId(List<Integer> tagId) {
        this.tagId = tagId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    void setAction(String act) {
        action = act;
    }

    String getAction() {
        return action;
    }

    List<String> getTags() {
        return tags;
    }

    int getTodoId() {
        return todoId;
    }
}

