package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by vamsee on 3/31/17.
 */

public class FitnessActivity extends AppCompatActivity{

    protected String workoutsPerWeek;
    protected String minutesPerWorkout;
    protected FitnessView fitnessView;
    protected EditText editText_workoutsPerWeek;
    protected EditText editText_minutesPerWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);
        fitnessView = new FitnessView(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_workoutsPerWeek = FirebaseDatabase.getInstance().getReference("Workouts_Per_Week").child(user.getUid());
        DatabaseReference myref_minutesPerWorkout = FirebaseDatabase.getInstance().getReference("Minutes_Per_Workout").child(user.getUid());


        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_workoutsPerWeek = database.getReference("Workouts_Per_Week");
        //DatabaseReference myref_minutesPerWorkout = database.getReference("Minutes_Per_Workout");

        myref_workoutsPerWeek.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                editText_workoutsPerWeek = (EditText) findViewById(R.id.workoutsPerWeek);
                editText_workoutsPerWeek.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", databaseError.toException());
            }
        });

        myref_minutesPerWorkout.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                editText_minutesPerWorkout = (EditText) findViewById(R.id.minutesPerWorkout);
                editText_minutesPerWorkout.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", databaseError.toException());
            }
        });
    }

    protected void onClick(View view) {

        /*if (view.getId() == R.id.main) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else*/

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference myref_workoutsPerWeek = FirebaseDatabase.getInstance().getReference("Workouts_Per_Week");
            DatabaseReference myref_minutesPerWorkout = FirebaseDatabase.getInstance().getReference("Minutes_Per_Workout");


            // FirebaseDatabase database = FirebaseDatabase.getInstance();
           // DatabaseReference myref_workoutsPerWeek = database.getReference("Workouts_Per_Week");
            //DatabaseReference myref_minutesPerWorkout = database.getReference("Minutes_Per_Workout");

            editText_workoutsPerWeek = (EditText) findViewById(R.id.workoutsPerWeek);
            workoutsPerWeek = editText_workoutsPerWeek.getText().toString();

            editText_minutesPerWorkout = (EditText) findViewById(R.id.minutesPerWorkout);
            minutesPerWorkout = editText_minutesPerWorkout.getText().toString();

            try {
                //myref_workoutsPerWeek.setValue(workoutsPerWeek);
                myref_workoutsPerWeek.child(user.getUid()).setValue(workoutsPerWeek);

                //myref_minutesPerWorkout.setValue(minutesPerWorkout);
                myref_minutesPerWorkout.child(user.getUid()).setValue(minutesPerWorkout);


                Toast.makeText(fitnessView.getContext(),
                        "Your fitness goals have been saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(fitnessView.getContext(),
                        "Error", Toast.LENGTH_SHORT).show();
            }
       // }
    }
}
