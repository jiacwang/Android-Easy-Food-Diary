package cis350.upenn.edu.easyfooddiary;

import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by Shuangchen Shen on 10/25/17.
 */

public class Date {
    protected String date;
    protected String monthyear;

    public Date(){
        this.date = date;
        this.monthyear = monthyear;
    }

    public void setDate(String s){
        date = s;
    }

    public void setMonthyear(String s){
        monthyear =s;
    }


    public String getDate(){
        return date;
    }

    public java.lang.String getMonthyear() {
        return monthyear;
    }


}
