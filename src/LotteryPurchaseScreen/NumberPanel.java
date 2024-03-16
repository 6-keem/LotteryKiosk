package src.LotteryPurchaseScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class NumberPanel extends JPanel {
    private JButton autoButton;
    private JButton deleteButton;
    private String lotteryName = null;
    protected Vector<JTextField> jTextFields = new Vector<>();
    protected Vector<JLabel> balls = new Vector<>();
    public NumberPanel(String lotteryName) {
        this.lotteryName = lotteryName;
        setBorder(BorderFactory.createLineBorder(new Color(10,10,30),3));
        setLayout(new GridLayout(1, 0));
        buildGUI(lotteryName);
    }

    private void buildGUI(String lotteryName) {
        setAutoButton("resource/LotteryImage/" + lotteryName + "자동.png", "resource/LotteryImage/" + lotteryName + "자동눌림.png");
        for(int i = 0 ; i < 5 ; i ++)
            addBalls("resource/LotteryImage/하얀공.png", Color.black);
        if(lotteryName.equals("메가밀리언"))
            addBalls("resource/LotteryImage/" + lotteryName + "공.png", Color.BLACK);
        else
            addBalls("resource/LotteryImage/" + lotteryName + "공.png", Color.white);
        setDeleteButton();
    }

    private void setAutoButton(String fileName, String pushedFileName){
        autoButton = new JButton(new ImageIcon(fileName));
        autoButton.setOpaque(false);
        // TODO: 2023-11-21 배경 없애는 법 검색
        autoButton.setContentAreaFilled(false);

        autoButton.setPressedIcon(new ImageIcon(pushedFileName));
        autoButton.setBorderPainted(false);
        add(autoButton);
    }
    private void setDeleteButton(){
        deleteButton = new JButton(new ImageIcon("resource/LotteryImage/삭제.png"));
        deleteButton.setOpaque(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setPressedIcon(new ImageIcon("resource/LotteryImage/삭제눌림.png"));
        deleteButton.setBorderPainted(false);
        add(deleteButton);
    }
    public Vector<JTextField> getjTextFields(){return jTextFields;}
    public JButton getDeleteButton(){return deleteButton;}
    public JButton getAutoButton(){return autoButton;}
    private void addBalls(String fileName, Color fontColor){
        JTextField textField = new JTextField(2);
        textField.setFont(new Font("맑은 고딕 Bold",Font.BOLD,20));
        textField.setForeground(fontColor);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addKeyListener(new MyJTextFieldListener());
        textField.setOpaque(false);
        // TODO: 2023-11-21 테두리 없애는 법 검색
        //https://www.tutorialspoint.com/swingexamples/add_border_to_label.htm
        textField.setBorder(BorderFactory.createEmptyBorder());
        jTextFields.add(textField);

        JLabel ball = new JLabel(new ImageIcon(fileName));
        ball.setLayout(new BorderLayout());
        ball.add(textField);
        balls.add(ball);
        add(ball);
    }
}