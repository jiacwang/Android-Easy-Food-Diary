package cis350.upenn.edu.easyfooddiary;

/**
 * Created by jiachenwang on 11/20/17.
 */

public class SleepHours {
    protected String sleepMon, sleepTue, sleepWed, sleepThur,sleepFri;

    public SleepHours(){

    }

    public void setSleepMon(String s){
        this.sleepMon=s;
    }

    public void setSleepTue(String s){
        this.sleepTue=s;
    }

    public void setSleepWed(String s){
        this.sleepWed=s;
    }

    public void setSleepThur(String s){
        this.sleepThur=s;
    }

    public void setSleepFri(String s){
        this.sleepFri=s;
    }


    public String getSleepMon(){
        return sleepMon;
    }

    public String getSleepTue(){
        return sleepTue;
    }

    public String getsleepWed(){
        return sleepWed;
    }

    public String getsleepThur(){
        return sleepThur;
    }

    public String getsleepFri(){
        return sleepFri;
    }

}
