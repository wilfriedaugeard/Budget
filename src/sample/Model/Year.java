package sample.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Year implements IPeriode, Serializable {
    private String name;
    private ArrayList<IPeriode> list;

    public Year(String name) {
        this.name = name;
        list = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getRevenuesValue() {
        double total = 0;
        for(IPeriode m : list){
            total += m.getRevenuesValue();
        }
        return total;
    }

    @Override
    public ArrayList<Montant> getRevenues() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Montant> getRevenuesRec() {
        return new ArrayList<>();
    }

    @Override
    public double getDepenses() {
        double total = 0;
        for(IPeriode m : list){
            total += m.getDepenses();
        }
        return total;
    }

    @Override
    public double getEpargne() {
        double total = 0;
        for(IPeriode m : list){
            total += m.getEpargne();
        }
        return total;
    }

    @Override
    public double getBudget() {
        return 0;
    }

    @Override
    public ArrayList<Montant> getCharges() {
        return new ArrayList<>();
    }

    @Override
    public void setBudget(double m) {

    }

    @Override
    public void setEpargne(double m) {

    }

    @Override
    public void addRevenues(Montant r) {

    }

    @Override
    public void addRevenuesRec(Montant r) {

    }

    @Override
    public void addDepenses(Montant d) {

    }

    @Override
    public void addEpargne(double e) {

    }

    @Override
    public void addCharges(Montant c) {

    }

    @Override
    public void removeCharges(Montant c) {

    }

    @Override
    public void removeRevenu(Montant r) {

    }

    @Override
    public void removeRevenuRec(Montant r) {

    }

    @Override
    public void addPeriode(IPeriode periode) {
        list.add(periode);
    }

    @Override
    public void removePeriode(IPeriode periode) {
        list.remove(periode);
    }

    @Override
    public ArrayList<IPeriode> getChildren() {
        return list;
    }

    @Override
    public IPeriode getChild(int n) {
        return list.get(n);
    }
}
