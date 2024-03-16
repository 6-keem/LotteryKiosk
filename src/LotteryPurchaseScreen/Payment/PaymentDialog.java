package src.LotteryPurchaseScreen.Payment;
import src.Utilities.Constants;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PaymentDialog extends JDialog {
    private JSlider jSlider = null;
    private JLabel animationImageLabel = null;
    private TimerPanel timer = null;
    private Timer timerBar = null;
    private JButton button = new JButton("결제취소");
    private int paymentStatus = Constants.PAYMENTINPROGRESS;
    public PaymentDialog(JFrame frame){
        super(frame,"Payment",true);
        timer = new TimerPanel();
        timerBar = new Timer(this,timer);
        setLayout(new BorderLayout());
        buildGUI();

        setLocation(150,200);
        pack();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    public int getPaymentStatus(){return paymentStatus;}
    public void setPaymentStatus(int paymentStatus){this.paymentStatus = paymentStatus;}
    private void buildGUI(){
        add(createAnimationImage(), BorderLayout.CENTER);
        add(createJSlider(), BorderLayout.EAST);
        add(createExitButton(),BorderLayout.SOUTH);
        add(timer, BorderLayout.NORTH);
        timerBar.start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                paymentStatus = Constants.CANCELED;
            }
        });
    }
    private JButton createExitButton(){
        button.setFont(new Font("맑은 고딕",Font.BOLD,15));
        button.setHorizontalAlignment(JButton.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paymentStatus = Constants.CANCELED;
                dispose();
            }
        });
        return button;
    }
    private JLabel createAnimationImage(){
        animationImageLabel = new JLabel();
        animationImageLabel.setIcon(new ImageIcon("./resource/cardinsert/0.gif"));
        animationImageLabel.setHorizontalAlignment(JLabel.CENTER);
        return animationImageLabel;
    }
    private JSlider createJSlider(){
        jSlider = new JSlider(JSlider.VERTICAL, 0, 14, 0);
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                // TODO: 2023-11-19 결제 타이머 시작 특정 시간 이후 오류
                if(slider.getValue() == 0)
                    animationImageLabel.setIcon(new ImageIcon("./resource/cardinsert/0.gif"));
                else if(slider.getValue() != slider.getMaximum())
                    animationImageLabel.setIcon(new ImageIcon("./resource/cardinsert/" + Integer.toString(slider.getValue()) + ".jpg"));
                else {
                    timer.setIsPaid(true);
                    try {
                        animationImageLabel.setIcon(new ImageIcon("./resource/cardinsert/결제완료.gif"));
                        Thread.sleep(500);
                        button.setEnabled(false);
                        jSlider.setEnabled(false);
                        setPaymentStatus(Constants.PAID);
                    }
                    catch (InterruptedException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
        return jSlider;
    }
}
