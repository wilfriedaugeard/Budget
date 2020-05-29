package sample.Model;

public class Montant {
    private double value;
    private ICategory category;

    public Montant(ICategory category, double value) {
        this.value = value;
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ICategory getCategory() {
        return category;
    }
}
