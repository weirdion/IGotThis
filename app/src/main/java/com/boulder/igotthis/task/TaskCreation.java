package com.boulder.igotthis.task;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Class Description
 *
 * @author asadana
 * @since 04/17/2017
 */

public class TaskCreation extends LinearLayout {

    private final Context context;

    public TaskCreation(Context context) {
        this(context, null);
    }

    public TaskCreation(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskCreation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TaskCreation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        //context.
    }
}
