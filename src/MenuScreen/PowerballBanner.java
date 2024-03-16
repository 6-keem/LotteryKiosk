package src.MenuScreen;

import src.Main.LotteryKiosk;

import javax.swing.*;
import java.awt.*;

public class PowerballBanner extends MenuBanner {
    public PowerballBanner(LotteryKiosk lotteryKiosk){
        super(lotteryKiosk,"파워볼", Color.red);
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

    public JPanel getPowerBallPanel() {
        return this;
    }
}
