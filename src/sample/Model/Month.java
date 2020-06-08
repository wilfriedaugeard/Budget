package sample.Model;

import java.util.ArrayList;

public class Month implements IPeriode{
    private String name;
    private ArrayList<Montant> depenses;
    private ArrayList<Montant> revenues;
    private ArrayList<Montant> revenuesRec;
    private ArrayList<Montant> charges;
    private double epargne;

    public Month(String name) {
        this.name = name;
        this.depenses = new ArrayList<>();
        this.revenues = new ArrayList<>();
        this.revenuesRec = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.epargne = 0;
    }

    @Override
    public double getRevenuesValue() {
        double total = 0;
        for(Montant m : revenues){
            total += m.getValue();
        }
        for(Montant m : revenuesRec){
            total += m.getValue();
        }
        return total;
    }

    @Override
    public ArrayList<Montant> getRevenues() {
        return revenues;
    }

    @Override
    public ArrayList<Montant> getRevenuesRec(){
        return revenuesRec;
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
        double res = getRevenuesValue();
        for(Montant m : depenses){
            res -= m.getValue();
        }
        for(Montant m : charges){
            res -= m.getValue();
        }
        return res;
    }

    @Override
    public ArrayList<Montant> getCharges() {
        return charges;
    }

    @Override
    public void addRevenues(Montant r) {
        revenues.add(r);
    }

    @Override
    public void addRevenuesRec(Montant r) {
        for(Montant m : getRevenuesRec()){
            if(m.getCategory().getName().equals(r.getCategory().getName())){
                if(m.getValue() == r.getValue()){
                    return;
                }
                int i = revenuesRec.indexOf(m);
                revenuesRec.set(i, r);
                return;
            }
        }
        revenuesRec.add(r);
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
        for(Montant m : getCharges()){
            if(m.getCategory().getName().equals(c.getCategory().getName())){
                if(m.getValue() == c.getValue()){
                    return;
                }
                int i = charges.indexOf(m);
                charges.set(i, c);
                return;
            }
        }
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
    public void removeRevenu(Montant r){
        revenues.remove(r);
    }

    @Override
    public void removeRevenuRec(Montant r){
        revenuesRec.remove(r);
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
