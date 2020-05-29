package sample.Factory;

import sample.Model.Year;
import sample.Model.IPeriode;
import sample.Model.Month;

public class PeriodeFactory {
    public static IPeriode createMonth(String name){
        return new Month(name);
    }
    public static IPeriode createYear(String name){
        return new Year(name);
    }
}
