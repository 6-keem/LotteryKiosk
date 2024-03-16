package src.MenuScreen;

import src.Utilities.Constants;

import javax.swing.*;
import java.util.Calendar;

public class Timer extends Thread{
    private JLabel refreshTimeLabel,remaineTimeLabel;
    private Calendar refreshTime;
    private String lotteryName = null;
    public Timer(JLabel refreshTimeLabel, JLabel remaineTimeLabel, Calendar refreshTime,String lotteryName){
        this.refreshTimeLabel = refreshTimeLabel;
        this.remaineTimeLabel = remaineTimeLabel;
        this.refreshTime = refreshTime;
        this.lotteryName = lotteryName;
        setTimeText();
    }
    private void setTimeText(){
        refreshTimeLabel.setText("추첨 : " + refreshTime.get(Calendar.YEAR) + "년 " + (refreshTime.get(Calendar.MONTH) + 1) + "월 " + refreshTime.get(Calendar.DAY_OF_MONTH) + "일 " +
                refreshTime.get(Calendar.HOUR_OF_DAY) + "시 " + refreshTime.get(Calendar.MINUTE) + "분");
    }
    @Override
    public void run(){
        while(true){
            try{
                Calendar currentTime = Calendar.getInstance();
                long millSec = refreshTime.getTimeInMillis() - currentTime.getTimeInMillis();
                if(millSec > 0){
                    long sec = millSec / 1000;
                    long min = sec / 60;
                    sec %= 60;
                    long hour = min / 60;
                    min %= 60;
                    remaineTimeLabel.setText("남은 시간 : " + hour + "시간 " + min + "분 " + sec + "초");
                    sleep(1000);
                } else {
                    refreshTime = Calendar.getInstance();
                    refreshTime.set(refreshTime.get(Calendar.YEAR), refreshTime.get(Calendar.MONTH), refreshTime.get(Calendar.DAY_OF_MONTH) + 1,
                            0,0,0);
//                    refreshTime.set(refreshTime.get(Calendar.YEAR), refreshTime.get(Calendar.MONTH), refreshTime.get(Calendar.DAY_OF_MONTH),
//                            refreshTime.get(Calendar.HOUR_OF_DAY),refreshTime.get(Calendar.MINUTE)+1,0);
                    setTimeText();
                    Constants.updateLotteryWinningNumber(lotteryName);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
