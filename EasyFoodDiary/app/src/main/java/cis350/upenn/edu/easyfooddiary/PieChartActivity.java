package cis350.upenn.edu.easyfooddiary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
 * Created by Daniel on 4/7/2017.
 */

public class PieChartActivity extends Activity {
    private static String TAG = "MainActivity";

    private float[] yData = new float[3];
    private String[] xData = {"Carbs", "Protein" , "Fat"};
    PieChart pieChart;
    protected JSONArray info;
    protected Nutrition ntr = new Nutrition();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.piechartdistribution);
        Log.d(TAG, "onCreate: starting to create chart");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_nutrition = FirebaseDatabase.getInstance().getReference("nutrition").child(user.getUid());

        myref_nutrition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s = dataSnapshot.getValue(String.class);
                try {
                    if (s == null) {
                        String[] arr = {"", "", "", ""};
                        info = new JSONArray(arr);
                    } else {
                        info = new JSONArray(s);
                    }
                    ntr.setCalories((String) info.get(0));
                    ntr.setCarbs((String) info.get(1));
                    ntr.setProtein((String) info.get(2));
                    ntr.setFat((String) info.get(3));


                    yData[0] = Float.parseFloat(ntr.getCarbs());
                    yData[1] = Float.parseFloat(ntr.getProtein());
                    yData[2] = Float.parseFloat(ntr.getFat());

                    setPieChart();
                    //More options just check out the documentation!

                    addDataSet();



                } catch (JSONException e) {
                    Toast.makeText(PieChartActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    protected void setPieChart(){
        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setRotationEnabled(true);

        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Macro Goals Distribution");
        pieChart.setCenterTextSize(10);
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Macro Percentages (Carbs, Protein, Fat)");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(146, 174, 150));//green
        colors.add(Color.rgb(119, 138, 166));//deeper gray
        colors.add(Color.rgb(119, 164, 166));//light green
        colors.add(Color.rgb(157, 164, 166));//gray
        colors.add(Color.rgb(217, 161, 150));//pink
        colors.add(Color.rgb(247, 202, 150));//orange
        colors.add(Color.rgb(212, 202, 236));//purple
        colors.add(Color.rgb(192, 164, 182));//light purple

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

}
