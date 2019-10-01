package cis350.upenn.edu.easyfooddiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by haile on 3/24/2017.
 */

public class MotivationView extends TextView {

    protected String quote;

    public MotivationView(Context c) {
        super(c);
    }

    public MotivationView(Context c, AttributeSet a) {
        super(c, a);
    }
}
