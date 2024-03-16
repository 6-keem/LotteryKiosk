package src.LotteryPurchaseScreen;

import src.Main.LotteryKiosk;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public abstract class LotteryScreen extends JPanel {
    private LotteryKiosk lotteryKiosk;
    private Vector<NumberPanel> numberPanelVector = new Vector<>();
    private JLabel money = new JLabel("6000원");
    private String lotteryName;
    private Color pressedPurchaseButtonColor = null;
    private Color defaultPurchaseButtonColor = null;
    private Color currentPurchaseButtonColor = null;
    private HashMap<String, Integer> validNumbers = new HashMap<>();
    protected LotteryScreen(LotteryKiosk lotteryKiosk, String lotteryName, Color pressedPurchaseButtonColor, Color defaultPurchaseButtonColor
    , int normalBallStartNumber, int normalBallEndNumber, int bonusBallStartNumber, int bonusBallEndNumber){
        this.lotteryKiosk = lotteryKiosk;
        this.lotteryName = lotteryName;
        this.pressedPurchaseButtonColor = pressedPurchaseButtonColor;
        this.defaultPurchaseButtonColor = defaultPurchaseButtonColor;
        this.currentPurchaseButtonColor = defaultPurchaseButtonColor;
        validNumbers.put(Constants.NORMAL_START, normalBallStartNumber);
        validNumbers.put(Constants.NORMAL_END, normalBallEndNumber);
        validNumbers.put(Constants.BONUS_START, bonusBallStartNumber);
        validNumbers.put(Constants.BONUS_END, bonusBallEndNumber);
    }
    abstract public void init();
    protected Color getDefaultPurchaseButtonColor(){return defaultPurchaseButtonColor;}
    protected LotteryKiosk getLotteryKiosk(){return lotteryKiosk;}
    protected JLabel getMoney() { return money; }
    protected JPanel createNorthPanel(){ return new NorthPanel(lotteryName); }
    protected JPanel createCenterPanel(){ return new CenterPanel(money, numberPanelVector,lotteryName);}
    protected JPanel createSouthPanel(){return new SouthPanel(lotteryKiosk,money,numberPanelVector, defaultPurchaseButtonColor, pressedPurchaseButtonColor,lotteryName);}
}
