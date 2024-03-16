package src.ConfrimationWinningScreen;

import src.Utilities.Constants;

import javax.swing.*;
import java.util.Vector;

public class WinningCheck {
    private Vector<Integer> winningNumber = null;
    private Vector<JTextField> userNumber = null;
    private int normalStart, normalEnd, bonusStart, bonusEnd;
    private String lotteryName = null;
    public WinningCheck(Vector<Integer> winningNumber, Vector<JTextField> userNumber, String lotteryName){
        this.winningNumber = winningNumber;
        this.userNumber = userNumber;
        this.lotteryName = lotteryName;
        if(!checkIsValidNumber())
            JOptionPane.showMessageDialog(null,"잘못된 번호가 입력 되었습니다.","경고",JOptionPane.ERROR_MESSAGE);
        else
            checkIsWin();
    }

    private boolean checkIsValidNumber(){
        if (lotteryName.equals("파워볼")){
            normalStart = Constants.POWERBALL_NORMAL_START_NUMBER;
            normalEnd = Constants.POWERBALL_NORMAL_END_NUMBER;
            bonusStart = Constants.POWERBALL_BONUS_START_NUMBER;
            bonusEnd = Constants.POWERBALL_BONUS_END_NUMBER;
        }
        else {
            normalStart = Constants.MEGAMILLION_NORMAL_START_NUMBER;
            normalEnd = Constants.MEGAMILLION_NORMAL_END_NUMBER;
            bonusStart = Constants.MEGAMILLION_BONUS_START_NUMBER;
            bonusEnd = Constants.MEGAMILLION_BONUS_END_NUMBER;
        }

        try{
            for(int i = 0 ; i < userNumber.size() ; i++){
                int number = Integer.parseInt(userNumber.get(i).getText()), count = -1;
                for(int j = 0 ; j < userNumber.size()-1 ; j++){
                    if(number == Integer.parseInt(userNumber.get(j).getText())){
                        count++;
                    }
                }
                if (count > 0)
                    return false;
                if(i == winningNumber.size()-1){
                    if(number < bonusStart || number > bonusEnd)
                        return false;
                }
                else {
                    if(number < normalStart || number > normalEnd)
                        return false;
                }
            }
            return true;
        } catch (NumberFormatException | NullPointerException exception){
            //exception.printStackTrace();
            return false;
        }
    }
    private void checkIsWin(){
        int corretCount = 0;
        boolean bonusFlag = false;
        for(int i = 0 ; i < userNumber.size(); i++){
            int number = Integer.parseInt(userNumber.get(i).getText());
            for(int j = 0 ; j < userNumber.size()-1 ; j++){
                if(number == winningNumber.get(j)){
                    corretCount++;
                }
            }
            if(i == winningNumber.size()-1){
                if(number == winningNumber.get(i))
                    bonusFlag = true;
            }
        }

        String message = lotteryName + " - ";
        switch(corretCount){
            case 0, 1 -> {
                if(bonusFlag)
                    message += "$4 당첨, 구매처에 문의하세요";
                else
                    message += "꽝 입니다.";
            }
            case 2 -> {
                if(bonusFlag)
                    message += "$7 당첨, 구매처에 문의하세요";
                else
                    message += "꽝 입니다.";
            }
            case 3 ->{
                if(bonusFlag)
                    message += "$100 당첨, 구매처에 문의하세요";
                else
                    message += "$7 당첨, 구매처에 문의하세요";
            }
            case 4->{
                if(bonusFlag)
                    message += "$50,000 당첨, 가까운 영업점에 방문하세요";
                else
                    message += "$100 당첨, 구매처에 문의하세요";
            }
            case 5->{
                if(bonusFlag)
                    message += "Grand Prize 당첨, 본사에 방문하세요";
                else
                    message += "$1 Million 당첨, 본사에 방문하세요";
            }
        }
        if(message.equals(lotteryName + " - "))
            message += "꽝 입니다.";
        JOptionPane.showMessageDialog(null,message,"결과",JOptionPane.INFORMATION_MESSAGE);
    }
}
