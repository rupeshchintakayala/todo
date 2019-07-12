import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TodoManagerTest {
    TodoManager todoManager = new TodoManager();

    @Test
    public void testForAddingTodo() {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todoManager.add(todo);
        for (Todo todo_iterator : todoManager.getTodos()) {
            assertEquals(todo_iterator, todo);
        }
    }

    @Test
    public void testForUpdatingTodo() throws InvalidIdException {
        Todo todo = new Todo("Go to vizag", "travel", Arrays.asList("home", "vacation"), false);
        todoManager.add(todo);
        todoManager.updateTodo(0, "Go to Bangalore");
        for (Todo todo_iterator : todoManager.getTodos()) {
            if(todo_iterator.getId()==0){
                assertEquals(todo_iterator.getAction(), todo.getAction());
            }
        }
    }

    @Test
    public void testForUpdatingTodoWithInvalidId() {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todoManager.add(todo);
        assertThrows(InvalidIdException.class, () -> todoManager.updateTodo(10, "drawing"));
    }

    @Test
    public void testForDeleteTodoWithInvalidId() {
        Todo todo = new Todo("Go to chennai", "travel", Arrays.asList("office", "work"), false);
        todoManager.add(todo);
        assertThrows(InvalidIdException.class, () -> todoManager.deleteTodo(10));
    }

    @Test
    public void testForFindByCategory(){
        Todo todo = new Todo("Go to Bangalore", "travel", Arrays.asList("fun", "party"), false);
        todoManager.add(todo);
        todoManager.displayUsingCategory(todo.getCategory());
        for (Todo todo_iterator : todoManager.getTodos()) {
            assertEquals(todo_iterator.getCategory(), todo.getCategory());
        }
    }

    @Test
    public void testForFindByTags(){
        Todo todo = new Todo("Go to office", "life", Arrays.asList("project", "learn"), false);
        todoManager.add(todo);
        todoManager.displayUsingTags("work");
        for (Todo todo_iterator : todoManager.getTodos()) {
            for(String tag: todo_iterator.getTags()){
                if(tag=="office"){
                    assertEquals(tag,"office");
                }
            }
        }
    }
}