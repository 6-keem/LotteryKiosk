package src.LotteryPurchaseScreen.Payment;

import javax.swing.*;
import java.io.*;
import java.util.Calendar;
import java.util.Vector;

public class Receipt extends JDialog {
    private String timeStamp;
    private String lotteryName = null;
    private Vector<Vector<Integer>> numbersVector;
    public Receipt(Vector<Vector<Integer>> numbersVector, String lotteryName){
        this.numbersVector = numbersVector;
        this.lotteryName = lotteryName;
        JOptionPane.showMessageDialog(null,getReceiptString(),"거래 영수증",JOptionPane.PLAIN_MESSAGE);
        receiptWrite();
    }

    private String getReceiptString(){
        String receipt = "                                 영수증\n";
        receipt += "-----------------------------------------------------------\n";
        receipt += getTimeStamp() + "\n";
        receipt += "==================================\n";
        receipt += "항목               단가       수량              금액\n";
        receipt += "==================================\n";
        receipt += lotteryName;
        if(receipt.equals("메가밀리언"))
            receipt += " ";
        receipt += "      6,000          " + numbersVector.size() + "                 " + (6000 * numbersVector.size()) + "\n";
        receipt += "-----------------------------------------------------------\n";

        for(int i = 0 ; i < numbersVector.size() ; i++){
            Vector<Integer> vt = numbersVector.get(i);
            receipt += "로또 " + (i+1) + " :   ";
            for(Integer number : vt){
                receipt += number + "     ";
            }
            receipt += "\n";
        }
        receipt += "-----------------------------------------------------------\n";
        return receipt;
    }
    private String getTimeStamp(){
        Calendar now = Calendar.getInstance();
        timeStamp = "거래일시 : " + now.get(Calendar.YEAR) + "년 " + (now.get(Calendar.MONTH) + 1) + "월 "
                + now.get(Calendar.DAY_OF_MONTH) + "일 " + now.get(Calendar.HOUR_OF_DAY) + "시 "
                + now.get(Calendar.MINUTE) + "분";
        return timeStamp;
    }
    private String getSalesDataString(){
        return lotteryName + "#" + timeStamp + "#" + numbersVector.size();
    }
    private void receiptWrite(){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try{
            fileWriter = new FileWriter("./resource/data/SalesData.txt",true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(getSalesDataString());
            bufferedWriter.newLine();
        } catch (IOException ioException){
            ioException.printStackTrace();
        } finally {
            try {
                if(bufferedWriter != null)
                    bufferedWriter.close();
                if(fileWriter !=null)
                    fileWriter.close();
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    private StringBuffer receiptRead(){
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        StringBuffer readData = new StringBuffer();
        try{
            fileReader = new FileReader("SalesData.txt");
            bufferedReader = new BufferedReader(fileReader);

            String msg;
            while((msg = bufferedReader.readLine()) != null)
                readData.append(msg);

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
        return readData;
    }
}
