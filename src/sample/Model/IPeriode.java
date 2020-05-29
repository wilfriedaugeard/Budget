package sample.Model;

import java.util.ArrayList;

public interface IPeriode {
    public String getName();
    public double getRevenues();
    public double getDepenses();
    public double getEpargne();

    public void addRevenues(Montant r);
    public void addDepenses(Montant d);
    public void addEpargne(double e);

    /* Composite pattern */
    public void addPeriode(IPeriode periode);
    public void removePeriode(IPeriode periode);
    public ArrayList<IPeriode> getChildren();
    public IPeriode getChild(int n);
}
