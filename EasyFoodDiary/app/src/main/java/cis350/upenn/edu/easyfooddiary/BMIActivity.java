package cis350.upenn.edu.easyfooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by haile on 4/15/2017.
 */

public class BMIActivity  extends AppCompatActivity {

    protected EditText editText_weight, editText_height;
    protected TextView bmi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        bmi = (TextView) findViewById(R.id.bmi);
        editText_weight = (EditText) findViewById(R.id.weight);
        editText_height = (EditText) findViewById(R.id.height);
    }

    public void onClick(View view) {
        //have to differentiate amongst buttons
        /*if (view.getId() == R.id.main) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else*/ if (view.getId() == R.id.calculate) {;
            String weight = editText_weight.getText().toString();
            String height = editText_height.getText().toString();
            if (height.equals("") || weight.equals("")) {
                Toast.makeText(BMIActivity.this, "Enter height/weight", Toast.LENGTH_SHORT).show();
            }
            else {
                int h = Integer.parseInt(height);
                int w = Integer.parseInt(weight);
                bmi.setText("BMI: " + 703.0 * w / (h * h));
            }
        }
    }
}