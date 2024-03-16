package src.Main;

import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("로또 키오스크");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(null,"종료하시겠습니까?","확인",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(flag != JOptionPane.YES_OPTION)
                    JOptionPane.showMessageDialog(null,"종료가 취소되었습니다.","알림",JOptionPane.INFORMATION_MESSAGE);
                else
                    System.exit(0);
            }
        });
        setBackground(Color.DARK_GRAY);
        setSize(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
        setResizable(false);
        setFocusable(true);
        requestFocus();
    }
}
