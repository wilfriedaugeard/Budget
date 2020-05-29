package sample.Factory;

import sample.Model.Category;
import sample.Model.GroupCategory;
import sample.Model.ICategory;

public class CategoryFactory {
    public static ICategory createCategory(String name){
        return new Category(name);
    }
    public static ICategory createGroupCategory(String name){
        return new GroupCategory(name);
    }
}
