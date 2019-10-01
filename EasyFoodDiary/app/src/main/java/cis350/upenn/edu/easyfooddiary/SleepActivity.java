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

public class SleepActivity extends AppCompatActivity {

    private static final String TAG = "sleeping";
    protected SleepView sleepView;
    protected JSONArray info;
    protected SleepHours slp = new SleepHours();
    protected EditText editText_sleepMon;
    protected EditText editText_sleepTue;
    protected EditText editText_sleepWed;
    protected EditText editText_sleepThur;
    protected EditText editText_sleepFri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        sleepView = new SleepView(this);
        final TextView myView = (TextView) findViewById(R.id.textView);
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_nutrition = database.getReference("nutrition");

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
                    getEditTexts();
                    editText_sleepMon.setText((String) info.get(0));
                    editText_sleepTue.setText((String) info.get(1));
                    editText_sleepWed.setText((String) info.get(2));
                    editText_sleepThur.setText((String) info.get(3));
                    editText_sleepFri.setText((String) info.get(4));



                } catch (JSONException e) {
                    Toast.makeText(SleepActivity.this, "Error1", Toast.LENGTH_SHORT).show();
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
        editText_sleepMon = (EditText) findViewById(R.id.sleepMonday);
        editText_sleepTue = (EditText) findViewById(R.id.sleepTuesday);
        editText_sleepWed = (EditText) findViewById(R.id.sleepWednesday);
        editText_sleepThur = (EditText) findViewById(R.id.sleepThursday);
        editText_sleepFri = (EditText) findViewById(R.id.sleepFriday);


    }

    protected void setSleepingTime(){
        getEditTexts();
        slp.setSleepMon(editText_sleepMon.getText().toString());
        slp.setSleepTue(editText_sleepTue.getText().toString());
        slp.setSleepWed(editText_sleepWed.getText().toString());
        slp.setSleepThur(editText_sleepThur.getText().toString());
        slp.setSleepFri(editText_sleepFri.getText().toString());
    }

    public void onClick3(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myref_sleepingTime = FirebaseDatabase.getInstance().getReference("sleep");

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myref_nutrition = database.getReference("nutrition");

        if (view.getId() == R.id.Save) {
            setSleepingTime();
            if (slp.getSleepMon().equals("")) slp.setSleepMon("0");
            if (slp.getSleepTue().equals("")) slp.setSleepTue("0");
            if (slp.getsleepWed().equals("")) slp.setSleepWed("0");
            if (slp.getsleepThur().equals("")) slp.setSleepThur("0");
            if (slp.getsleepFri().equals("")) slp.setSleepFri("0");


            try {
                info.put(0, slp.getSleepMon());
                info.put(1, slp.getSleepTue());
                info.put(2, slp.getsleepWed());
                info.put(3, slp.getsleepThur());
                info.put(4, slp.getsleepFri());


                myref_sleepingTime.child(user.getUid()).setValue(info.toString());
                Toast.makeText(sleepView.getContext(),
                        "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } catch (JSONException e) {
                Toast.makeText(SleepActivity.this, "Error2", Toast.LENGTH_SHORT).show();
            }

        }
        else if (view.getId() == R.id.displayLineChart){
            Intent i = new Intent(this, LineChartActivitySleep.class);
            startActivity(i);
        }
    }
}
