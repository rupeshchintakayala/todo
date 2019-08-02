package com.todo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/addTodo")
    public String getTodo(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo";
    }

    @PostMapping("/addTodo")
    public String todoSubmit(@ModelAttribute Todo todo) {
        return "result";
    }

}
