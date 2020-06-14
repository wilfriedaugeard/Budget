package sample.Factory;

import sample.Model.ICategory;
import sample.Model.Montant;

public class MontantFactory {
    public static Montant createMontant(ICategory category, double value){
        return new Montant(category, value);
    }
}
