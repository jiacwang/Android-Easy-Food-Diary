package cis350.upenn.edu.easyfooddiary;

/**
 * Created by Daniel on 4/7/2017.
 */

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
 * Created by haile on 4/7/2017.
 */

public class MacrosActivity extends AppCompatActivity {

    protected EditText editText_calories;
    protected TextView title, carbsText, proteinText, fatText;
    protected JSONArray info;
    protected String cals, carbs, protein, fat;
    protected Double gramsPerCalorie = 7.71617917647;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaymacros);
        title = (TextView) findViewById(R.id.title);
        title.setText("Amount of Macros Needed to Achieve Goals!");
        carbsText = (TextView) findViewById(R.id.carbs);
        proteinText = (TextView) findViewById(R.id.protein);
        fatText = (TextView) findViewById(R.id.fat);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_nutrition = database.getReference("nutrition");


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
                    /*editText_calories = (EditText) findViewById(R.id.calories);
                    carbsText = (EditText) findViewById(R.id.carbs);
                    proteinText = (EditText) findViewById(R.id.protein);
                    fatText = (EditText) findViewById(R.id.fat);*/

                    cals = ((String) info.get(0));

                    carbs = ((String) info.get(1));
                    protein = ((String) info.get(2));

                    fat = ((String) info.get(3));

                    int calories = Integer.parseInt(cals);
                    Double carbPercent = (Integer.parseInt(carbs) / 100.0);
                    Double proteinPercent = (Integer.parseInt(protein) / 100.0);
                    Double fatPercent = (Integer.parseInt(fat) / 100.0);

                    carbsText.setText("Carbohydrates Needed: " + (int) (carbPercent * calories * gramsPerCalorie) + " grams");
                    proteinText.setText("Protein Needed: " + (int) (proteinPercent * calories * gramsPerCalorie) + " grams");
                    fatText.setText("Fat Needed: " + (int) (fatPercent * calories * gramsPerCalorie) + " grams");

                } catch (JSONException e) {
                    Toast.makeText(MacrosActivity.this, "Error1", Toast.LENGTH_SHORT).show();
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
        //have to differentiate amongst buttons
            //have to differentiate amongst buttons
           /* if (view.getId() == R.id.main) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
*/


    }
}
