package src.Main;

import src.ConfrimationWinningScreen.ConfirmationWinning;
import src.MenuScreen.MenuScreen;
import src.LotteryPurchaseScreen.LotteryScreen;
import src.LotteryPurchaseScreen.MegamillionScreen;
import src.LotteryPurchaseScreen.PowerballScreen;
import src.Utilities.Constants;

import java.awt.*;
import java.util.Vector;

public class LotteryKiosk{
    private int currentPanel = Constants.MAIN;
    private MainFrame mainFrame;
    private Container contentPane;
    private MenuScreen menuScreen;
    private CardLayout cardLayout = new CardLayout();
    private Vector<LotteryScreen> numberSelectPanels = new Vector<>();
    private ConfirmationWinning confirmationWinning = new ConfirmationWinning(this);
    public LotteryKiosk(){
        mainFrame = new MainFrame();
        contentPane = mainFrame.getContentPane();
        contentPane.setLayout(cardLayout);
        menuScreen = new MenuScreen(this);
        buildGUI();
    }

    private void buildGUI() {
        contentPane.add(menuScreen,"menuScreen");
        contentPane.add(confirmationWinning,"numberCheckScreen");
        numberSelectPanels.add(new PowerballScreen(this));
        numberSelectPanels.add(new MegamillionScreen(this));

        String []str = {"powerBallScreen", "megaMillionScreen","numberCheckScreen"};
        for(int i = 0 ; i < numberSelectPanels.size() ; i++)
            contentPane.add(numberSelectPanels.get(i),str[i]);
        contentPane.setBackground(Color.white);
        mainFrame.setVisible(true);
    }

    public MainFrame getMainFrame(){return mainFrame;}
    public void changePanelValue(int currentPanel){
        this.currentPanel = currentPanel;
        this.panelChange();
    }
    private LotteryScreen getNumberSelectPanel(int panelValue){
        for(LotteryScreen panel : numberSelectPanels){
            if(panelValue == Constants.POWERBALL && panel instanceof PowerballScreen)
                return panel;
            else if(panelValue == Constants.MEGAMILLIONS && panel instanceof MegamillionScreen)
                return panel;
        }
        return null;
    }
    private void panelChange(){
        switch (currentPanel){
            case Constants.MAIN -> {
                cardLayout.show(contentPane,"menuScreen");
            }
            case Constants.POWERBALL -> {
                try{
                    getNumberSelectPanel(Constants.POWERBALL).init();
                    cardLayout.show(contentPane,"powerBallScreen");
                } catch (NullPointerException exception){
                    exception.printStackTrace();
                }
            }
            case Constants.MEGAMILLIONS -> {
                try{
                    getNumberSelectPanel(Constants.MEGAMILLIONS).init();
                    cardLayout.show(contentPane,"megaMillionScreen");
                } catch (NullPointerException exception){
                    exception.printStackTrace();
                }
            }
            case Constants.NUMBERCHECK -> {
                confirmationWinning.update();
                cardLayout.show(contentPane,"numberCheckScreen");
            }
        }
    }
}
