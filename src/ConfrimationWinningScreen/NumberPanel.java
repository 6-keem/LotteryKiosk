package src.ConfrimationWinningScreen;

import src.LotteryPurchaseScreen.MyJTextFieldListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class NumberPanel extends JPanel {
    private Vector<JTextField> winningNumberTextFieldVector = new Vector<>();
    private Vector<JTextField> userNumberTextfieldVector = new Vector<>();
    private Vector<Integer> winningNumber = new Vector<>();
    private String lotteryName = null;
    public NumberPanel(Vector<Integer> winningNumber, String lotteryName){
        this.winningNumber = winningNumber;
        this.lotteryName = lotteryName;
        buildGUI();
    }
    private void buildGUI() {
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new BorderLayout());
        JPanel winningNumberPanel = new JPanel();
        JPanel userNumberPanel = new JPanel();

        for (int i = 0; i < 5; i++)
            addBalls(winningNumberPanel,userNumberPanel, "./resource/LotteryImage/하얀공.png", Color.black, i);

        if (lotteryName.equals("파워볼"))
            addBalls(winningNumberPanel,userNumberPanel,"./resource/LotteryImage/파워볼공.png", Color.white, 5);
        else
            addBalls(winningNumberPanel,userNumberPanel,"./resource/LotteryImage/메가밀리언공.png", Color.black, 5);

        JButton button = new JButton(new ImageIcon("./resource/LotteryImage/확인버튼.PNG"));
        button.setPressedIcon(new ImageIcon("./resource/LotteryImage/확인버튼눌림.PNG"));

        // 버튼 크기 조절하는 법, 배경 없애는 법 검색 했음
        button.setPreferredSize(new Dimension(70,60));
        button.setContentAreaFilled(false);

        button.setBorderPainted(false);
        button.addActionListener(new MyActionListener());

        JPanel tempPanel = new JPanel(new GridLayout(2,0));
        tempPanel.add(winningNumberPanel);
        tempPanel.add(userNumberPanel);

        numberPanel.add(new JLabel(new ImageIcon("./resource/LotteryImage/" + lotteryName + ".png")), BorderLayout.NORTH);
        numberPanel.add(tempPanel, BorderLayout.CENTER);
        numberPanel.add(button, BorderLayout.SOUTH);
        add(numberPanel);
    }
    public void updateNumber(Vector<Integer> newWinningNumber){
        winningNumber = new Vector<>();
        for(int i = 0 ; i < winningNumberTextFieldVector.size() ; i ++){
            winningNumberTextFieldVector.get(i).setText(Integer.toString(newWinningNumber.get(i)));
            winningNumber.add(newWinningNumber.get(i));
        }

        for(JTextField jTextField : userNumberTextfieldVector)
            jTextField.setText("");
    }
    private void addBalls(JPanel winningNumberPanel, JPanel userNumberPanel, String fileName, Color fontColor, int index){
        JTextField winningNumberTextField = new JTextField(2);
        winningNumberTextField.setFont(new Font("맑은 고딕 Bold",Font.BOLD,20));
        winningNumberTextField.setForeground(fontColor);
        winningNumberTextField.setHorizontalAlignment(JTextField.CENTER);
        winningNumberTextField.setOpaque(false);
        winningNumberTextField.setEditable(false);
        winningNumberTextField.setBorder(BorderFactory.createEmptyBorder());
        winningNumberTextField.setText(Integer.toString(winningNumber.get(index)));
        winningNumberTextFieldVector.add(winningNumberTextField);

        JTextField userNumberTextField = new JTextField(2);
        userNumberTextField.setFont(new Font("맑은 고딕 Bold",Font.BOLD,20));
        userNumberTextField.setForeground(fontColor);
        userNumberTextField.setHorizontalAlignment(JTextField.CENTER);
        userNumberTextField.addKeyListener(new MyJTextFieldListener());
        userNumberTextField.setOpaque(false);
        userNumberTextField.setBorder(BorderFactory.createEmptyBorder());
        userNumberTextfieldVector.add(userNumberTextField);

        JLabel winningLabel = new JLabel(new ImageIcon(fileName));
        JLabel userLabel = new JLabel(new ImageIcon(fileName));

        winningLabel.setLayout(new BorderLayout());
        userLabel.setLayout(new BorderLayout());

        winningLabel.add(winningNumberTextField);
        userLabel.add(userNumberTextField);

        winningNumberPanel.add(winningLabel);
        userNumberPanel.add(userLabel);
    }
    private class MyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            new WinningCheck(winningNumber,userNumberTextfieldVector,lotteryName);
        }
    }
}
