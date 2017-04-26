package com.boulder.igotthis.task;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.boulder.igotthis.R;
import com.boulder.igotthis.task.util.ActionType;
import com.boulder.igotthis.task.util.EventType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Class Description
 *
 * @author asadana
 * @since 04/17/2017
 */

public class TaskCreation extends LinearLayout {

    private final Context context;

    // Event Elements
    private Spinner eventDropDownSpinner;
    private LinearLayout itemActionLayout;
    // Action Elements
    private Spinner actionDropDownSpinner;
    private ImageButton addActionButton;

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
        populateEventSpinner();
    }

    private void populateEventSpinner() {
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
                    case BLUETOOTH_DISCONNECTED:
                    case WIFI_CONNECTED:
                    case WIFI_DISCONNECTED:
                        addActionView();
                        break;
                    // TODO temporarily do nothing for charging urlConnectionected/disurlConnectionected.
                    case CHARGING_CONNECTED:
                    case CHARGING_DISCONNECTED:
                    default:
                        itemActionLayout.removeAllViews();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addActionView() {
        itemActionLayout.addView(View.inflate(context, R.layout.task_creation_item_action, null));
        actionDropDownSpinner = (Spinner) itemActionLayout.findViewById(R.id.item_drop_down_list);
        addActionButton = (ImageButton) itemActionLayout.findViewById(R.id.item_action_add_action_button);
        addActionButton.setEnabled(false);  // TODO: keep this disabled till functionality is implemented

        final List<String> actionDropDownList = new ArrayList<String>(ActionType.values().length);
        // TODO : make this dependent on the permissions given, otherwise disable it.
        // TODO : make this dynamic so there's no duplicates from previous choices?
        for (ActionType actionType : ActionType.values()) {
            actionDropDownList.add(context.getString(ActionType.getStringResource(actionType)));
        }
        ArrayAdapter<String> actionDropDownAdapter = new ArrayAdapter<String>(context,
                R.layout.drop_down_item, actionDropDownList);
        actionDropDownAdapter.setDropDownViewResource(R.layout.drop_down_item);

        actionDropDownSpinner.setAdapter(actionDropDownAdapter);
        actionDropDownSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (ActionType.valueAt(position)) {
                    case TURN_BLUETOOTH_ON: {
                        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!bluetoothAdapter.isEnabled())
                            bluetoothAdapter.enable();
                    }
                    break;
                    case TURN_BLUETOOTH_OFF: {
                        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (bluetoothAdapter.isEnabled())
                            bluetoothAdapter.disable();
                    }
                    break;
                    case TURN_WIFI_ON: {
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if (!wifiManager.isWifiEnabled())
                            wifiManager.setWifiEnabled(true);
                    }
                    break;
                    case TURN_WIFI_OFF: {
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager.isWifiEnabled())
                            wifiManager.setWifiEnabled(false);
                    }
                    break;
                    case OPEN_APP:
                        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.waze");
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(launchIntent);
                        break;
                    case SEND_MESSAGE_USING_ALLO:
                        break;
                    case PERFORM_CUSTOM_ACTION:
                        new AsyncTask<String, String, String>() {

                            @Override
                            protected String doInBackground(String... params) {

                                String resultToDisplay = "";
                                HttpURLConnection httpURLConnection = null;

                                try {

                                    URL url = new URL("http:///www.google.com");
                                    Log.e(TAG, "doInBackground: " + String.format(urlString, email, token, command));

                                    httpURLConnection = (HttpURLConnection) url.openConnection();
                                    httpURLConnection.setReadTimeout(15000);
                                    httpURLConnection.setConnectTimeout(15000);
                                    httpURLConnection.setRequestMethod("GET");
                                    httpURLConnection.setDoInput(true);
                                    httpURLConnection.setDoOutput(true);
/*

                                    OutputStream os = httpURLConnection.getOutputStream();
                                    BufferedWriter writer = new BufferedWriter(
                                            new OutputStreamWriter(os, "UTF-8"));
                                    writer.write(getPostDataString(postDataParams));

                                    writer.flush();
                                    writer.close();
                                    os.close();
*/

                                    int responseCode = httpURLConnection.getResponseCode();

                                    //if (responseCode == HttpURLConnection.HTTP_OK) {

                                        BufferedReader bufferedReader = new BufferedReader(
                                                new InputStreamReader(
                                                        httpURLConnection.getInputStream()));
                                        StringBuilder stringBuilder = new StringBuilder("");
                                        String line = "";

                                        while ((line = bufferedReader.readLine()) != null) {

                                            stringBuilder.append(line);
                                            break;
                                        }

                                        bufferedReader.close();
                                        resultToDisplay = stringBuilder.toString();

                                    /*} else {
                                        resultToDisplay = "false : " + responseCode;
                                    }*/

                                } catch (MalformedURLException error) {
                                    //Handles an incorrectly entered URL
                                    Log.e(TAG, "doInBackground: ", error);
                                } catch (SocketTimeoutException error) {
                                    //Handles URL access timeout.
                                    Log.e(TAG, "doInBackground: ", error);
                                } catch (IOException error) {
                                    //Handles input and output error
                                    Log.e(TAG, "doInBackground: ", error);
                                } finally {
                                    if (httpURLConnection != null) // Make sure the connection is not null.
                                        httpURLConnection.disconnect();
                                }

                                return resultToDisplay;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                            }
                        }.execute();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}