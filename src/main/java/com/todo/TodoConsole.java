package com.todo;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TodoConsole {

    public static void main(String[] args) throws SQLException {
        int choice;
        Scanner sc = new Scanner(System.in);
        String str;
        int index;
        TodoManager todoManager = new TodoManager();
        TodoTable todoTable=new TodoTable();

        todoTable.dropTable("todoTable");
        todoTable.dropTable("tagTable");
        todoTable.dropTable("categoryTable");
        todoTable.dropTable("todoIdTagId");

        todoTable.connectToDatabase();

        todoTable.createTodoTable();
        todoTable.createTagTable();
        todoTable.createCategoryTable();
        todoTable.createTodoIdTagIdTable();

        todoTable.existingCategories();
        todoTable.existingTags();

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
                    String cat="";
                    int todoId=0;
                    int catId=0;
                    int tagId=0;
                    System.out.println("Enter the action");
                    String act = sc.nextLine();
                    System.out.println("Enter:\n1 to choose from existing categories\n2 to enter new category");
                    int choiceForCategory=sc.nextInt();
                    sc.nextLine();
                    if(choiceForCategory==1){
                        todoTable.displayExistingCategories();
                        catId=sc.nextInt();
                        sc.nextLine();
                    }
                    else if(choiceForCategory==2){
                        System.out.println("Enter new category name:");
                        cat = sc.nextLine();
                        catId=todoTable.insertValuesToCategoryTable(cat);
                    }
                    todoId=todoTable.insertValuesToTodoTable(act,catId);
                    todoTable.displayExistingTodos();

                    System.out.println("Enter:\n1 to choose from existing tags\n2 to enter new tag");
                    int choiceForTags=sc.nextInt();
                    sc.nextLine();
                    if(choiceForTags==1){
                        todoTable.displayExistingTags();
                        System.out.println("Enter your choice by comma separated id's:");
                        String tagLine=sc.nextLine();
                        List<String> tagIdList=Arrays.asList(tagLine.split(","));
                        for(String tagIds:tagIdList){
                            todoTable.insertValuesToTodoIdTagIdTable(todoId, Integer.parseInt(tagIds));
                        }
                    }
                    else if(choiceForTags==2){
                        System.out.println("Enter your choice by comma separated values:");
                        String tagLine=sc.nextLine();
                        List<String> tagNameList=Arrays.asList(tagLine.split(","));
                        for(String tagNames:tagNameList){
                            int tagIdForNewTag=todoTable.insertValuesToTagTable(tagNames);
                            todoTable.insertValuesToTodoIdTagIdTable(todoId,tagIdForNewTag);
                        }

                    }
//                    List<String> tag;
//                    System.out.println("Enter tags using comma's");
//                    line = sc.nextLine();
//                    tag = Arrays.asList(line.split(","));
//                    Todo todo = new Todo(act, cat, tag, false);
////                    todo.setId(id);
//                    todoManager.add(todo);
                    break;
                case 2:
                    todoManager.displayAll();
                    System.out.print("Enter id of todo to update: \t");
                    index = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the new Action: \t");
                    str = sc.nextLine();
                    todoTable.updateTodo(index,str);
                    try {
                        todoManager.updateTodo(index, str);
                    } catch (InvalidIdException e) {
                        System.out.println(e.toString());
                    }

                    break;
                case 3:
                    todoManager.displayAll();
                    System.out.print("Enter id of todo to delete: \t");
                    index = sc.nextInt();
                    sc.nextLine();
                    try {
                        todoManager.deleteTodo(index);
                    } catch (InvalidIdException e) {
                        System.out.println(e.toString());
                    }
                    todoTable.deleteTodo(index);
                    break;
                case 4:
                    System.out.println("Enter index of action to check");
                    index = sc.nextInt();
                    todoManager.completedTodo(index);
                    break;
                case 5:
                    System.out.println("Enter todo category: \t");
                    str = sc.nextLine();
                    todoTable.findByCategory(str);
//                    todoManager.displayUsingCategory(str);
                    break;
                case 6:
                    System.out.println("Enter todo tag: \t");
                    str = sc.nextLine();
                    todoTable.findByTag(str);
//                    todoManager.displayUsingTags(str);
                    break;
                case 7:
                    System.out.println("completed actions are:");
                    todoManager.viewCompletedTodos();
                    break;
                case 8:
                    System.out.println("Program-");
//                    todoManager.displayAll();
//                    todoTable.displayExistingCategories();
//                    todoTable.displayExistingTags();
//                    todoTable.displayExistingTodoTagBridge();
                    todoTable.displayAllActions();
                    break;
                default:
                    System.out.println("Enter a valid number between 1-8");

            }
        }

    }
}
