package src.Utilities;

import src.LotteryPurchaseScreen.NumberPanel;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

public class Constants {
    // screen option
    public static final int FRAME_WIDTH = 550;
    public static final int FRAME_HEIGHT = 820;

    // screen value
    public static final int MAIN = 0;
    public static final int POWERBALL = 1;
    public static final int MEGAMILLIONS = 2;
    public static final int NUMBERCHECK = 3;

    // lotto vector size
    public static final int MAXLOTTOAMOUNT = 6;

    // payment Dialog & thread value
    public static final int PAID = 0;
    public static final int PAYMENTFAILED = 1;
    public static final int PAYMENTINPROGRESS = 2;
    public static final int CANCELED = 3;

    // login from flag value
    public static final boolean LOGIN_SUCCESS = true;
    public static final boolean LOGIN_FAILED = false;

    // authorization frame panel value
    public static final int LOG = 0;
    public static final int VIEW_ALL = 1;
    public static final int VIEW_POWER_BALL = 2;
    public static final int VIEW_MEGA_MILLION = 3;

    // value for when panel switched
    public static final String LOG_PANEL = "logPanel";
    public static final String VIEW_ALL_PANEL = "viewAllPanel";
    public static final String VIEW_POWER_BALL_PANEL = "viewPowerBallPanel";
    public static final String VIEW_MEGA_MILLION_PANEL = "viewMegaMillionPanel";
    public static final String[] PANEL_NAME = {"로그", "판매 실적 조회", "파워볼 판매 실적 조회", "메가밀리언 판매 실적 조회"};
    //
    public static final int POWERBALL_NORMAL_START_NUMBER = 1;
    public static final int POWERBALL_NORMAL_END_NUMBER = 69;
    public static final int POWERBALL_BONUS_START_NUMBER = 1;
    public static final int POWERBALL_BONUS_END_NUMBER = 26;
    public static final int MEGAMILLION_NORMAL_START_NUMBER = 1;
    public static final int MEGAMILLION_NORMAL_END_NUMBER = 70;
    public static final int MEGAMILLION_BONUS_START_NUMBER = 1;
    public static final int MEGAMILLION_BONUS_END_NUMBER = 25;

    public static final int PAYMENT_EXPIRE_TIME = 15;
    public static final int SESSION_EXPIRE_TIME = 180;

    public static final String NORMAL_START = "NORMAL_START_NUMBER";
    public static final String NORMAL_END = "NORMAL_END_NUMBER";
    public static final String BONUS_START = "BONUS_START_NUMBER";
    public static final String BONUS_END = "BONUS_END_NUMBER";

    public static final int ARC_WIDTH = 20;
    public static final int ARC_HEIGHT = 20;
    private static String filePath = null;
    public static void setFilePath(String path) { filePath = path;}
    public static String getFilePath(){return filePath;}

    private static final HashMap<String, Integer> pbHashMap = new HashMap<>() {{
        put(NORMAL_START, POWERBALL_NORMAL_START_NUMBER);
        put(NORMAL_END, POWERBALL_NORMAL_END_NUMBER);
        put(BONUS_START, POWERBALL_BONUS_START_NUMBER);
        put(BONUS_END, POWERBALL_BONUS_END_NUMBER);
    }};
    private static final HashMap<String, Integer> mmHashMap = new HashMap<>() {{
        put(NORMAL_START, MEGAMILLION_NORMAL_START_NUMBER);
        put(NORMAL_END, MEGAMILLION_NORMAL_END_NUMBER);
        put(BONUS_START, MEGAMILLION_BONUS_START_NUMBER);
        put(BONUS_END, MEGAMILLION_BONUS_END_NUMBER);
    }};

    private static final HashMap<String, String> lotterNameKOR2ENG = new HashMap<>() {{
        put("파워볼", "Powerball");
        put("메가밀리언", "Megamillion");
    }};
    public static boolean isValidNumber(Vector<Integer> vt, String lotteryName){
        if(vt.size() < 6)
            return false;
        HashMap<String, Integer> hashMap = null;
        if(lotteryName.equals("파워볼"))
            hashMap = pbHashMap;
        else
            hashMap = mmHashMap;
        for(int i = 0 ; i < vt.size() ; i++){
            int number = vt.get(i), count = -1;
            for(int j = 0 ; j < vt.size()-1 ; j++){
                if(number == vt.get(j)){
                    count++;
                }
            }
            if (count > 0)
                return false;
            if(i == vt.size()-1){
                if(number < hashMap.get(BONUS_START) || number > hashMap.get(BONUS_END))
                    return false;
            }
            else {
                if(number < hashMap.get(NORMAL_START) || number > hashMap.get(NORMAL_END))
                    return false;
            }
        }
        return true;
    }

    public static Vector<Boolean> isValidLottery(Vector<NumberPanel> vt, String lotteryName){
        Vector<Boolean> validFlags = new Vector<>();
        for(NumberPanel panel : vt){
            boolean flag = true;
            Vector<Integer> numberVector = new Vector<>();
            for(JTextField textField : panel.getjTextFields()){
                try{
                    numberVector.add(Integer.parseInt(textField.getText()));
                } catch (NullPointerException | NumberFormatException e){
                    validFlags.add(false);
                    flag = false;
                    break;
                }
            }
            if(flag)
                validFlags.add(isValidNumber(numberVector, lotteryName));
        }
        return validFlags;
    }

    public static Vector<Integer> createAutoLotteryNumber(String lotteryName){
        Vector<Integer> vt = new Vector<>(6);
        HashMap<String, Integer> hashMap = null;
        if(lotteryName.equals("파워볼"))
            hashMap = pbHashMap;
        else
            hashMap = mmHashMap;
        while(vt.size() < 5){
            boolean flag = true;
            int r = (int) (Math.random() * (hashMap.get(NORMAL_END) - hashMap.get(NORMAL_START) + 1) + hashMap.get(NORMAL_START));
            for (Integer n : vt){
                if(n == r){
                    flag = false;
                    break;
                }
            }
            if (flag)
                vt.add(r);
        }
        Collections.sort(vt);
        vt.add((int)(Math.random() * (hashMap.get(BONUS_END) - hashMap.get(BONUS_START) + 1) + hashMap.get(BONUS_START)));
        return vt;
    }

    public static void updateLotteryWinningNumber(String lotteryName){
        Vector<Integer> lotteryNumber = createAutoLotteryNumber(lotteryName);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter("./resource/data/WinningNumber" + lotterNameKOR2ENG.get(lotteryName) + ".txt");
            bw = new BufferedWriter(fw);

            for(int i = 0 ; i < lotteryNumber.size() ; i ++){
                bw.write(Integer.toString(lotteryNumber.get(i)));
                if(i != lotteryNumber.size()-1)
                    bw.write("/");
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }finally {
            try{
                if(bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
    }
}
