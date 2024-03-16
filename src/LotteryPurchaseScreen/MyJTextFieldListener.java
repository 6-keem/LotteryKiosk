package src.LotteryPurchaseScreen;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class MyJTextFieldListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e){
        JTextField field = (JTextField) e.getSource();
        try{
            if(Character.isAlphabetic(e.getKeyChar())){
                field.setText("");
            }
            else if(Character.isDigit(e.getKeyChar())){
                if(field.getText().length() >= 2){
                    field.setText("");
                }
            }
        } catch (StringIndexOutOfBoundsException execption){
            field.setText("");
        }
    }
}