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

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Daniel on 3/24/2017.
 */
public class VitalsActivity extends AppCompatActivity {

    protected VitalsView vitalsView;

    protected String heartRate;
    protected String bloodPressureS;
    protected String bloodPressureD;
    protected String bodyTemperature;
    protected String respiratoryRate;

    protected String date;
    protected String monthyear;
    protected JSONArray dateInfo;

    protected EditText editText_heartRate;
    protected EditText editText_bloodPressureS;
    protected EditText editText_bloodPressureD;
    protected EditText editText_bodyTemperature;
    protected EditText editText_respiratoryRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);
        vitalsView = new VitalsView(this);
        date = getIntent().getExtras().getString("DATE");
        monthyear = getIntent().getExtras().getString("MONTHYEAR");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref_date = database.getReference(date + "v").child(user.getUid());;



        myref_date.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                try {
                    if (s == null) {
                        String[] arr = {"", "", "", "", ""};
                        dateInfo = new JSONArray(arr);
                    } else {
                        dateInfo = new JSONArray(s);
                    }
                    editText_heartRate = (EditText) findViewById(R.id.hr);
                    editText_bloodPressureS = (EditText) findViewById(R.id.bps);
                    editText_bloodPressureD = (EditText) findViewById(R.id.bpd);
                    editText_bodyTemperature = (EditText) findViewById(R.id.bt);
                    editText_respiratoryRate = (EditText) findViewById(R.id.rr);

                    editText_heartRate.setText((String) dateInfo.get(0));
                    editText_bloodPressureS.setText((String) dateInfo.get(1));
                    editText_bloodPressureD.setText((String) dateInfo.get(2));
                    editText_bodyTemperature.setText((String) dateInfo.get(3));
                    editText_respiratoryRate.setText((String) dateInfo.get(4));

                } catch (JSONException e) {
                    Toast.makeText(VitalsActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    public void onClick(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref_date = database.getReference(date + "v");

        editText_heartRate = (EditText) findViewById(R.id.hr);
        editText_bloodPressureS = (EditText) findViewById(R.id.bps);
        editText_bloodPressureD = (EditText) findViewById(R.id.bpd);
        editText_bodyTemperature = (EditText) findViewById(R.id.bt);
        editText_respiratoryRate = (EditText) findViewById(R.id.rr);

        heartRate = editText_heartRate.getText().toString();
        bloodPressureS = editText_bloodPressureS.getText().toString();
        bloodPressureD = editText_bloodPressureD.getText().toString();
        bodyTemperature = editText_bodyTemperature.getText().toString();
        respiratoryRate = editText_respiratoryRate.getText().toString();

        try {
            dateInfo.put(0, heartRate);
            dateInfo.put(1, bloodPressureS);
            dateInfo.put(2, bloodPressureD);
            dateInfo.put(3, bodyTemperature);
            dateInfo.put(4, respiratoryRate);
            myref_date.child(user.getUid()).setValue(dateInfo.toString());
            Toast.makeText(vitalsView.getContext(),
                    "Saved", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, CalendarActivity.class);
            i.putExtra("Type","vitals");
            startActivity(i);

        } catch (JSONException e) {
            Toast.makeText(VitalsActivity.this, "Error2", Toast.LENGTH_SHORT).show();
        }
    }
}