import java.util.*;

class TodoManager {
    private int actionIdCounter = 0;
    private int categoryIdCounter = 500;
    private int tagIdCounter = 1000;
    private Map<Integer, List<Integer>> tagIdActionId = new HashMap<>();
    private Map<Integer, List<Integer>> catIdActionId = new HashMap<>();
    private Map<String, Integer> tagNameId = new HashMap<>();
    private Map<String, Integer> catNameId = new HashMap<>();
    private Map<Integer, Todo> idToTodoMap = new HashMap<>();

    void add(Todo todo) {
        todo.setId(actionIdCounter);
        idToTodoMap.put(actionIdCounter, todo);
        for (String tagName : todo.getTags()) {
            if (tagNameId.containsKey(tagName)) {
                int tagId = tagNameId.get(tagName);
                List<Integer> temp = tagIdActionId.get(tagId);
                temp.add(actionIdCounter);
                tagIdActionId.put(tagId, temp);
            } else {
                tagNameId.put(tagName, tagIdCounter);
                List<Integer> temp = new ArrayList<>();
                temp.add(actionIdCounter);
                tagIdActionId.put(tagIdCounter, temp);
                tagIdCounter++;
            }
        }
        if (catNameId.containsKey(todo.getCategory())) {
            int index = catNameId.get(todo.getCategory());
            List<Integer> temp = catIdActionId.get(index);
            temp.add(actionIdCounter);
            catIdActionId.put(index, temp);
        } else {
            catNameId.put(todo.getCategory(), categoryIdCounter);
            List<Integer> temp = new ArrayList<>();
            temp.add(actionIdCounter);
            catIdActionId.put(categoryIdCounter, temp);
            categoryIdCounter++;
        }
        actionIdCounter++;
    }

    void updateTodo(int id, String action) throws InvalidIdException {
        if (!idToTodoMap.containsKey(id)) {
            throw new InvalidIdException("Enter a valid todo id");
        }
        idToTodoMap.get(id).setAction(action);
    }

    void deleteTodo(int id) throws InvalidIdException {
        if (!idToTodoMap.containsKey(id)) {
            throw new InvalidIdException("Enter a valid todo id");
        }
        idToTodoMap.remove(id);
    }

    void displayUsingCategory(String category) {
        if (catNameId.containsKey(category)) {
            for (Integer i : catIdActionId.get(catNameId.get(category))) {
                if (idToTodoMap.get(i) != null) {
                    System.out.println(idToTodoMap.get(i));
                }
            }
        } else {
            System.out.println("No action found related to this category:\t" + category);
        }
    }

    void displayUsingTags(String tag) {
        if (tagNameId.containsKey(tag)) {
            for (Integer i : tagIdActionId.get(tagNameId.get(tag))) {
                if (idToTodoMap.get(i) != null) {
                    System.out.println(idToTodoMap.get(i));
                }
            }
        } else {
            System.out.println("No action found related to this tag " + tag);
        }
    }

    void displayAll() {
        for (Todo todo : idToTodoMap.values()) {
            System.out.println(todo.getId() + "\t\t" + todo.getAction() + "\t\t" + todo.getCategory() + "\t\t" + todo.getTags());
        }
    }

    Collection<Todo> getTodos(){
        return idToTodoMap.values();
    }

    void completedTodo(int id) {
        for (Todo todo : idToTodoMap.values()) {
            if (todo.getId() == id) {
                todo.check = true;
            }
        }
    }

    void viewCompletedTodos() {
        for (Todo todo : idToTodoMap.values()) {
            if (todo.check) {
                System.out.println(todo.getId() + "Action: " + todo.getAction() + "\t\tcategory: " + todo.getCategory() + "\t\ttags" + todo.getTags());
            }
        }
    }
}