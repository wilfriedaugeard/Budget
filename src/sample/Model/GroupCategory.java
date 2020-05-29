package sample.Model;

import java.util.ArrayList;

public class GroupCategory implements ICategory{
    private String name;
    private ArrayList<ICategory> list;

    public GroupCategory(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    @Override
    public void add(ICategory category) {
        list.add(category);
    }

    @Override
    public void remove(ICategory category) {
        list.remove(category);
    }

    @Override
    public ArrayList<ICategory> getChildren() {
        return list;
    }

    @Override
    public ICategory getChild(int n) {
        return list.get(n);
    }

    @Override
    public String getName() {
        return name;
    }
}
