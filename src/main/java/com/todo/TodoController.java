package com.todo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class TodoController {

    private TodoStore todoStore=new TodoStore();

    @GetMapping("/get")
    public String getTodo(){
        Todo todoList;
        List<String> tags= Arrays.asList("work","project");
        todoList=new Todo("chennai","work",tags,false);
        return todoList.toString();
    }

    @GetMapping("/all")
    public String getAllActions() throws SQLException {
        String todos = String.valueOf(todoStore.displayAllActions());
        return todos;
    }

    @GetMapping("/tags")
    public List<String> getTags() throws SQLException {
        List<String> tags = todoStore.displayExistingTags();
        return tags;
    }

    @GetMapping("/categories")
    public List<String> getCategories() throws SQLException {
        List<String> categories = todoStore.displayExistingCategories();
        return categories;
    }


}
