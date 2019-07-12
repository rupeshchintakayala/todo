import java.util.*;

class Todo {
    private int id;
    private String action;
    private String categoryName;
    private List<String> tags;
    boolean check;
    Todo(int id, String act, String cat, List<String> tag, boolean check){
        this.id = id;
        action=act;
        categoryName=cat;
        tags=tag;
        this.check=check;
    }
    void setAction(String act){
        action=act;
    }

    String getAction(){
        return action;
    }

    String getCategory(){
        return categoryName;
    }

    List<String> getTags(){
        return tags;
    }

    int getId() {return id;}
}

