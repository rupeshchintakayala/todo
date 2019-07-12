import java.util.*;

public class TodoConsole {

    public static void main(String[] args) throws InvalidIdException {
        int choice;
        Scanner sc = new Scanner(System.in);
        String str;
        int index;
        String line;
        TodoManager todoManager = new TodoManager();
        while (true) {
            System.out.println("Choose an action");
            System.out.println("1) Add Todo");
            System.out.println("2) Update Todo");
            System.out.println("3) Delete Todo");
            System.out.println("4) Check an Action");
            System.out.println("5) Display Todo using Category");
            System.out.println("6) Display Todo using todos Tag");
            System.out.println("7) View Completed actions");
            System.out.println("8) Display all actions");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter the action");
                    String act = sc.nextLine();
                    System.out.println("Enter the category");
                    String cat = sc.nextLine();
                    List<String> tag;
                    System.out.println("Enter tags using comma's");
                    line = sc.nextLine();
                    tag = Arrays.asList(line.split(","));
                    todoManager.add(act, cat, tag);
                    break;
                case 2:
                    todoManager.displayAll();
                    System.out.print("Enter id of todo to update: \t");
                    index = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the new Action: \t");
                    str = sc.nextLine();
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
                    break;
                case 4:
                    System.out.println("Enter index of action to check");
                    index = sc.nextInt();
                    todoManager.completedTodo(index);
                    break;
                case 5:
                    System.out.println("Enter todos category: \t");
                    str = sc.nextLine();
                    todoManager.displayUsingCategory(str);
                    break;
                case 6:
                    System.out.println("Enter todos tag: \t");
                    str = sc.nextLine();
                    todoManager.displayUsingTags(str);
                    break;
                case 7:
                    System.out.println("completed actions are:");
                    todoManager.viewCompletedTodos();
                    break;
                case 8:
                    todoManager.displayAll();
                    break;
                default:
                    System.out.println("Enter a valid number between 1-8");

            }
        }

    }
}
