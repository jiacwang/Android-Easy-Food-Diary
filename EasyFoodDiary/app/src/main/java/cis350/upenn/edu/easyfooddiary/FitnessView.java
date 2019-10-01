package cis350.upenn.edu.easyfooddiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by vamsee on 3/31/17.
 */

public class FitnessView extends TextView {

    protected String workoutsPerWeek;
    protected String minutesPerWorkout;

    public FitnessView (Context c) { super(c); }

    public FitnessView(Context c, AttributeSet a) {
        super(c, a);
    }
}
