package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupCategory implements ICategory, Serializable {
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
    public void add(int index, ICategory category){
        list.add(index, category);
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
