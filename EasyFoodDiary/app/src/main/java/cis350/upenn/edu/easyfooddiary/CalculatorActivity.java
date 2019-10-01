package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by haile on 4/7/2017.
 */

public class CalculatorActivity extends AppCompatActivity {

    protected EditText editText_calories;
    protected TextView running, pushups, biking, walking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        running = (TextView) findViewById(R.id.running);
        pushups = (TextView) findViewById(R.id.pushups);
        biking = (TextView) findViewById(R.id.biking);
        walking = (TextView) findViewById(R.id.walking);
        editText_calories = (EditText) findViewById(R.id.calories);
    }

    public void onClick(View view) {
        //have to differentiate amongst buttons
        /*if (view.getId() == R.id.main) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else*/ if (view.getId() == R.id.calculate) {;
            String calories = editText_calories.getText().toString();
            if (calories.equals("")) {
                Toast.makeText(CalculatorActivity.this, "Enter calories to burn", Toast.LENGTH_SHORT).show();
            }
            else {
                int cal = Integer.parseInt(calories);
                pushups.setText("Pushups needed: " + cal * 5);
                running.setText("Miles to run: " + cal / 100.0);
                biking.setText("Miles to bike: " + cal / 30.0);
                walking.setText("Miles to walk: " + cal / 50.0);
            }
        }
    }
}
