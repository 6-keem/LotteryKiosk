package src.AdministratorDialog;

import src.Main.MainFrame;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginForm extends JDialog {
    private JTextField idField = null;
    private JPasswordField passwordField = null;
    private boolean loginFlag = false;
    private boolean loginTried = false;
    public LoginForm(MainFrame mainFrame) {
        // TODO: 2023-11-20 로그인 창 뜨면 메인프레임 스레드 재움
        super(mainFrame,"관리자 로그인", true);
        setTitle("authorization confirm");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(0,1));
        buildGUI();
        setSize(300,200);
        setFocusable(true);
        setVisible(true);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                requestFocus();
            }
        });
    }
    private void createPopUp(){
        if(getLoginFlag() == Constants.LOGIN_SUCCESS)
            JOptionPane.showMessageDialog(null,"로그인 되었습니다.", "확인", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null,"아이디와 비밀번호를 확인하세요", "경고", JOptionPane.ERROR_MESSAGE);
    }
    public boolean getLoginFlag(){return loginFlag;}
    private void loginProcess(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        String id = "";
        String pw = "";

        try{
            fileReader = new FileReader("./resource/data/UserData.txt");
            bufferedReader = new BufferedReader(fileReader);

            String msg;
            for(int i = 0 ; (msg = bufferedReader.readLine()) != null ; i ++){
                if(i == 0)
                    id = msg;
                else
                    pw = msg;
            }

        } catch (IOException ioException){
            ioException.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null)
                    bufferedReader.close();
                if(fileReader !=null)
                    fileReader.close();
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
        if (id.equals(idField.getText()) && pw.equals(passwordField.getText()))
            this.loginFlag = Constants.LOGIN_SUCCESS;
        else
            this.loginFlag = Constants.LOGIN_FAILED;
        this.loginTried = true;
    }
    public boolean getLoginTreid(){return loginTried;}
    private void buildGUI(){
        add(createLoginLabel(), BorderLayout.NORTH);
        add(createLoginPanel(),BorderLayout.CENTER);
        add(createLoginButton(),BorderLayout.SOUTH);
    }
    private JButton createLoginButton(){
        JButton loginButton = new JButton("로그인");
        loginButton.setSize(getWidth(),getHeight());
        loginButton.setHorizontalAlignment(JButton.CENTER);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 2023-11-20 로그인 완성
                confirmAuthorization();
            }
        });
        return loginButton;
    }
    private void confirmAuthorization(){
        loginProcess();
        dispose();
        createPopUp();
    }
    private JLabel createLoginLabel(){
        JLabel login = new JLabel("Login");
        login.setFont(new Font("맑은 고딕 Bold", Font.BOLD, 25));
        login.setHorizontalAlignment(JLabel.CENTER);
        return login;
    }
    private JPanel createLoginPanel(){
        JPanel loginPanel = new JPanel(new GridLayout(2,1));
        loginPanel.add(new JLabel("아이디: "));
        loginPanel.add(createidField());
        loginPanel.add(new JLabel("패스워드: "));
        loginPanel.add(createPasswordField());
        return loginPanel;
    }
    private JTextField createidField(){
        idField = new JTextField();
        idField.setFont(new Font("맑은 고딕",Font.PLAIN, 15));
        idField.setColumns(10);
        return idField;
    }
    private JPasswordField createPasswordField(){
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("맑은 고딕",Font.PLAIN, 15));
        passwordField.setColumns(10);
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAuthorization();
            }
        });
        return passwordField;
    }
}
