package src.LotteryPurchaseScreen;

import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;

public class NorthPanel extends JPanel {
    public NorthPanel(String lotteryName){
        buildGUI(lotteryName);
    }

    private void buildGUI(String lotteryName) {
        setLayout(new GridLayout(3,0));
        JLabel logo = new JLabel(new ImageIcon("resource/LotteryImage/" + lotteryName + ".png"));
        logo.setHorizontalAlignment(JLabel.CENTER);

        JLabel label = new JLabel("※ 1회당 6게임까지 구매 가능, 다회 구매 가능");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("맑은 고딕",Font.BOLD,15));
        label.setForeground(Color.red);

        JLabel numberLabel = new JLabel();
        if(lotteryName.equals("파워볼"))
            numberLabel.setText("WHITE : 1 ~ 69 / RED : 1 ~ 26");
        else
            numberLabel.setText("WHITE : 1 ~ 70 / YELLOW : 1 ~ 25");
        numberLabel.setFont(new Font("맑은 고딕",Font.BOLD, 20));
        numberLabel.setForeground(Color.gray);
        numberLabel.setHorizontalAlignment(JLabel.CENTER);

        setOpaque(true);
        setBackground(new Color(10,10,30));
        add(logo);
        add(label);
        add(numberLabel);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.red);
        g.fillRoundRect(0,0,getWidth()-20,getHeight(), Constants.ARC_WIDTH,Constants.ARC_HEIGHT);
    }
}