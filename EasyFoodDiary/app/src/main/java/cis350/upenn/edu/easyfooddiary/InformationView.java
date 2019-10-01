package cis350.upenn.edu.easyfooddiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by akshay on 2/24/17.
 */

public class InformationView extends TextView {

    protected String namer;
    protected String birthdate;
    protected String goal;

    public InformationView(Context c) {
        super(c);
    }

    public InformationView(Context c, AttributeSet a) {
        super(c, a);
    }


}
