package cis350.upenn.edu.easyfooddiary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by JiachenWang on 11/20/2017.
 */

public class LineChartActivitySleep extends Activity {

    private static String TAG = "MainActivity";

    private float[] yData = new float[5];
    private String[] xData = {"Monday", "Tuesday" , "Wednesday", "Thursday", "Friday"};
    LineChart lineChart;
    protected JSONArray info;
    protected SleepHours slp = new SleepHours();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechartdistribution);
        Log.d(TAG, "onCreate: starting to create chart");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_sleepingTime = FirebaseDatabase.getInstance().getReference("sleep").child(user.getUid());

        myref_sleepingTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s = dataSnapshot.getValue(String.class);
                try {
                    if (s == null) {
                        String[] arr = {"", "", "", "", ""};
                        info = new JSONArray(arr);
                    } else {
                        info = new JSONArray(s);
                    }
                    slp.setSleepMon((String) info.get(0));
                    slp.setSleepTue((String) info.get(1));
                    slp.setSleepWed((String) info.get(2));
                    slp.setSleepThur((String) info.get(3));
                    slp.setSleepFri((String) info.get(4));



                    yData[0] = Float.parseFloat(slp.getSleepMon());
                    yData[1] = Float.parseFloat(slp.getSleepTue());
                    yData[2] = Float.parseFloat(slp.getsleepWed());
                    yData[3] = Float.parseFloat(slp.getsleepThur());
                    yData[4] = Float.parseFloat(slp.getsleepFri());


                    setLineChart();
                    //More options just check out the documentation!

                    addDataSet();



                } catch (JSONException e) {
                    Toast.makeText(LineChartActivitySleep.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    protected void setLineChart(){
        lineChart = (LineChart) findViewById(R.id.idLineChart);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

    }


    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<Entry> yEntrys = new ArrayList<>();


        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new Entry(i , yData[i]));
        }


        LineDataSet set1 = new LineDataSet(yEntrys,"Sleeping Time during Weekday");

        set1.setFillAlpha(110);

        set1.setColor(Color.BLUE);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);

        LineData data= new LineData(dataSets);

        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xData));
        xAxis.setGranularity(1);


    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter{
        private String[] mValues;
        public MyXAxisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return mValues[(int)value];
        }

    }

}
