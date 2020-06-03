package sample.Model;

import java.util.ArrayList;

public class Month implements IPeriode{
    private String name;
    private ArrayList<Montant> depenses;
    private ArrayList<Montant> revenues;
    private ArrayList<Montant> charges;
    private double epargne;

    public Month(String name) {
        this.name = name;
        this.depenses = new ArrayList<>();
        this.revenues = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.epargne = 0;
    }

    @Override
    public double getRevenues() {
        double total = 0;
        for(Montant m : revenues){
            total += m.getValue();
        }
        return total;
    }

    @Override
    public double getDepenses() {
        double total = 0;
        for(Montant m : depenses){
            total += m.getValue();
        }
        return total;
    }



    /* Getters and Setters */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEpargne() {
        return epargne;
    }

    @Override
    public double getBudget() {
        double res = 0;
        boolean equal = false;
        for(Montant c : charges){
            for(Montant d : depenses){
                if(c.getCategory().equals(d.getCategory()))
                   equal = true;
                res += d.getValue();
            }
            if(!equal)
                res+= c.getValue();
        }
        return getRevenues()-res;
    }

    @Override
    public void addRevenues(Montant r) {
        revenues.add(r);
    }

    @Override
    public void addDepenses(Montant d) {
        depenses.add(d);
    }

    @Override
    public void addEpargne(double e) {
        epargne += e;
    }

    @Override
    public void addCharges(Montant c) {
        charges.add(c);
    }
    @Override
    public void removeCharges(Montant c){
        charges.remove(c);
    }

    @Override
    public void addPeriode(IPeriode periode) {
        return;
    }

    @Override
    public void removePeriode(IPeriode periode) {
        return;
    }



    @Override
    public ArrayList<IPeriode> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public IPeriode getChild(int n) {
        return null;
    }
}
