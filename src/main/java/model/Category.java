package model;

public class Category {
    private int id;
    private Integer parentID;
    private String name;

    public Category() {}

    public Category(int id, Integer parentID, String name) {
        this.id = id;
        this.parentID = parentID;
        this.name = name;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getParentId() { return parentID; }
    public void setParentId(Integer parentID) { this.parentID = parentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}