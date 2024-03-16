package src.MenuScreen;

import src.Main.LotteryKiosk;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

abstract class MenuBanner extends JPanel {
    private LotteryKiosk lotteryKiosk;
    protected JPanel selectInfoPanel;
    protected JPanel selectButtonPanel;
    protected JPanel getSelectInfoPanel(){return selectInfoPanel;}
    protected JPanel getSelectButtonPanel(){return selectButtonPanel;}
    protected Color defaultColor;
    protected Color pressedColor = new Color(228,228,228);
    protected Color releasedColor = Color.white;
    protected Color currentColor = releasedColor;
    private String lotteryName = null;
    private Calendar refreshTime;
    public MenuBanner(LotteryKiosk lotteryKiosk, String lotteryName, Color defaultColor){
        this.lotteryName = lotteryName;
        this.lotteryKiosk = lotteryKiosk;
        this.defaultColor = defaultColor;
        refreshTime = Calendar.getInstance();
        refreshTime.set(refreshTime.get(Calendar.YEAR), refreshTime.get(Calendar.MONTH), refreshTime.get(Calendar.DAY_OF_MONTH) + 1,
                0,0,0);
//        refreshTime.set(refreshTime.get(Calendar.YEAR), refreshTime.get(Calendar.MONTH), refreshTime.get(Calendar.DAY_OF_MONTH),
//                refreshTime.get(Calendar.HOUR_OF_DAY),refreshTime.get(Calendar.MINUTE) + 1,0);
    }
    protected void changePanelValue(int panelValue){
        lotteryKiosk.changePanelValue(panelValue);
    }
    protected LotteryKiosk getLotteryKiosk(LotteryKiosk lotteryKiosk){
        return lotteryKiosk;
    }
    protected void setSelectInfoPanel(JPanel selectInfoPanel){
        this.selectInfoPanel = selectInfoPanel;
    }
    protected void setSelectButtonPanel(JPanel selectButtonPanel){
        this.selectButtonPanel = selectButtonPanel;
    }
    protected class SelectInfoPanel extends JPanel{
        public SelectInfoPanel(){
            setLayout(new GridLayout(6,1));
            setOpaque(true);
            buildGUI();
            setFocusable(true);
        }

        private void buildGUI() {
            JLabel label = new JLabel(new ImageIcon("resource/LotteryImage/" + lotteryName + ".png"));

            JLabel money = new JLabel("$295 Million");
            money.setHorizontalAlignment(JLabel.CENTER);
            money.setFont(new Font("맑은 고딕 Bold",Font.BOLD,30));
            money.setForeground(Color.white);

            JLabel refreshDate = new JLabel();
            refreshDate.setHorizontalAlignment(JLabel.CENTER);
            refreshDate.setFont(new Font("맑은 고딕",Font.BOLD,12));
            refreshDate.setForeground(Color.lightGray);

            JLabel remainDate = new JLabel();
            remainDate.setHorizontalAlignment(JLabel.CENTER);
            remainDate.setFont(new Font("맑은 고딕 Bold",Font.BOLD,20));
            remainDate.setForeground(new Color(230,230,230));

            add(new JLabel());
            add(label);
            add(money);
            add(refreshDate);
            add(remainDate);
            add(new JLabel());
            Timer timer = new Timer(refreshDate,remainDate,refreshTime,lotteryName);
            timer.start();
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(defaultColor);
            g.fillRoundRect(20,0,getWidth()-20,200,Constants.ARC_WIDTH, Constants.ARC_HEIGHT);
            g.setColor(defaultColor);
            g.drawRoundRect(20,0,getWidth()-20,200,Constants.ARC_WIDTH, Constants.ARC_HEIGHT);
        }
        public JPanel getSelectInfoPanel(){
            return this;
        }
    }
    protected class SelectButtonPanel extends JPanel{
        public SelectButtonPanel(){
            setLayout(new GridLayout(3,1));
            buildGUI();
            setFocusable(true);
        }

        private void buildGUI() {
            add(createImageButtonLabel());
            add(createLotteryNameLabel());
            add(createSelectLabel());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentColor = Color.white;
                    repaint();
                    switch (lotteryName){
                        case "파워볼" -> {
                            changePanelValue(Constants.POWERBALL);
                            break;
                        }
                        case "메가밀리언" -> {
                            changePanelValue(Constants.MEGAMILLIONS);
                            break;
                        }
                        case "당첨확인" -> {
                            changePanelValue(Constants.NUMBERCHECK);
                            break;
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent e){
                    currentColor = new Color(228,228,228);
                    repaint();
                }
                @Override
                public  void mouseReleased(MouseEvent e){
                    currentColor = Color.white;
                    repaint();
                }
            });
        }

        private JLabel createLotteryNameLabel(){
            JLabel label = null;
            if(lotteryName.equals("파워볼"))
                label = new JLabel("     " + lotteryName);
            else
                label = new JLabel("  " + lotteryName);
            label.setFont(new Font("맑은 고딕",Font.BOLD,20));
            label.setForeground(defaultColor);
            label.setHorizontalAlignment(JLabel.LEFT);
            return label;
        }
        private JLabel createSelectLabel(){
            JLabel label = new JLabel("  신청하기");
            label.setFont(new Font("맑은 고딕 Bold", Font.BOLD,25));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.LEFT);
            return label;
        }
        private JLabel createImageButtonLabel(){
            ImageIcon imageIcon = new ImageIcon("resource/LotteryImage/" + lotteryName + "버튼.png");
            return new JLabel(imageIcon);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(currentColor);
            g.fillRoundRect(0,0,getWidth()-20,200,Constants.ARC_WIDTH, Constants.ARC_HEIGHT);
            g.setColor(defaultColor);
            g.drawRoundRect(0,0,getWidth()-20,200,Constants.ARC_WIDTH, Constants.ARC_HEIGHT);
        }
        public JPanel getSelectButtonPanel(){
            return this;
        }
    }
}