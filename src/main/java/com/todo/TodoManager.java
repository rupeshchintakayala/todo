package com.todo;

import java.util.*;

class TodoManager {
    private int categoryIdCounter = 500;
    private int tagIdCounter = 1000;
    private Map<Integer, List<Integer>> tagIdActionId = new HashMap<Integer, List<Integer>>();
    private Map<Integer, List<Integer>> catIdActionId = new HashMap<Integer, List<Integer>>();
    private Map<String, Integer> tagNameId = new HashMap<String, Integer>();
    private Map<String, Integer> catNameId = new HashMap<String, Integer>();
    private Map<Integer, Todo> idToTodoMap = new HashMap<Integer, Todo>();

    Todo add(Todo todo) {
        int actionIdCounter = todo.getTodoId();
        idToTodoMap.put(actionIdCounter, todo);
        for (String tagName : todo.getTagNames()) {
            if (tagNameId.containsKey(tagName)) {
                int tagId = tagNameId.get(tagName);
                List<Integer> temp = tagIdActionId.get(tagId);
                temp.add(actionIdCounter);
                tagIdActionId.put(tagId, temp);
            } else {
                tagNameId.put(tagName, tagIdCounter);
                List<Integer> temp = new ArrayList<Integer>();
                temp.add(actionIdCounter);
                tagIdActionId.put(tagIdCounter, temp);
                tagIdCounter++;
            }
        }
        if (catNameId.containsKey(todo.getCategoryName())) {
            int index = catNameId.get(todo.getCategoryId());
            List<Integer> temp = catIdActionId.get(index);
            temp.add(actionIdCounter);
            catIdActionId.put(index, temp);
        } else {
            catNameId.put(String.valueOf(todo.getCategoryId()), categoryIdCounter);
            List<Integer> temp = new ArrayList<Integer>();
            temp.add(actionIdCounter);
            catIdActionId.put(categoryIdCounter, temp);
            categoryIdCounter++;
        }
        actionIdCounter++;
        return todo;
    }

    void updateTodo(int id, String action) throws InvalidIdException {
        if (!idToTodoMap.containsKey(id)) {
            throw new InvalidIdException("Enter a valid todo id");
        }
        idToTodoMap.get(id).setTodoName(action);
    }

    void deleteTodo(int id) throws InvalidIdException {
        if (!idToTodoMap.containsKey(id)) {
            throw new InvalidIdException("Enter a valid todo id");
        }
        idToTodoMap.remove(id);
    }

    void displayUsingCategory(String category) {
        if (catNameId.containsKey(category)) {
            for (Integer i : catIdActionId.get(catNameId.get(category))) {
                if (idToTodoMap.get(i) != null) {
                    System.out.println(idToTodoMap.get(i).getTodoName());
                }
            }
        } else {
            System.out.println("No action found related to this category:\t" + category);
        }
    }

    void displayUsingTags(String tag) {
        if (tagNameId.containsKey(tag)) {
            for (Integer i : tagIdActionId.get(tagNameId.get(tag))) {
                if (idToTodoMap.get(i) != null) {
                    System.out.println(idToTodoMap.get(i).getTodoName());
                }
            }
        } else {
            System.out.println("No action found related to this tag " + tag);
        }
    }

    void displayAll() {
        for (Todo todo : idToTodoMap.values()) {
            System.out.println(todo.getTodoId() + "\t\t" + todo.getTodoName() + "\t\t" + todo.getCategoryName() + "\t\t" + todo.getTagNames());
        }
    }

    Collection<Todo> getTodos() {
        return idToTodoMap.values();
    }

    void completedTodo(int id) {
        for (Todo todo : idToTodoMap.values()) {
            if (todo.getTodoId() == id) {
                todo.check = true;
            }
        }
    }

    void viewCompletedTodos() {
        for (Todo todo : idToTodoMap.values()) {
            if (todo.check) {
                System.out.println(todo.getTodoId() + "Action: " + todo.getTodoName() + "\t\tcategory: " + todo.getCategoryId() + "\t\ttags" + todo.getTagNames());
            }
        }
    }
}