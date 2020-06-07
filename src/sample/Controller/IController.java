package sample.Controller;

import javafx.scene.Scene;
import sample.Model.ICategory;
import sample.Model.IPeriode;
import sample.Model.Montant;
import sample.View.View;

import java.io.IOException;
import java.util.ArrayList;

public interface IController {
    public void initializeView() throws IOException;
    public Scene getScene();
    public View getView();

    public void nextMonth();
    public void previousMonth();

    public ArrayList<IPeriode> getGlobalPeriode();
    public ICategory getRevenuesCategory();
    public ICategory getDepensesCategory();
    public int getMonthCursor();
    public int getYearCursor();
    public int getCurrentMonthCursor();
    public int getCurrentYearCursor();
    public IPeriode getCurrentMonth();

    public double computeEpargneTotal();
    public void addRecurrentCharge(Montant charge);

}
