package cis350.upenn.edu.easyfooddiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by haile on 2/24/2017.
 */

public class FoodView extends TextView {

    protected String weight;
    protected String breakfast;
    protected String lunch;
    protected String dinner;
    protected String snack;

    protected String breakfastCalories;
    protected String lunchCalories;
    protected String dinnerCalories;
    protected String snackCalories;

    public FoodView(Context c) {
        super(c);
    }

    public FoodView(Context c, AttributeSet a) {
        super(c, a);
    }






}
