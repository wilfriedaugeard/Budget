package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements ICategory, Serializable {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    @Override
    public void add(ICategory category) {

    }

    @Override
    public void add(int index, ICategory category) {

    }

    @Override
    public void remove(ICategory category) {

    }

    @Override
    public ArrayList<ICategory> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public ICategory getChild(int n) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
