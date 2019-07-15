package com.todo;
import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class TodoManagerTest {
    TodoManager todoManager = new TodoManager();

    @Test
    public void testForAddingTodo() {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todo = todoManager.add(todo);
        boolean match = false;
        for (Todo todo_iterator : todoManager.getTodos()) {
            if (todo.getId() == todo_iterator.getId()) {
                match = true;
            }
        }
        assertTrue(match);
    }

    @Test
    public void testForUpdatingTodo() throws InvalidIdException {
        Todo todo = new Todo("Go to Vizag", "travel", Arrays.asList("home", "vacation"), false);
        todo = todoManager.add(todo);
        boolean match = false;
        todoManager.updateTodo(0, "Go to Bangalore");
        for (Todo todo_iterator : todoManager.getTodos()) {
            if (todo_iterator.getId() == (todo.getId())) {
                if (todo_iterator.getAction().equals(todo.getAction())) {
                    match = true;
                }
            }
        }
        assertTrue(match);
    }

    @Test (expected = InvalidIdException.class)
    public void testForUpdatingTodoWithInvalidId() throws InvalidIdException {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todoManager.add(todo);
       todoManager.updateTodo(10, "drawing");
    }

    @Test (expected = InvalidIdException.class)
    public void testForDeleteTodoWithInvalidId() throws InvalidIdException {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todoManager.add(todo);
       todoManager.deleteTodo(10);
    }

    @Test
    public void testForDeletingTodo() throws InvalidIdException {
        Todo todo = new Todo("Go to vizag", "travel", Arrays.asList("home", "vacation"), false);
        todo = todoManager.add(todo);
        todoManager.deleteTodo(0);
        boolean match = false;
        for (Todo todo_iterator : todoManager.getTodos()) {
            if (todo_iterator.getId() == todo.getId()) {
                match = true;
            }
        }
        assertFalse(match);
    }

    @Test
    public void testForFindByCategory() {
        Todo todo = new Todo("Go to Bangalore", "travel", Arrays.asList("fun", "party"), false);
        todo = todoManager.add(todo);
        boolean match = false;
        String findByCategory = "travel";
        todoManager.displayUsingCategory(findByCategory);
        for (Todo todo_iterator : todoManager.getTodos()) {
            if (todo_iterator.getCategory().equals(findByCategory)) {
                if (todo_iterator.getAction().equals(todo.getAction())) {
                    match = true;
                }
            }
        }
        assertTrue(match);
    }

    @Test
    public void testForFindByTags() {
        Todo todo = new Todo("Go to office", "life", Arrays.asList("project", "work"), false);
        String findByTag = "work";
        todoManager.add(todo);
        todoManager.displayUsingTags(findByTag);
        boolean match = false;
        for (Todo todo_iterator : todoManager.getTodos()) {
            for (String tag : todo_iterator.getTags()) {
                if (tag.equals(findByTag)) {
                    if (todo_iterator.getAction().equals(todo.getAction())) {
                        match = true;
                    }
                }
            }
        }
        assertTrue(match);
    }
}