package src.ConfrimationWinningScreen;

import src.Main.LotteryKiosk;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ConfirmationWinning extends JPanel {
    private LotteryKiosk lotteryKiosk = null;
    private CenterPanel centerPanel = null;
    public ConfirmationWinning(LotteryKiosk lotteryKiosk){
        this.lotteryKiosk = lotteryKiosk;
        setLayout(new BorderLayout());
        buildGUI();
    }

    private void buildGUI(){
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createSouthPanel(), BorderLayout.SOUTH);
    }
    public void update(){
        centerPanel.updateNumber();
    }
    private JPanel createNorthPanel(){
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.black);
        panel.setLayout(new GridLayout(0,1));

        JLabel logo = new JLabel(new ImageIcon("./resource/LotteryImage/당첨라벨.png"));
        JLabel label = new JLabel("※ 숫자를 입력하고 확인 버튼을 누르세요 ※");

        label.setForeground(Color.red);
        label.setHorizontalAlignment(JLabel.CENTER);
        logo.setHorizontalAlignment(JLabel.CENTER);

        panel.add(logo);
        panel.add(label);
        return panel;
    }
    private CenterPanel createCenterPanel(){
        centerPanel =  new CenterPanel();
        return centerPanel;
    }

    private JPanel createSouthPanel(){
        SouthPanel southPanel = new SouthPanel();
        southPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                southPanel.setCurrentButtonColor(new Color(228,228,228));
                southPanel.repaint();
                lotteryKiosk.changePanelValue(Constants.MAIN);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                southPanel.setCurrentButtonColor(new Color(228,228,228));
                southPanel.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                southPanel.setCurrentButtonColor(Color.white);
                southPanel.repaint();
            }
        });
        return southPanel;
    }

}
