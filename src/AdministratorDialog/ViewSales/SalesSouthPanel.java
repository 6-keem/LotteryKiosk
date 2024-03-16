package src.AdministratorDialog.ViewSales;

import javax.swing.*;
import java.awt.*;

public class SalesSouthPanel extends JPanel {
    private final boolean powerBall, megaMillion;
    private final JLabel jLabel = new JLabel();
    public SalesSouthPanel(boolean powerBall, boolean megaMillion){
        this.powerBall = powerBall;
        this.megaMillion = megaMillion;
        buildGUI();
    }
    private void buildGUI(){
        setLayout(new BorderLayout());
        add(createNorthLabel(), BorderLayout.NORTH);
        add(createSouthLabel(0), BorderLayout.SOUTH);
    }
    protected void updateData(int totalSalesCount){
        createSouthLabel(totalSalesCount);
    }
    private JLabel createNorthLabel(){
        return new JLabel("종류                                          총계                                                        매출");
    }
    private JLabel createSouthLabel(int totalSalesCount){
        if(powerBall && megaMillion)
            jLabel.setText("모두            ");
        else if(powerBall)
            jLabel.setText("파워볼        ");
        else
            jLabel.setText("메가밀리언");
        jLabel.setText(jLabel.getText() + "                               " + totalSalesCount + "개                                             " + (totalSalesCount * 6000) + "원");
        return jLabel;
    }
}
