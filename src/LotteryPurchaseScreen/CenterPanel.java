package src.LotteryPurchaseScreen;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class CenterPanel extends JPanel {
    private Vector<JButton> numberPanelDeleteButtonVector = new Vector<>();
    private Vector<JButton> numberPanelAutoButtonVector = new Vector<>();
    private Vector<NumberPanel> numberPanelVector = null;
    private CenterCenterPanel centerCenterPanel = null;
    private CenterSouthPanel centerSouthPanel = null;
    private JLabel moneyJLabelForSouthPanel;
    private String lotteryName = null;
    public CenterPanel(JLabel moneyJLabelForSouthPanel, Vector<NumberPanel> numberPanelVector, String lotteryName) {
        this.lotteryName = lotteryName;
        this.numberPanelVector = numberPanelVector;
        this.moneyJLabelForSouthPanel = moneyJLabelForSouthPanel;
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout());
        centerCenterPanel = new CenterCenterPanel();
        centerSouthPanel = new CenterSouthPanel();
        add(centerCenterPanel, BorderLayout.CENTER);
        add(centerSouthPanel, BorderLayout.SOUTH);
    }

    public void addNewNumberPanel(){
        if(numberPanelVector.size() == Constants.MAXLOTTOAMOUNT)
            return;
        NumberPanel numberPanel = new NumberPanel(lotteryName);
        numberPanel.getDeleteButton().addActionListener(new MyDeleteButtonActionListener());
        numberPanel.getAutoButton().addActionListener(new MyAutoButtonActionListener());
        numberPanelVector.add(numberPanel);
        centerCenterPanel.removeAll();
        for(NumberPanel numPanel: numberPanelVector)
            centerCenterPanel.add(numPanel);
        centerCenterPanel.revalidate();
        centerCenterPanel.repaint();
    }
    private void deleteNumberPanel(JButton button){
        centerCenterPanel.removeAll();
        NumberPanel removePanel = null;
        for(NumberPanel numberPanel : numberPanelVector){
            if(numberPanel.getDeleteButton().equals(button)){
                removePanel = numberPanel;
                break;
            }
        }
        //패널 지우고 다시 그리기 검색 https://stackoverflow.com/questions/2501861/how-can-i-remove-a-jpanel-from-a-jframe
        numberPanelVector.remove(removePanel);
        for(NumberPanel numberPanel : numberPanelVector)
            centerCenterPanel.add(numberPanel);
        centerCenterPanel.revalidate();
        centerCenterPanel.repaint();
    }
    private void updateCalculatedPrice(){
        moneyJLabelForSouthPanel.setText(numberPanelVector.size() * 6000 + "원");
    }
    private class CenterCenterPanel extends JPanel{
        public CenterCenterPanel(){
            setLayout(new GridLayout(6, 1));
            add(createNumberPanel());
        }
        private JPanel createNumberPanel(){
            NumberPanel numberPanel = new NumberPanel(lotteryName);
            numberPanel.getDeleteButton().addActionListener(new MyDeleteButtonActionListener());
            numberPanel.getAutoButton().addActionListener(new MyAutoButtonActionListener());
            numberPanelVector.add(numberPanel);
            numberPanelAutoButtonVector.add(numberPanel.getAutoButton());
            numberPanelDeleteButtonVector.add(numberPanel.getDeleteButton());
            return numberPanel;
        }
    }
    private class CenterSouthPanel extends JPanel{
        public CenterSouthPanel(){
            JButton button = new JButton(new ImageIcon("resource/LotteryImage/추가버튼.png"));
            button.setPressedIcon(new ImageIcon("resource/LotteryImage/추가버튼2.png"));
            button.setBorderPainted(false);
            button.setHorizontalAlignment(JLabel.CENTER);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewNumberPanel();
                    updateCalculatedPrice();
                }
            });
        }
    }
    private void setAutoNumber(JButton button){
        NumberPanel searchPanel = null;
        for(NumberPanel nPanel : numberPanelVector){
            if(nPanel.getAutoButton().equals(button)){
                searchPanel = nPanel;
                break;
            }
        }
        try{
            Vector<JTextField> textFields = searchPanel.getjTextFields();
            Vector<Integer> autoNumbers = Constants.createAutoLotteryNumber(lotteryName);
            for(int i = 0 ; i < textFields.size() ; i++)
                textFields.get(i).setText(Integer.toString(autoNumbers.get(i)));
        } catch (NullPointerException exception){
            exception.printStackTrace();
        }
    }
    private class MyAutoButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            JButton button = (JButton) e.getSource();
            setAutoNumber(button);
        }
    }
    private class MyDeleteButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton button = (JButton)e.getSource();
            deleteNumberPanel(button);
            updateCalculatedPrice();
        }
    }
}

