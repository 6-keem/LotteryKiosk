package src.MenuScreen;

import src.AdministratorDialog.AdministratorDialog;
import src.AdministratorDialog.LoginForm;
import src.Main.LotteryKiosk;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MenuScreen extends JPanel {
    private Vector<MenuBanner> selectPanels = new Vector<>();
    private LotteryKiosk lotteryKiosk;
    public MenuScreen(LotteryKiosk lotteryKiosk){
        this.lotteryKiosk = lotteryKiosk;
        setLayout(new BorderLayout());
        buildGUI(lotteryKiosk);
    }

    private void buildGUI(LotteryKiosk lotteryKiosk) {
        JLabel logo = new JLabel(new ImageIcon("resource/KioskLogo/seven.png"));
        logo.setHorizontalAlignment(JLabel.CENTER);
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 3){
                    LoginForm loginForm = new LoginForm(lotteryKiosk.getMainFrame());
                    if(loginForm.getLoginTreid() && loginForm.getLoginFlag() == Constants.LOGIN_SUCCESS){
                        AdministratorDialog administratorDialog = new AdministratorDialog(lotteryKiosk.getMainFrame());
                    }
                }
            }
        });

        selectPanels.add(new PowerballBanner(lotteryKiosk));
        selectPanels.add(new MegamillionBanner(lotteryKiosk));
        selectPanels.add(new WinConfrimBanner(lotteryKiosk));

        add(logo,BorderLayout.NORTH);
        add(menu(), BorderLayout.CENTER);
    }

    private JPanel menu(){
        JPanel panel = new JPanel(new GridLayout(3,1));
        try{
            for(MenuBanner subPanels: selectPanels)
                panel.add(subPanels);
        } catch (NullPointerException e){
            e.printStackTrace();
            System.exit(1);
        }
        return panel;
    }
}
