package com.boulder.igotthis.task;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.boulder.igotthis.R;
import com.boulder.igotthis.task.util.EventType;

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
        final List<String> eventDropDownList = new ArrayList<String>(EventType.values().length);
        // TODO : make this dependent on the permissions given, otherwise disable it.
        for (EventType eventType : EventType.values()) {
            eventDropDownList.add(context.getString(EventType.getStringResource(eventType)));
        }
        ArrayAdapter<String> eventDropDownAdapter = new ArrayAdapter<String>(context,
                R.layout.drop_down_item, eventDropDownList);
        eventDropDownAdapter.setDropDownViewResource(R.layout.drop_down_item);
        eventDropDownSpinner.setAdapter(eventDropDownAdapter);
        eventDropDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (EventType.valueAt(position)) {
                    case BLUETOOTH_CONNECTED:
                        break;
                    case BLUETOOTH_DISCONNECTED:
                        break;
                    case CHARGING_CONNECTED:
                        break;
                    case CHARGING_DISCONNECTED:
                        break;
                    case WIFI_CONNECTED:
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager.isWifiEnabled()) {
                            StringBuilder stringBuilder = new StringBuilder("");
                            for (WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                                stringBuilder.append(wifiConfiguration.SSID);
                                stringBuilder.append("\n");
                            }
                            Toast.makeText(context, "WIFI Enabled\n" + stringBuilder.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case WIFI_DISCONNECTED:
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
