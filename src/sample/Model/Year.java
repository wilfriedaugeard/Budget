package sample.Model;

import java.util.ArrayList;

public class Year implements IPeriode{
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
    public double getRevenues() {
        double total = 0;
        for(IPeriode m : list){
            total += m.getRevenues();
        }
        return total;
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
    public void addRevenues(Montant r) {

    }

    @Override
    public void addDepenses(Montant d) {

    }

    @Override
    public void addEpargne(double e) {

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
