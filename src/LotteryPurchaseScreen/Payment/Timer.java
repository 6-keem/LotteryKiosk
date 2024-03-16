package src.LotteryPurchaseScreen.Payment;

import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;

class TimerPanel extends JPanel{
    private int currentTimerCount = 0;
    private boolean isExpired = false;
    private boolean isPaid = false;
    private final Color outterColor = Color.black;
    private final Color innerColor = new Color(0,94,184);
    public TimerPanel(){
        setOpaque(true);
        setBackground(Color.white);
    }
    public boolean getIsExpired(){
        return isExpired;
    }
    public void setIsPaid(boolean isPaid) {this.isPaid = isPaid;}
    public void consume(){
        if(currentTimerCount == Constants.PAYMENT_EXPIRE_TIME){
            isExpired = true;
            return;
        }
        currentTimerCount++;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!isPaid){
            g.setColor(innerColor);
            g.fillRoundRect(0,0,getWidth()-((getWidth()/Constants.PAYMENT_EXPIRE_TIME) * currentTimerCount),getHeight(),Constants.ARC_WIDTH - 10,Constants.ARC_HEIGHT-10);
            g.setColor(outterColor);
            g.drawRoundRect(0,0,getWidth(),getHeight(),Constants.ARC_WIDTH - 10,Constants.ARC_HEIGHT-10);
        }
    }
}

public class Timer extends Thread{
    private TimerPanel timer;
    private boolean isExpired = false;
    private PaymentDialog paymentDialog;
    public Timer(PaymentDialog paymentDialog, TimerPanel timer){
        this.paymentDialog = paymentDialog;
        this.timer = timer;
    }
    public boolean getIsExpired(){return isExpired;}
    @Override
    public void run(){
        while(true){
            try{
                sleep(1000);
                timer.consume();
                if(timer.getIsExpired() && paymentDialog.getPaymentStatus() == Constants.PAYMENTINPROGRESS){
                    JOptionPane.showMessageDialog(null,"시간 초과. 다시 시도하세요", "오류",JOptionPane.ERROR_MESSAGE);
                    paymentDialog.setPaymentStatus(Constants.PAYMENTFAILED);
                    paymentDialog.dispose();
                    return;
                }
            } catch (InterruptedException e){
                JOptionPane.showMessageDialog(null,"결제 중 오류가 발생했습니다.", "오류",JOptionPane.ERROR_MESSAGE);
                isExpired = true;
                return;
            }
        }
    }
}