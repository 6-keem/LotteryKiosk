package src.AdministratorDialog.ViewSales;

import src.AdministratorDialog.AdministratorDialog;

import javax.swing.*;
import java.awt.*;

abstract public class ViewSales extends Panel {
    protected AdministratorDialog administratorDialog;
    protected Integer totalSalesCount = 0;
    private SalesCenterPanel centerPanel = null;
    private SalesSouthPanel southPanel = null;
    private StringBuffer saveSalesData = new StringBuffer("");
    protected ViewSales(AdministratorDialog administratorDialog,boolean powerBall, boolean megaMillion){
        this.administratorDialog = administratorDialog;
        this.totalSalesCount = 0;
        buildGUI(powerBall,megaMillion);
    }
    protected void buildGUI(boolean powerBall, boolean megaMillion){
        setLayout(new BorderLayout());
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(powerBall,megaMillion), BorderLayout.CENTER);
        add(createSouthPanel(powerBall,megaMillion), BorderLayout.SOUTH);
    }
    public StringBuffer getSaveSalesData(){return saveSalesData;}
    public void updateData(){
        centerPanel.updateData();
        southPanel.updateData(centerPanel.getTotalSalesCount());
        centerPanel.setTotalSalesCount();
    }
    private JLabel createNorthPanel(){
        JLabel label = new JLabel("거래번호                                               날짜                                                수량");
        label.setHorizontalAlignment(JLabel.LEFT);
        return label;
    }
    private SalesCenterPanel createCenterPanel(boolean powerBall, boolean megaMillion){
        centerPanel = new SalesCenterPanel(administratorDialog, saveSalesData, powerBall,megaMillion);
        return centerPanel;
    }
    private JPanel createSouthPanel(boolean powerBall, boolean megaMillion){
        southPanel = new SalesSouthPanel(powerBall,megaMillion);
        return southPanel;
    }
}
