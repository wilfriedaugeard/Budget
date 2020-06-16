package sample.Model;

import java.util.ArrayList;

public interface IPeriode {
    public String getName();
    public double getRevenuesValue();
    public ArrayList<Montant> getRevenues();
    public ArrayList<Montant> getRevenuesRec();
    public double getDepensesValue();
    public ArrayList<Montant> getDepenses();
    public double getEpargne();
    public double getBudget();
    public ArrayList<Montant> getCharges();
    public double getChargeValue();
    public double getTotalDepenses();

    public void setBudget(double m);
    public void setEpargne(double m);

    public void addRevenues(Montant r);
    public void addRevenuesRec(Montant r);
    public void addDepenses(Montant d);
    public void addEpargne(double e);
    public void addCharges(Montant c);
    public void removeCharges(Montant c);
    public void removeRevenu(Montant r);
    public void removeRevenuRec(Montant r);

    /* Composite pattern */
    public void addPeriode(IPeriode periode);
    public void removePeriode(IPeriode periode);
    public ArrayList<IPeriode> getChildren();
    public IPeriode getChild(int n);
}
