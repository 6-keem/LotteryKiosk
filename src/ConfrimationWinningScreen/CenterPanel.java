package src.ConfrimationWinningScreen;

import src.Utilities.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class CenterPanel extends JPanel {
    private NumberPanel powerBallPanel = null;
    private NumberPanel megaMillionPanel = null;
    private Vector<Vector<Integer>> vt = null;
    public CenterPanel(){
        readData();
        buildGUI();
    }
    private void buildGUI(){
        add(createSubPanel());
    }
    public void updateNumber(){
        readData();
        powerBallPanel.updateNumber(vt.get(0));
        megaMillionPanel.updateNumber(vt.get(1));
    }
    private JPanel createSubPanel(){
        JPanel subPanel = new JPanel();
        powerBallPanel = new NumberPanel(vt.get(0),"파워볼");
        megaMillionPanel = new NumberPanel(vt.get(1),"메가밀리언");
        powerBallPanel.setBorder(BorderFactory.createLineBorder(new Color(10,10,30),3));
        megaMillionPanel.setBorder(BorderFactory.createLineBorder(new Color(10,10,30),3));
        subPanel.setLayout(new GridLayout(0,1));
        subPanel.add(powerBallPanel);
        subPanel.add(megaMillionPanel);
        return subPanel;
    }
    private void readData(){
        vt = new Vector<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try{
            fileReader = new FileReader("./resource/data/WinningNumberPowerball.txt");
            bufferedReader = new BufferedReader(fileReader);

            String msg;
            while((msg = bufferedReader.readLine()) != null)
                vt.add(toVector(msg));
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

        try{
            fileReader = new FileReader("./resource/data/WinningNumberMegamillion.txt");
            bufferedReader = new BufferedReader(fileReader);

            String msg;
            while((msg = bufferedReader.readLine()) != null)
                vt.add(toVector(msg));
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
    }
    private Vector<Integer> toVector(String message){
        Vector<Integer> vector = new Vector<>();
        String []tokens = message.split("/");
        try{
            for(String token : tokens){
                vector.add(Integer.parseInt(token));
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return vector;
    }
}
