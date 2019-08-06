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
    public Todo todoSubmit(@RequestBody Todo todo) throws SQLException {
        todoStore.add(todo);
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

    @GetMapping("/findByCategory")
    public String findByCategory(@RequestBody Search search) throws SQLException {
        String searchResultsForCategory= String.valueOf(todoStore.getJSONForSearch(search.getType(),search.getQuery()));
        return searchResultsForCategory;
    }

    @GetMapping("/findByTag")
    public String findByTag(@RequestBody Search search) throws SQLException {
        String searchResultsForTag= String.valueOf(todoStore.getJSONForSearch(search.getType(),search.getQuery()));
        return searchResultsForTag;
    }

}
