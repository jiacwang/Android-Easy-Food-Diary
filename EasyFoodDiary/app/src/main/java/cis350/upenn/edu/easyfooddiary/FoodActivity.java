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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by haile on 2/24/2017.
 */

public class FoodActivity extends AppCompatActivity {

    protected FoodView foodView;
    protected String date;
    protected String monthyear;
    protected JSONArray dateInfo;
    //protected String weight, breakfast, lunch, dinner, snack1, snack2, snack3;
    //protected String breakfastCalories, lunchCalories, dinnerCalories, snack1Calories, snack2Calories, snack3Calories;
    protected String[] typeStr = new String[7];
    protected String[] caloriesStr = new String[6];

    /*protected EditText editText_weight;
    protected EditText editText_breakfast;
    protected EditText editText_lunch;
    protected EditText editText_dinner;
    protected EditText editText_snack1;
    protected EditText editText_snack2;
    protected EditText editText_snack3;*/
    protected EditText[] etType = new EditText[7];

    /*protected EditText editText_breakfastCalories;
    protected EditText editText_lunchCalories;
    protected EditText editText_dinnerCalories;
    protected EditText editText_snack1Calories;
    protected EditText editText_snack2Calories;
    protected EditText editText_snack3Calories;*/
    protected EditText[] etCalories = new EditText[6];

    private void getTypeAndCalorieET(){
        etType[0] = (EditText) findViewById(R.id.weight);
        etType[1] = (EditText) findViewById(R.id.breakfast);
        etType[2] = (EditText) findViewById(R.id.lunch);
        etType[3] = (EditText) findViewById(R.id.dinner);
        etType[4] = (EditText) findViewById(R.id.snack1);
        etType[5] = (EditText) findViewById(R.id.snack2);
        etType[6] = (EditText) findViewById(R.id.snack3);
        etCalories[0] = (EditText) findViewById(R.id.breakfastCalories);
        etCalories[1] = (EditText) findViewById(R.id.lunchCalories);
        etCalories[2] = (EditText) findViewById(R.id.dinnerCalories);
        etCalories[3] = (EditText) findViewById(R.id.snack1Calories);
        etCalories[4] = (EditText) findViewById(R.id.snack2Calories);
        etCalories[5] = (EditText) findViewById(R.id.snack3Calories);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        foodView = new FoodView(this);
        date = getIntent().getExtras().getString("DATE");
        monthyear = getIntent().getExtras().getString("MONTHYEAR");
        TextView myView = (TextView) findViewById(R.id.textView);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_date = database.getReference(date);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_date = FirebaseDatabase.getInstance().getReference(date).child(user.getUid());


        myref_date.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String s = dataSnapshot.getValue(String.class);
                try {
                    if (s == null) {
                        String[] arr = {"", "", "", "", "", "", "", "", "", "", "", "", ""};
                        dateInfo = new JSONArray(arr);
                    } else {
                        dateInfo = new JSONArray(s);
                    }
                    getTypeAndCalorieET();

                    etType[0].setText((String) dateInfo.get(0));
                    int ind = 1;
                    for(int i = 1;i < 7;i++){
                        etType[i].setText((String) dateInfo.get(ind));
                        ind = ind + 2;
                    }

                    ind = 2;
                    for(int i = 0;i < 6;i++){
                        etCalories[i].setText((String) dateInfo.get(ind));
                        ind = ind + 2;
                    }

                } catch (JSONException e) {
                    Toast.makeText(FoodActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    public void onClick(View view) {

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_date = database.getReference(date);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_date = FirebaseDatabase.getInstance().getReference(date);



        getTypeAndCalorieET();
        for(int i = 0;i < 7;i++){
            typeStr[i] = etType[i].getText().toString();
        }
        for(int i = 0;i < 6;i++){
            caloriesStr[i] = etCalories[i].getText().toString();
        }

        try {
            dateInfo.put(0, typeStr[0]);
            int ind = 1;
            for(int i = 1;i < 7;i++){
                dateInfo.put(ind, typeStr[i]);
                ind = ind + 2;
            }
            ind = 2;
            for(int i = 0;i < 6;i++){
                dateInfo.put(ind, caloriesStr[i]);
                ind = ind + 2;
            }

            myref_date.child(user.getUid()).setValue(dateInfo.toString());

            //myref_date.setValue(dateInfo.toString());
            Toast.makeText(foodView.getContext(),
                    "Saved", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, CalendarActivity.class);
            i.putExtra("Type","calendar");
            startActivity(i);
        } catch (JSONException e) {
            Toast.makeText(FoodActivity.this, "Error2", Toast.LENGTH_SHORT).show();
        }
    }

}