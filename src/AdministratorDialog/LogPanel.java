package src.AdministratorDialog;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private final JTextArea jTextArea = new JTextArea("root@root:~$ ");
    private JScrollPane jScrollPane = new JScrollPane(jTextArea);
    public LogPanel(){
        setOpaque(true);
        buildGUI();
        add(jScrollPane);
    }
    private void buildGUI(){
        setBackground(Color.black);
        setForeground(Color.white);
        jTextArea.setColumns(33);
        jTextArea.setRows(18);
        jTextArea.setOpaque(true);
        jTextArea.setBackground(Color.black);
        jTextArea.setForeground(Color.white);
        jTextArea.setEditable(false);
        appendLogMessage("로그인 성공");
    }
    public void appendLogMessage(String message){
        String prompt = "root@root:~$ ";
        jTextArea.append(message + " ...\n" + prompt);
    }
}
