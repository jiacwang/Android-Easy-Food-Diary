package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by jiachenwang on 11/20/17.
 */

public class CaloriesActivity extends AppCompatActivity {

    private static final String TAG = "kaluli";
    protected CaloriesView caloriesView;
    protected JSONArray info;
    protected Calories col = new Calories();
    protected EditText editText_caloriesMon;
    protected EditText editText_caloriesTue;
    protected EditText editText_caloriesWed;
    protected EditText editText_caloriesThur;
    protected EditText editText_caloriesFri;
    protected EditText editText_caloriesSat;
    protected EditText editText_caloriesSun;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        caloriesView = new CaloriesView(this);
        final TextView myView = (TextView) findViewById(R.id.textView);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_nutrition = database.getReference("nutrition");

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
                    getEditTexts();
                    editText_caloriesMon.setText((String) info.get(0));
                    editText_caloriesTue.setText((String) info.get(1));
                    editText_caloriesWed.setText((String) info.get(2));
                    editText_caloriesThur.setText((String) info.get(3));
                    editText_caloriesFri.setText((String) info.get(4));
                    editText_caloriesSat.setText((String) info.get(5));
                    editText_caloriesSun.setText((String) info.get(6));


                } catch (JSONException e) {
                    Toast.makeText(CaloriesActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

    }

    protected void getEditTexts(){
        editText_caloriesMon = (EditText) findViewById(R.id.caloriesMonday);
        editText_caloriesTue = (EditText) findViewById(R.id.caloriesTuesday);
        editText_caloriesWed = (EditText) findViewById(R.id.caloriesWednesday);
        editText_caloriesThur = (EditText) findViewById(R.id.caloriesThursday);
        editText_caloriesFri = (EditText) findViewById(R.id.caloriesFriday);
        editText_caloriesSat = (EditText) findViewById(R.id.caloriesSaturday);
        editText_caloriesSun = (EditText) findViewById(R.id.caloriesSunday);

    }

    protected void setCalories(){
        getEditTexts();
        col.setCaloriesMon(editText_caloriesMon.getText().toString());
        col.setCaloriesTue(editText_caloriesTue.getText().toString());
        col.setCaloriesWed(editText_caloriesWed.getText().toString());
        col.setCaloriesThur(editText_caloriesThur.getText().toString());
        col.setCaloriesFri(editText_caloriesFri.getText().toString());
        col.setCaloriesSat(editText_caloriesSat.getText().toString());
        col.setCaloriesSun(editText_caloriesSun.getText().toString());
    }

    public void onClick(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_calories = FirebaseDatabase.getInstance().getReference("calories");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_nutrition = database.getReference("nutrition");

        if (view.getId() == R.id.Save) {
            setCalories();
            if (col.getCaloriesMon().equals("")) col.setCaloriesMon("0");
            if (col.getCaloriesTue().equals("")) col.setCaloriesTue("0");
            if (col.getCaloriesWed().equals("")) col.setCaloriesWed("0");
            if (col.getCaloriesThur().equals("")) col.setCaloriesThur("0");
            if (col.getCaloriesFri().equals("")) col.setCaloriesFri("0");
            if (col.getCaloriesSat().equals("")) col.setCaloriesSat("0");
            if (col.getCaloriesSun().equals("")) col.setCaloriesSun("0");

            try {
                info.put(0, col.getCaloriesMon());
                info.put(1, col.getCaloriesTue());
                info.put(2, col.getCaloriesWed());
                info.put(3, col.getCaloriesThur());
                info.put(4, col.getCaloriesFri());
                info.put(5, col.getCaloriesSat());
                info.put(6, col.getCaloriesSun());

                myref_calories.child(user.getUid()).setValue(info.toString());
                Toast.makeText(caloriesView.getContext(),
                        "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                } catch (JSONException e) {
                    Toast.makeText(CaloriesActivity.this, "Error2", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view.getId() == R.id.displayLineChart){
            Intent i = new Intent(this, LineChartActivity.class);
            startActivity(i);
        }
    }
}
