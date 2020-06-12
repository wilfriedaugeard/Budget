package sample.Model;

import java.util.ArrayList;

public interface ICategory {
    public void add(ICategory category);
    public void add(int index, ICategory category);
    public void remove(ICategory category);
    public ArrayList<ICategory> getChildren();
    public ICategory getChild(int n);

    public String getName();
}
