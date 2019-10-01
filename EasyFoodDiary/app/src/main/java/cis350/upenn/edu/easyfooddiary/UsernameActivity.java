package cis350.upenn.edu.easyfooddiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class UsernameActivity extends AppCompatActivity {

    protected EditText usernameEditText;
    protected Button buttonSet;
    protected TextView usernameTextView;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        usernameEditText = (EditText) findViewById(R.id.usernameText);
        buttonSet = (Button) findViewById(R.id.setButton);

        usernameTextView = (TextView) findViewById(R.id.textViewUsername);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference profile = FirebaseDatabase.getInstance().getReference("profile").child(user.getUid());

        profile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String name = dataSnapshot.getValue(String.class);
                usernameTextView.setText("Username: "+name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });




    //usernameTextView.setText("Username: "+user.getEmail());

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = usernameEditText.getText().toString();


                DatabaseReference myref_profile = FirebaseDatabase.getInstance().getReference("profile");

                myref_profile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if(uname.isEmpty()){
                            Toast.makeText(UsernameActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                        }else if (snapshot.hasChild(uname)) {
                            Toast.makeText(UsernameActivity.this, "Username already exists. Please change another one!", Toast.LENGTH_SHORT).show();
                        }else{
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference myref_friend = FirebaseDatabase.getInstance().getReference("profile");
                            myref_friend.child(uname).setValue(user.getUid());
                            myref_friend.child(user.getUid()).setValue(uname);
                            Toast.makeText(UsernameActivity.this, "Set username successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("tag", "Failed to read value.", databaseError.toException());

                    }
                });


            }
        });
    }
}
