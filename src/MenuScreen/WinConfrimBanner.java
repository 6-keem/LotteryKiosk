package src.MenuScreen;

import src.Main.LotteryKiosk;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinConfrimBanner extends MenuBanner {
    private Color blackColor = new Color(0,0,0);
    public WinConfrimBanner(LotteryKiosk lotteryKiosk){
        super(lotteryKiosk,"당첨확인",Color.red);
        setOpaque(true);
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout());
        selectInfoPanel = new LotteryNumberCheckSelectInfoPanel();
        add(selectInfoPanel, BorderLayout.CENTER);
        selectButtonPanel = new LotteryNumberCheckButtonPanel();
        add(selectButtonPanel, BorderLayout.EAST);
        setFocusable(true);
    }

    private class LotteryNumberCheckSelectInfoPanel extends JPanel{
        public LotteryNumberCheckSelectInfoPanel(){
            setLayout(new GridLayout(5,1));
            setOpaque(true);
            buildGUI();
            setFocusable(true);
        }

        private void buildGUI() {
            JLabel none = new JLabel(" ");
            ImageIcon imageIcon1 = new ImageIcon("resource/LotteryImage/파워볼.png");
            JLabel label1 = new JLabel(imageIcon1);
            ImageIcon imageIcon2 = new ImageIcon("resource/LotteryImage/메가밀리언.png");
            JLabel label2 = new JLabel(imageIcon2);
            JLabel money = new JLabel("당첨 확인");
            money.setHorizontalAlignment(JLabel.CENTER);
            money.setFont(new Font("맑은 고딕 Bold",Font.BOLD,25));
            money.setForeground(Color.white);

            add(none);
            add(label1);
            add(label2);
            add(money);
        }

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(blackColor);
            g.fillRoundRect(20,0,getWidth()-20,200,Constants.ARC_WIDTH,Constants.ARC_HEIGHT);
            g.setColor(blackColor);
            g.drawRoundRect(20,0,getWidth()-20,200,Constants.ARC_WIDTH,Constants.ARC_HEIGHT);
        }
        public JPanel getLotteryNumberCheckSelectInfoPanel(){
            return this;
        }
    }
    private class LotteryNumberCheckButtonPanel extends JPanel{
        private Color color = Color.white;
        private final JPanel panel = this;
        public LotteryNumberCheckButtonPanel(){
            setLayout(new GridLayout(3,1));
            buildGUI();
            setFocusable(true);
        }

        private void buildGUI() {
            add(createImageButtonLabel());
            add(createLotteyNameLabel());
            add(createSelectLabel());
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    changePanelValue(Constants.NUMBERCHECK);
                    color = Color.white;
                    panel.repaint();
                }
                @Override
                public void mousePressed(MouseEvent e){
                    color = new Color(228,228,228);
                    panel.repaint();
                }
                @Override
                public  void mouseReleased(MouseEvent e){
                    color = Color.white;
                    panel.repaint();
                }
            });
        }

        private JLabel createLotteyNameLabel(){
            return new JLabel(" ");
        }
        private JLabel createSelectLabel(){
            JLabel label = new JLabel("  확인하기");
            label.setFont(new Font("맑은 고딕 Bold", Font.BOLD,25));
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(JLabel.LEFT);
            return label;
        }
        private JLabel createImageButtonLabel(){
            ImageIcon imageIcon = new ImageIcon("resource/LotteryImage/당첨확인버튼.png");
            return new JLabel(imageIcon);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(color);
            g.fillRoundRect(0,0, getWidth() - 20,200,Constants.ARC_WIDTH,Constants.ARC_HEIGHT);
            g.setColor(blackColor);
            g.drawRoundRect(0,0, getWidth() - 20,200,Constants.ARC_WIDTH,Constants.ARC_HEIGHT);
        }
        public JPanel getLotteryNumberCheckSelectButtonPanel(){
            return this;
        }
    }
    public JPanel getLotteryNumberCheckPanel() {
        return this;
    }
}
