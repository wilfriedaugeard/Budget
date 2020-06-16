package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Month implements IPeriode, Serializable {
    private String name;
    private ArrayList<Montant> depenses;
    private ArrayList<Montant> revenues;
    private ArrayList<Montant> revenuesRec;
    private ArrayList<Montant> charges;
    private double budget;
    private double epargne;

    public Month(String name) {
        this.name = name;
        this.depenses = new ArrayList<>();
        this.revenues = new ArrayList<>();
        this.revenuesRec = new ArrayList<>();
        this.charges = new ArrayList<>();
        this.epargne = 0;
        this.budget = 0;
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
    public double getDepensesValue() {
        double total = 0;
        for(Montant m : depenses){
            total += m.getValue();
        }
        return total;
    }

    @Override
    public ArrayList<Montant> getDepenses(){
        return depenses;
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
        return this.budget;
    }

    @Override
    public void setBudget(double m){
        budget+=m;
    }

    @Override
    public void setEpargne(double m) {
        epargne+=m;
    }

    @Override
    public ArrayList<Montant> getCharges() {
        return charges;
    }

    @Override
    public void addRevenues(Montant r) {
        revenues.add(r);
        budget+=r.getValue();
    }

    @Override
    public void addRevenuesRec(Montant r) {
        for(Montant m : getRevenuesRec()){
            if(m.getCategory().getName().equals(r.getCategory().getName())){
                if(m.getValue() == r.getValue()){
                    return;
                }
                removeRevenuRec(m);
                revenuesRec.add(r);
                budget+=r.getValue();
                return;
            }
        }
        revenuesRec.add(r);
        budget+=r.getValue();
    }

    @Override
    public void addDepenses(Montant d) {
        depenses.add(d);
        budget-=d.getValue();
    }

    @Override
    public void addEpargne(double e) {
        epargne += e;
    }

    @Override
    public double getChargeValue(){
        double charges = 0;
        for(Montant m : getCharges()){
            charges += m.getValue();
        }
        return charges;
    }

    @Override
    public double getTotalDepenses(){
        return getDepensesValue()+getChargeValue();
    }

    @Override
    public void addCharges(Montant c) {
        for(Montant m : getCharges()){
            if(m.getCategory().getName().equals(c.getCategory().getName())){
                if(m.getValue() == c.getValue()){
                    return;
                }
                removeCharges(m);
                charges.add(c);
                budget-=c.getValue();
                return;
            }
        }
        charges.add(c);
        budget-=c.getValue();
    }


    @Override
    public void removeCharges(Montant c){
        charges.remove(c);
        budget+=c.getValue();
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
        budget-=r.getValue();
    }

    @Override
    public void removeRevenuRec(Montant r){
        revenuesRec.remove(r);
        budget-=r.getValue();
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
