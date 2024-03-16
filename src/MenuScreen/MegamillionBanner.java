package src.MenuScreen;

import src.Main.LotteryKiosk;

import javax.swing.*;
import java.awt.*;

public class MegamillionBanner extends MenuBanner {
    public MegamillionBanner(LotteryKiosk lotteryKiosk){
        super(lotteryKiosk, "메가밀리언", new Color(0,0,128));
        setOpaque(true);
        buildGUI();
        setFocusable(true);
    }
    private void buildGUI() {
        setLayout(new BorderLayout());
        selectInfoPanel = new SelectInfoPanel();
        add(selectInfoPanel, BorderLayout.CENTER);
        selectButtonPanel = new SelectButtonPanel();
        add(selectButtonPanel, BorderLayout.EAST);
    }

    public JPanel getMegaMillionPanel() {
        return this;
    }
}
