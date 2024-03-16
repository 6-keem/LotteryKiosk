package src.ConfrimationWinningScreen;

import javax.swing.*;
import java.awt.*;

public class SouthPanel extends JPanel {
    private Color currentButtonColor = Color.white;
    public SouthPanel(){
        buildGUI();
    }
    private void buildGUI(){
        JLabel label = new JLabel(new ImageIcon("./resource/LotteryImage/흰색홈버튼.png"));
        add(label);
    }
    protected void setCurrentButtonColor(Color currentButtonColor){this.currentButtonColor = currentButtonColor;}
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
        g.setColor(currentButtonColor);
        g.drawRoundRect(0,0,getWidth(),getHeight(),20,20);
    }
}
