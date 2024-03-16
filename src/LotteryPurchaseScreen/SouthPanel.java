package src.LotteryPurchaseScreen;

import src.Main.LotteryKiosk;
import src.LotteryPurchaseScreen.Payment.PaymentDialog;
import src.LotteryPurchaseScreen.Payment.Receipt;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class SouthPanel extends JPanel {
    private Color homeButtonPressColor = new Color(228,228,228);
    private Color homeButtonDefaultColor = Color.white;
    private Color homeButtonColor = homeButtonDefaultColor;
    private Color purchaseButtonDefaultColor = null;
    private Color purchaseButtonPressedColor = null;
    private Color purchaseButtonColor = null;
    private LotteryKiosk lotteryKiosk;
    private JLabel money = null;
    private HomePanel homePanel = new HomePanel();
    private PurchasePanel purchasePanel = new PurchasePanel();
    private Vector<NumberPanel> numberPanelVector = null;
    private String lotteryName = null;
    public SouthPanel(LotteryKiosk lotteryKiosk, JLabel money, Vector<NumberPanel> numberPanelVector, Color purchaseButtonDefaultColor,
                      Color purchaseButtonPressedColor, String lotteryName){
        this.lotteryName = lotteryName;
        this.lotteryKiosk = lotteryKiosk;
        this.money = money;
        this.numberPanelVector = numberPanelVector;
        this.purchaseButtonDefaultColor = purchaseButtonDefaultColor;
        this.purchaseButtonPressedColor = purchaseButtonPressedColor;
        this.purchaseButtonColor = purchaseButtonDefaultColor;
        buildGUI(money);
    }

    private void buildGUI(JLabel money) {
        setBorder(BorderFactory.createLineBorder(new Color(10,10,30),5));
        setLayout(new GridLayout(0,3));
        add(money);
        add(homePanel);
        add(purchasePanel);
    }

    private void createPopUp(){
        Vector<Boolean> validFlags = Constants.isValidLottery(numberPanelVector,lotteryName);
        Vector<Integer> errorIndex = new Vector<>();
        boolean totalFlag = true;
        int count = 0;
        for(int i = 0 ; i < validFlags.size() ; i++) {
            if(!validFlags.get(i)){
                errorIndex.add(i+1);
                totalFlag = false;
                count++;
            }
        }
        
        if(!totalFlag){
            String errorMessage = "";
            for(int i = 0 ; i < count - 1; i ++)
                errorMessage += Integer.toString(errorIndex.get(i)) + ", ";
            errorMessage += Integer.toString(errorIndex.get(errorIndex.size()-1)) +"번 째 로또에 잘못된 번호가 입력되었습니다!";
            JOptionPane.showMessageDialog(null,errorMessage, "오류",JOptionPane.ERROR_MESSAGE);
        } else {
            if(numberPanelVector.isEmpty())
                return;
            PaymentDialog paymentDialog = new PaymentDialog(lotteryKiosk.getMainFrame());
            paymentDialog.setPaymentStatus(Constants.PAYMENTINPROGRESS);
            paymentDialog.setVisible(true);
            if(paymentDialog.getPaymentStatus() == Constants.PAID){
                Vector<Vector<Integer>> numbersVector = new Vector<>();
                int index = 0;
                try {
                    for(NumberPanel numberPanel : numberPanelVector){
                        Vector<Integer> vt = new Vector<>();
                        for(JTextField textField : numberPanel.getjTextFields()){
                            vt.add(Integer.parseInt(textField.getText()));
                        }
                        numbersVector.add(vt);
                    }
                    Receipt receipt = new Receipt(numbersVector,lotteryName);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private class HomePanel extends JPanel{
        public HomePanel(){
            JLabel label = new JLabel(new ImageIcon("resource/LotteryImage/홈버튼.png"));
            label.setHorizontalAlignment(JLabel.CENTER);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    homeButtonColor = homeButtonDefaultColor;
                    repaint();
                    lotteryKiosk.changePanelValue(Constants.MAIN);
                }
                @Override
                public void mousePressed(MouseEvent e){
                    homeButtonColor = homeButtonPressColor;
                    repaint();
                }
                @Override
                public  void mouseReleased(MouseEvent e){
                    homeButtonColor = homeButtonDefaultColor;
                    repaint();
                }
            });
            add(label);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(homeButtonColor);
            g.fillRoundRect(0,0,getWidth(),getHeight(), 20, 20);
            g.setColor(Color.black);
            g.drawRoundRect(0,0,getWidth(),getHeight(), 20, 20);
        }
    }
    private class PurchasePanel extends JPanel{
        public PurchasePanel(){
            JLabel label = new JLabel("결제하기");
            label.setForeground(Color.white);
            label.setFont(new Font("맑은 고딕", Font.BOLD,30));
            label.setHorizontalAlignment(JLabel.CENTER);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    purchaseButtonColor = purchaseButtonDefaultColor;
                    repaint();
                    createPopUp();
                }
                @Override
                public void mousePressed(MouseEvent e){
                    purchaseButtonColor = purchaseButtonPressedColor;
                    repaint();
                }
                @Override
                public  void mouseReleased(MouseEvent e){
                    purchaseButtonColor = purchaseButtonDefaultColor;
                    repaint();
                }
            });
            add(label);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(purchaseButtonColor);
            g.fillRoundRect(0,0,getWidth(),getHeight(), 20, 20);
        }
    }
}