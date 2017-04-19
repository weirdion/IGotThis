package com.boulder.igotthis.task.util;

import android.content.Context;
import android.widget.LinearLayout;

import com.boulder.igotthis.R;

/**
 * Class Description
 *
 * @author ankit
 * @since 04/18/2017
 */

public class TaskCreationItemAction extends LinearLayout {
    private final Context context;

    public TaskCreationItemAction(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        addView(inflate(context, R.layout.task_creation_item_action, null));
    }
}
