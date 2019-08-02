package com.todo;

import java.sql.SQLException;
import java.util.*;

public class TodoConsole {

    public static void main(String[] args) throws SQLException {
        int choice;
        Scanner sc = new Scanner(System.in);
        String input;
        int index;
        Todo todo;
        TodoManager todoManager = new TodoManager();
        TodoStore todoStore = new TodoStore();
        System.out.println(todoStore.initialize());
        while (true) {
            System.out.println("Choose an action");
            System.out.println("1) Add Todo");
            System.out.println("2) Update Todo");
            System.out.println("3) Delete Todo");
            System.out.println("4) Check an Action");
            System.out.println("5) Display Todo using Category");
            System.out.println("6) Display Todo using Tag");
            System.out.println("7) View Completed actions");
            System.out.println("8) Display all actions");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    String categoryName = " ";
                    int catId = 0;
                    List<String> tagList;
                    List<String> tagNameList;
                    List<Integer> tagIdList;
                    //Enter Action
                    System.out.println("Enter the action");
                    String act = sc.nextLine();

                    //Enter Category
                    System.out.println("Enter:\n1 to choose from existing categories\n2 to enter new category");
                    int choiceForCategory = sc.nextInt();
                    sc.nextLine();
                    if (choiceForCategory == 1) {
                        List<String> categoryList = todoStore.displayExistingCategories();
                        for (String category : categoryList) {
                            System.out.println(category);
                        }
                        catId = sc.nextInt();
                        categoryName = todoStore.getCategory(catId);
                        sc.nextLine();
                    } else if (choiceForCategory == 2) {
                        System.out.println("Enter new category name:");
                        categoryName = sc.nextLine();
                    }

                    //Enter Tags
                    System.out.println("Enter:\n1 to choose from existing tags\n2 to enter new tag");
                    int choiceForTags;
                    choiceForTags = sc.nextInt();
                    sc.nextLine();
                    if (choiceForTags == 1) {
                        List<String> tagsList = todoStore.displayExistingTags();
                        for (String tag : tagsList) {
                            System.out.println(tag);
                        }
                        System.out.println("Enter your choice by comma separated id's:");
                        String tagLine = sc.nextLine();
                        tagList = Arrays.asList(tagLine.split(","));
                        tagIdList = new ArrayList<Integer>();
                        for (String tagid : tagList) {
                            tagIdList.add(Integer.valueOf(tagid));
                        }
                        tagNameList = todoStore.getTags(tagIdList);
                        System.out.println(tagNameList);
                        todo = new Todo(act, categoryName, tagNameList, false);
                        int todoId;
                        if(choiceForCategory==1){
                            todo.setCategoryId(catId);
                            todo.setCategoryName(todoStore.getCategory(catId));
                            todo.setTagIdList(tagIdList);
                            todo.setTagNames(todoStore.getTags(tagIdList));
                            todoId = todoStore.add(todo);
                            todo.setTodoId(todoId);
                            todoManager.add(todo);
                        }else{
                            todo.setCategoryId(catId);
                            todo.setCategoryName(categoryName);
                            todo.setTagIdList(tagIdList);
                            todo.setTagNames(todoStore.getTags(tagIdList));
                            todoId = todoStore.add(todo);
                            todo.setTodoId(todoId);
                            todoManager.add(todo);
                        }
                        todo.setTodoId(todoId);
                    }
                    if (choiceForTags == 2) {
                        System.out.println("Enter your choice by comma separated values:");
                        String tagLine = sc.nextLine();
                        tagNameList = Arrays.asList(tagLine.split(","));
                        todo = new Todo(act, categoryName, tagNameList, false);
                        int todoId;
                        if(choiceForCategory==1){
                            todo.setCategoryId(catId);
                            todo.setCategoryName(todoStore.getCategory(catId));
                            todo.setTagIdList(null);
                            todo.setTagNames(tagNameList);
                            todoId = todoStore.add(todo);
                            todo.setTodoId(todoId);
                            todoManager.add(todo);
                        }
                        else{
                            todo.setCategoryId(catId);
                            todo.setCategoryName(categoryName);
                            todo.setTagIdList(null);
                            todo.setTagNames(tagNameList);
                            todoId = todoStore.add(todo);
                            todo.setTodoId(todoId);
                            todoManager.add(todo);
                        }

                    }
                    System.out.println("A new Todo is added");
                    break;
                case 2:
                    todoManager.displayAll();
                    List<String> listOfActions = todoStore.displayAllActions();
                    for (String actions : listOfActions) {
                        System.out.println(actions);
                    }
                    System.out.print("Enter id of todo to update: \t");
                    index = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the new Action: \t");
                    input = sc.nextLine();
                    todoStore.updateTodo(index, input);
                    try {
                        todoManager.updateTodo(index, input);
                    } catch (InvalidIdException e) {
                        System.out.println(e.toString());
                    }
                    break;
                case 3:
                    todoManager.displayAll();
                    listOfActions = todoStore.displayAllActions();
                    for (String actions : listOfActions) {
                        System.out.println(actions);
                    }
                    System.out.print("Enter id of todo to delete: \t");
                    index = sc.nextInt();
                    sc.nextLine();
                    try {
                        todoManager.deleteTodo(index);
                    } catch (InvalidIdException e) {
                        System.out.println(e.toString());
                    }
                    System.out.println(todoStore.deleteTodo(index));
                    break;
                case 4:
                    listOfActions = todoStore.displayAllActions();
                    for (String actions : listOfActions) {
                        System.out.println(actions);
                    }
                    System.out.println("Enter index of action to check");
                    index = sc.nextInt();
                    todoManager.completedTodo(index);
                    break;
                case 5:
                    System.out.println("Enter todo category: \t");
                    input = sc.nextLine();
                    System.out.println("Actions that are related to the category " + input + " are:");
                    List<String> searchResultListForCategory = todoStore.findByCategory(input);
                    for (String actions : searchResultListForCategory) {
                        System.out.println(actions);
                    }
                    todoManager.displayUsingCategory(input);
                    break;
                case 6:
                    System.out.println("Enter todo tag: \t");
                    input = sc.nextLine();
                    System.out.println("Actions that are related to the tag " + input + " are:");
                    List<String> searchResultListForTag = todoStore.findByTag(input);
                    for (String actions : searchResultListForTag) {
                        System.out.println(actions);
                    }
                    todoManager.displayUsingTags(input);
                    break;
                case 7:
                    System.out.println("completed actions are:");
                    todoManager.viewCompletedTodos();
                    break;
                case 8:
                    System.out.println("Program-");
                    todoManager.displayAll();
                    System.out.println("TodoStore-");
                    listOfActions = todoStore.displayAllActions();
                    for (String actions : listOfActions) {
                        System.out.println(actions);
                    }
                    System.out.println(todoStore.displayExistingCategories());
                    todoStore.displayExistingTodoTagBridge();
                    break;
                default:
                    System.out.println("Enter a valid number between 1-8");

            }
        }
    }
}
