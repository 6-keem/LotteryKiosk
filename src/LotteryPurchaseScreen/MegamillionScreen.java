package src.LotteryPurchaseScreen;
import src.Main.LotteryKiosk;
import javax.swing.*;
import java.awt.*;

public class MegamillionScreen extends LotteryScreen {
    public MegamillionScreen(LotteryKiosk lotteryKiosk){
        super(lotteryKiosk,"메가밀리언",new Color(0,0,100),new Color(0,0,128),1,70,1,25);
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout());
        add(createNorthPanel(), BorderLayout.NORTH);
        add(createCenterPanel(),BorderLayout.CENTER);
        add(createSouthPanel(),BorderLayout.SOUTH);

        getMoney().setFont(new Font("맑은 고딕",Font.BOLD,30));
        getMoney().setForeground(getDefaultPurchaseButtonColor());
        getMoney().setHorizontalAlignment(JLabel.CENTER);
    }

    public void init(){
        removeAll();
        add(new MegamillionScreen(getLotteryKiosk()), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
