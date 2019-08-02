package com.todo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {

    private TodoStore todoStore=new TodoStore();

    private List<String> todos = new ArrayList<>();

    @GetMapping("/all")
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

    @PostMapping("/addTodo")
    public String handlePostRequest(Todo todo) throws SQLException {
        todoStore.add(todo);
        return "redirect:/addTodo";
    }

    @GetMapping("/addTodo")
    public String handleGetRequest(Model model) {
        model.addAttribute("todos", todos);
        return "redirect:/all";
    }

}
