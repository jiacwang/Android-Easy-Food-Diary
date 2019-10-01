package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;

/**
 * Created by haile on 2/24/2017.
 */

public class CalendarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        final CalendarView view = (CalendarView) findViewById(R.id.calendar);
        long date = Calendar.getInstance().getTimeInMillis();
        view.setDate(date);
        final String type = getIntent().getExtras().getString("Type");

        view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {//user select a date, and choose the correct activity
            public void startRelevantEvent(Class ActivityName,int year, int month, int dayOfMonth){
                Intent i = new Intent(CalendarActivity.this, ActivityName);
                int month_add;
                month_add = month + 1;
                i.putExtra("DATE", month_add + "," + dayOfMonth + "," + year);
                i.putExtra("MONTHYEAR", month_add + "," + year);
                view.getContext().startActivity(i);
            }
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                if (type.equals("calendar")) {
                    startRelevantEvent(FoodActivity.class, year, month, dayOfMonth);
                } else if (type.equals("vitals")) {
                    startRelevantEvent(VitalsActivity.class,year,month,dayOfMonth);
                } else {
                    startRelevantEvent(SleepActivity.class,year, month, dayOfMonth);
                }
            }
        });
    }

    public void onClick(View view) {
        //have to differentiate amongst buttons
       /* if (view.getId() == R.id.main) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }*/
    }

}
