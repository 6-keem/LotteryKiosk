package src.AdministratorDialog;

import src.AdministratorDialog.ViewSales.ViewAll;
import src.AdministratorDialog.ViewSales.ViewMegaMillion;
import src.AdministratorDialog.ViewSales.ViewPowerBall;
import src.AdministratorDialog.ViewSales.ViewSales;
import src.Main.MainFrame;
import src.Utilities.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

// TODO: 2023-12-04 스레드에서 화면 껐을 때 경고창 안뜨는지 확인

public class AdministratorDialog extends JDialog {
    private final LogPanel logPanel = new LogPanel();;
    private final CardLayout cardLayout = new CardLayout();
    private final Container contentPane = getContentPane();
    private String filePath = null;
    private final Vector<ViewSales> viewSalesVector = new Vector<>();

    public AdministratorDialog(MainFrame mainFrame){
        super(mainFrame, "관리자", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane.setLayout(cardLayout);

        buildGUI();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Constants.setFilePath(null);
            }
        });
        setSize(400,400);
        setResizable(false);
        setVisible(true);
    }
    public void changePanel(int panelValue){
        switch (panelValue){
            case Constants.LOG -> {
                cardLayout.show(contentPane, Constants.LOG_PANEL);
            }
            case Constants.VIEW_ALL -> {
                cardLayout.show(contentPane, Constants.VIEW_ALL_PANEL);
                updateData();
            }
            case Constants.VIEW_POWER_BALL -> {
                cardLayout.show(contentPane, Constants.VIEW_POWER_BALL_PANEL);
                updateData();
            }
            case Constants.VIEW_MEGA_MILLION -> {
                cardLayout.show(contentPane, Constants.VIEW_MEGA_MILLION_PANEL);
                updateData();
            }
        }
    }
    private void updateData(){
        for(ViewSales vt : viewSalesVector)
            vt.updateData();
    }
    private void buildGUI(){
        setJMenuBar(createJMenuBar());
        ViewAll viewAll = new ViewAll(this);
        ViewPowerBall viewPowerBall = new ViewPowerBall(this);
        ViewMegaMillion viewMegaMillion = new ViewMegaMillion(this);
        viewSalesVector.add(viewAll);
        viewSalesVector.add(viewPowerBall);
        viewSalesVector.add(viewMegaMillion);

        contentPane.add(logPanel,Constants.LOG_PANEL);
        contentPane.add(viewAll,Constants.VIEW_ALL_PANEL);
        contentPane.add(viewPowerBall,Constants.VIEW_POWER_BALL_PANEL);
        contentPane.add(viewMegaMillion,Constants.VIEW_MEGA_MILLION_PANEL);
        Timer timer = new Timer();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                timer.setSessionFlag(false);
            }
        });
        timer.start();
    }
    public void appendLogMessage(String message){
        logPanel.appendLogMessage(message);
    }
    private JMenuBar createJMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        setBackground(new Color(215,215,215));
        setFont(new Font("맑은 고딕", Font.PLAIN,15));

        jMenuBar.add(createFileMenu());
        jMenuBar.add(createViewMenu());
        jMenuBar.add(createHelpMenu());
        return jMenuBar;
    }
    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem saveAs = new JMenuItem("SaveAs");

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TXT 필터링 https://creatordev.tistory.com/51
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");

                // TODO: 2023-11-20 컴퓨터 마다 경로 설정 다르게
                // 디렉터리 설정 https://stackoverflow.com/questions/13516829/jfilechooser-change-default-directory-in-windows
                chooser.setCurrentDirectory(new File("./resource/data/"));
                chooser.setFileFilter(filter);

                int ret = chooser.showOpenDialog(null);
                if (ret != JFileChooser.APPROVE_OPTION){
                    JOptionPane.showMessageDialog(null,"선택된 파일이 없습니다.","경고",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                filePath = chooser.getSelectedFile().getPath();
                logPanel.appendLogMessage("매출 데이터 추가 완료");
                Constants.setFilePath(filePath);
                updateData();
            }
        });
        // TODO: 2023-11-29 생성자에서 StringBuffer 받고 그 버퍼에 아무것도 저장 X면 그냥 리턴하고 저장되어있으면 Buffer열어서 저장하기
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: 2023-11-20 filter 설정 검색 https://creatordev.tistory.com/51
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");

                // TODO: 2023-11-20 컴퓨터 마다 경로 설정 다르게
                chooser.setCurrentDirectory(new File("./resource/data/"));
                chooser.setFileFilter(filter);

                int ret = chooser.showSaveDialog(null);
                if (ret != JFileChooser.APPROVE_OPTION){
                    JOptionPane.showMessageDialog(null,"선택된 파일이 없습니다.","경고",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(saveData(chooser.getSelectedFile().getPath()))
                    logPanel.appendLogMessage("매출 데이터 저장 완료");
                else
                    logPanel.appendLogMessage("매출 데이터 저장 실패");
            }
        });
        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(saveAs);
        return fileMenu;
    }
    private JMenu createViewMenu(){
        JMenu viewMenu = new JMenu("View");
        JMenuItem aSales = new JMenuItem("ALL Sales");
        JMenuItem pSales = new JMenuItem("PowerBall Sales");
        JMenuItem mSales = new JMenuItem("MegaMillions Sales");

        addActionLiseneter(aSales, Constants.VIEW_ALL);
        addActionLiseneter(pSales, Constants.VIEW_POWER_BALL);
        addActionLiseneter(mSales, Constants.VIEW_MEGA_MILLION);

        viewMenu.add(aSales);
        viewMenu.add(pSales);
        viewMenu.add(mSales);
        return viewMenu;
    }
    private void addActionLiseneter(JMenuItem item, int panelValue){
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(panelValue);
                logPanel.appendLogMessage(Constants.PANEL_NAME[panelValue]);
            }
        });
    }
    private JMenu createHelpMenu(){
        JMenu helpMenu = new JMenu("Help");
        JMenuItem jMenuItem = new JMenuItem("Help");
        jMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"판매처: 서울시 용산구\na/s문의: 02-123-5678","Help",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(jMenuItem);
        return helpMenu;
    }
    private boolean saveData(String saveFilePath){
        FileWriter fw = null;
        BufferedWriter bw = null;
        boolean flag = true;
        try{
            fw = new FileWriter(saveFilePath);
            bw = new BufferedWriter(fw);
            
            String []lines = viewSalesVector.get(0).getSaveSalesData().toString().split("\n");
            for(String line : lines){
                bw.write(line);
                bw.newLine();
            }
            Calendar today = Calendar.getInstance();
            bw.write(System.getProperty("line.separator"));
            bw.write("오늘 : " + today.get(Calendar.YEAR) + "년 " + (today.get(Calendar.MONTH) + 1) + "월 " + today.get(Calendar.DAY_OF_MONTH) + "일\n");
        } catch (IOException exception){
            exception.printStackTrace();
            flag = false;
        }finally {
            try{
                if(bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            }catch (IOException exception){
                exception.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
    public class Timer extends Thread{
        private int currentTimerCount = 0;
        private boolean sessionFlag = true;
        private boolean isExpired = false;
        public void setSessionFlag(boolean sessionFlag){this.sessionFlag = sessionFlag;}
        @Override
        public void run(){
            while(sessionFlag){
                try{
                    sleep(1000);
                    currentTimerCount++;
                    if(currentTimerCount == Constants.SESSION_EXPIRE_TIME){
                        JOptionPane.showMessageDialog(null, "세션이 만료 되었습니다. 다시 로그인하세요.","세션 만료",JOptionPane.WARNING_MESSAGE);
                        dispose();
                    }
                } catch (InterruptedException e){
                    JOptionPane.showMessageDialog(null,"세션 오류", "오류",JOptionPane.ERROR_MESSAGE);
                    isExpired = true;
                    return;
                }
            }
        }
    }
}
