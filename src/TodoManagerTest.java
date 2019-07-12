import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoManagerTest {
    TodoManager todoManager=new TodoManager();

    @Test
    public void testForAddingTodo(){
        Todo todo=new Todo("Go to chennai","travel", Arrays.asList("office","work"),false);
        todoManager.add(todo);
        for (Todo todo_iterator: todoManager.getTodos()) {
            assertEquals(todo_iterator, todo);
        }
    }

    @Test
    public void testForUpdatingTodo() throws InvalidIdException {
        Todo todo=new Todo("Go to chennai","travel", Arrays.asList("office","work"),false);
        todoManager.add(todo);
        todoManager.updateTodo(0,"Go to Bangalore");
        for (Todo todo_iterator: todoManager.getTodos()) {
            assertEquals(todo_iterator, todo);
        }
    }

    @Test
    public void testForUpdatingTodoWithInvalidId() {
        Todo todo=new Todo("Go to chennai","travel", Arrays.asList("office","work"),false);
        todoManager.add(todo);
        assertThrows(InvalidIdException.class,()->todoManager.updateTodo(10,"drawing"));
    }

    @Test
    public void testForDeleteTodo() {
        Todo todo=new Todo("Go to chennai","travel", Arrays.asList("office","work"),false);
        todoManager.add(todo);
        assertThrows(InvalidIdException.class,()->todoManager.deleteTodo(10));
    }
}