package src.AdministratorDialog.ViewSales;

import src.AdministratorDialog.AdministratorDialog;
import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.StringTokenizer;

public class SalesCenterPanel extends JPanel {
    private final boolean powerBall, megaMillion;
    private JTextArea jTextArea;
    private int totalSalesCount = 0;
    private AdministratorDialog administratorDialog = null;
    private StringBuffer saveSalesData = null;
    public SalesCenterPanel(AdministratorDialog administratorDialog, StringBuffer saveSalesData, boolean powerBall, boolean megaMillion){
        this.administratorDialog = administratorDialog;
        this.saveSalesData = saveSalesData;
        this.powerBall = powerBall;
        this.megaMillion = megaMillion;
        setLayout(new BorderLayout());
        buildGUI();
        readSalesData();
    }
    private void buildGUI(){
        add(createLineLabel(), BorderLayout.NORTH);
        add(createCenter(), BorderLayout.CENTER);
        add(createLineLabel(), BorderLayout.SOUTH);
    }
    protected int getTotalSalesCount(){return totalSalesCount;}
    protected void setTotalSalesCount(){this.totalSalesCount = 0;}
    private JLabel createLineLabel(){
        return new JLabel("-----------------------------------------------------------------------------------------------");
    }
    private JScrollPane createCenter(){
        jTextArea = new JTextArea("");
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jTextArea.setColumns(33);
        jTextArea.setRows(18);
        jTextArea.setOpaque(true);
        jTextArea.setBackground(Color.white);
        jTextArea.setForeground(Color.black);
        jTextArea.setEditable(false);
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                administratorDialog.changePanel(Constants.LOG);
            }
            }
        });
        return jScrollPane;
    }
    protected void updateData(){readSalesData();}
    private void readSalesData(){
        String str = null;
        if(powerBall && megaMillion)
            str = "전체";
        else if(!megaMillion)
            str = "파워볼";
        else
            str = "메가밀리언";
        jTextArea.setText("");
        if(Constants.getFilePath() == null)
            return;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer originalData = new StringBuffer();
        try{
            fileReader = new FileReader(Constants.getFilePath());
            bufferedReader = new BufferedReader(fileReader);

            String msg;
            if(!bufferedReader.readLine().equals("매출데이터")){
                jTextArea.append("매출 데이터 오류");
                jTextArea.setForeground(Color.red);
                return;
            }
            jTextArea.setForeground(Color.black);
            for(int i = 0 ; (msg = bufferedReader.readLine()) != null ; i ++){
                appendSalesData(i, msg);
                originalData.append(msg + "\n");
            }
        } catch (IOException ioException){
            ioException.printStackTrace();
            Constants.setFilePath(null);
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
        processingSaveData(originalData);
    }
    private void processingSaveData(StringBuffer originalData){
        int total, powerballTotal, megamillionTotal;
        int todayTotal, powerballTodayTotal, megamillionTodayTotal;
        total = powerballTotal = megamillionTotal = todayTotal = powerballTodayTotal = megamillionTodayTotal = 0;
        StringTokenizer tokenizer = new StringTokenizer(originalData.toString(),"\n");
        while(tokenizer.hasMoreTokens()){
            String str = tokenizer.nextToken();
            String []tokens = str.split("#");
            if(tokens[0].equals("파워볼")){
                if(isToday(tokens[1]))
                    powerballTodayTotal += 6000 * Integer.parseInt(tokens[2]);
                powerballTotal += 6000 * Integer.parseInt(tokens[2]);
            }
            else {
                if(isToday(tokens[1]))
                    megamillionTodayTotal += 6000 * Integer.parseInt(tokens[2]);
                megamillionTotal += 6000 * Integer.parseInt(tokens[2]);
            }
        }
        total = powerballTotal + megamillionTotal;
        todayTotal = powerballTodayTotal + megamillionTodayTotal;

        String str = "전체 매출 - " + (total / 6000) + "개 / " + total + "원\n" +
                "파워볼 매출 - " + (powerballTotal / 6000) + "개 / " + powerballTotal + "원\n" +
                "메가밀리언 매출 - " + (megamillionTotal / 6000) + "개 / " + megamillionTotal + "원\n" +
                "------------------------------------------------\n" +
                "금일 전체 매출 - " + (todayTotal / 6000) + "개 / " + todayTotal + "원\n" +
                "금일 파워볼 매출 - " + (powerballTodayTotal / 6000) + "개 / " + powerballTodayTotal + "원\n" +
                "금일 메가밀리언 매출 - " + (megamillionTodayTotal / 6000) + "개 / " + megamillionTodayTotal + "원\n";
        saveSalesData.replace(0,saveSalesData.length(),str);
    }
    private boolean isToday(String date){
        Calendar today = Calendar.getInstance();
        if(date.indexOf(today.get(Calendar.YEAR) + "년") == -1)
            return false;
        if(date.indexOf(today.get(Calendar.MONTH) + 1 + "월") == -1)
            return false;
        if(date.indexOf(today.get(Calendar.DAY_OF_MONTH) + "일") == -1)
            return false;
        return true;
    }
    private void appendSalesData(int dealNumber, String message){
        String []tokens = message.split("#");
        if(tokens.length != 3 || (!tokens[0].equals("파워볼") && !tokens[0].equals("메가밀리언"))){
            Constants.setFilePath(null);
            return;
        }
        if(powerBall && tokens[0].equals("파워볼"))
            jTextArea.append(createMessageString(dealNumber,tokens));
        else if(megaMillion && tokens[0].equals("메가밀리언"))
            jTextArea.append(createMessageString(dealNumber,tokens));
    }
    private String createMessageString(int dealNumber, String []tokens){
        try {
            totalSalesCount += Integer.parseInt(tokens[2]);
            return (dealNumber + 1)+ "    :        " + tokens[1].replace("거래일시 : ", "") + "                |  " + tokens[2] + "개\n";
        } catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException exception){
            exception.printStackTrace();
        }
        return null;
    }
}
