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

public class LineChartActivity extends Activity {

    private static String TAG = "MainActivity";

    private float[] yData = new float[7];
    private String[] xData = {"Monday", "Tuesday" , "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    LineChart lineChart;
    protected JSONArray info;
    protected Calories col = new Calories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linechartdistribution);//linechartActivity一旦启动，set linechartdistribution layout
        Log.d(TAG, "onCreate: starting to create chart");


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_calories = FirebaseDatabase.getInstance().getReference("calories").child(user.getUid());

        myref_calories.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s = dataSnapshot.getValue(String.class);
                try {
                    if (s == null) {
                        String[] arr = {"", "", "", "", "", "", ""};
                        info = new JSONArray(arr);
                    } else {
                        info = new JSONArray(s);
                    }
                    col.setCaloriesMon((String) info.get(0));
                    col.setCaloriesTue((String) info.get(1));
                    col.setCaloriesWed((String) info.get(2));
                    col.setCaloriesThur((String) info.get(3));
                    col.setCaloriesFri((String) info.get(4));
                    col.setCaloriesSat((String) info.get(5));
                    col.setCaloriesSun((String) info.get(6));


                    yData[0] = Float.parseFloat(col.getCaloriesMon());
                    yData[1] = Float.parseFloat(col.getCaloriesTue());
                    yData[2] = Float.parseFloat(col.getCaloriesWed());
                    yData[3] = Float.parseFloat(col.getCaloriesThur());
                    yData[4] = Float.parseFloat(col.getCaloriesFri());
                    yData[5] = Float.parseFloat(col.getCaloriesSat());
                    yData[6] = Float.parseFloat(col.getCaloriesSun());

                    setLineChart();
                    //More options just check out the documentation!

                    addDataSet();



                } catch (JSONException e) {
                    Toast.makeText(LineChartActivity.this, "Error1", Toast.LENGTH_SHORT).show();
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

    //下面的方法，把数据加进去，上面是把chart架构搭出来。
    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<Entry> yEntrys = new ArrayList<>();


        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new Entry(i , yData[i]));
        }


        LineDataSet set1 = new LineDataSet(yEntrys,"Data Set 1");

        set1.setFillAlpha(110);

        set1.setColor(Color.RED);

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
