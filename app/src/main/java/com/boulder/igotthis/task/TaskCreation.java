package com.boulder.igotthis.task;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.annotation.Nullable;
import android.widget.SpinnerAdapter;

import com.boulder.igotthis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description
 *
 * @author asadana
 * @since 04/17/2017
 */

public class TaskCreation extends LinearLayout {

    private final Context context;

    private Spinner eventDropDownSpinner;
    private LinearLayout itemActionLayout;

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
        addView(inflate(context, R.layout.task_creation_layout, null));
        eventDropDownSpinner = (Spinner) findViewById(R.id.event_drop_down_list);
        itemActionLayout = (LinearLayout) findViewById(R.id.item_action_select_layout);
        populateSpinners();
    }

    private void populateSpinners() {
        List<String> eventDropDownList = new ArrayList<String>();
        eventDropDownList.add("On Wifi Connected");
        eventDropDownList.add("On Wifi Disconnected");
        eventDropDownList.add("On Bluetooth Connected");
        eventDropDownList.add("On Bluetooth Disconnected");
        ArrayAdapter<String> eventDropDownAdapter = new ArrayAdapter<String>(context,
                R.layout.support_simple_spinner_dropdown_item, eventDropDownList);
        eventDropDownAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        eventDropDownSpinner.setAdapter(eventDropDownAdapter);
        eventDropDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemActionLayout.addView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
