package src.LotteryPurchaseScreen;

import src.Main.LotteryKiosk;

import javax.swing.*;
import java.awt.*;

public class PowerballScreen extends LotteryScreen {
    public PowerballScreen(LotteryKiosk lotteryKiosk){
        super(lotteryKiosk,"파워볼",new Color(180,0,0),Color.red,1,69,1,26);
        setLayout(new BorderLayout());
        buildGUI();
    }

    private void buildGUI() {
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(),BorderLayout.CENTER);
        add(createSouthPanel(),BorderLayout.SOUTH);

        getMoney().setFont(new Font("맑은 고딕",Font.BOLD,30));
        getMoney().setForeground(getDefaultPurchaseButtonColor());
        getMoney().setHorizontalAlignment(JLabel.CENTER);
    }
    public void init(){
        //패널 지우고 다시 그리기 검색 https://stackoverflow.com/questions/2501861/how-can-i-remove-a-jpanel-from-a-jframe
        removeAll();
        add(new PowerballScreen(getLotteryKiosk()), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
