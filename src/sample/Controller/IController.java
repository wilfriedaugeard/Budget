package sample.Controller;

import javafx.scene.Scene;
import sample.Model.ICategory;
import sample.Model.IPeriode;

import java.io.IOException;
import java.util.ArrayList;

public interface IController {
    public void initializeView() throws IOException;
    public Scene getScene();

    public void nextMonth();
    public void previousMonth();

    public ArrayList<IPeriode> getGlobalPeriode();
    public ICategory getRevenuesCategory();
    public ICategory getDepensesCategory();
    public int getMonthCursor();
    public int getYearCursor();
    public int getCurrentMonthCursor();
    public int getCurrentYearCursor();

    public double computeEpargneTotal();

}
