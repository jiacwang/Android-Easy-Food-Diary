package cis350.upenn.edu.easyfooddiary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by vamsee on 4/27/17.
 */

public class VitalsView extends TextView {

    protected String heartRate;
    protected String bloodPressureS;
    protected String bloodPressureD;
    protected String bodyTemperature;
    protected String respiratoryRate;

    public VitalsView(Context c) {
        super(c);
    }

    public VitalsView(Context c, AttributeSet a) {
        super(c, a);
    }
}
