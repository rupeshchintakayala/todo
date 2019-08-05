package com.todo;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private TodoStore todoStore=new TodoStore();

    private List<String> todos = new ArrayList<>();
    @GetMapping
    public String getAllActions() throws SQLException {
        String todos = String.valueOf(todoStore.getJSON("all"));
        return todos;
    }

    @GetMapping("/tags")
    public String getTags() throws SQLException {
        String tags = String.valueOf(todoStore.getJSON("tags"));
        return tags;
    }

    @GetMapping("/categories")
    public String getCategories() throws SQLException {
        String categories = String.valueOf(todoStore.getJSON("category"));
        return categories;
    }

    @PostMapping
    public Todo todoSubmit(@RequestBody Map<String,String> data) throws SQLException {
        List<Integer> tagIdList=new ArrayList<>();
        String categoryName=todoStore.getCategoryName(Integer.parseInt(data.get("categoryId")));
        Todo todo=new Todo(data.get("todoName"),categoryName, Arrays.asList(data.get("tags").split(",")),false);
        todoStore.add(todo);
        for (String tag: Arrays.asList(data.get("tags").split(","))) {
            tagIdList.add(todoStore.getTagId(tag));
        }
        return todo;
    }

    @DeleteMapping("/{todoId}")
    public String deleteTodo(@PathVariable("todoId") int todoId) throws SQLException {
        todoStore.deleteTodo(todoId);
        return "Todo Deleted";
    }

    @PutMapping("/{todoId}")
    public String update(@RequestBody Map<String,String> data,@PathVariable("todoId") int todoId) throws SQLException {
        todoStore.updateTodo(todoId,data.get("todoName"));
        return "Todo Updated";
    }
}
