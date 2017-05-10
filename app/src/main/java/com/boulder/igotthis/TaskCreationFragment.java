/*
 * Copyright (C) 2017 Ankit Sadana
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.boulder.igotthis;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.boulder.igotthis.task.util.ActionType;
import com.boulder.igotthis.task.util.EventType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * @author ankit
 * @since 05/02/2017
 */

public class TaskCreationFragment extends Fragment {

    private View rootView;
    private Context context;

    // Event Elements
    private Spinner eventDropDownSpinner;
    private LinearLayout itemActionLayout;
    // Action Elements
    private Spinner actionDropDownSpinner;
    private ImageButton addActionButton;

    public TaskCreationFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.task_creation_layout, container, false);
        context = rootView.getContext();
        eventDropDownSpinner = (Spinner) rootView.findViewById(R.id.event_drop_down_list);
        itemActionLayout = (LinearLayout) rootView.findViewById(R.id.item_action_select_layout);
        populateEventSpinner();
        return rootView;
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
                                String urlString = "http://www.google.com";

                                String resultToDisplay = "";
                                HttpURLConnection httpURLConnection = null;

                                try {

                                    URL url = new URL(urlString);

                                    httpURLConnection = (HttpURLConnection) url.openConnection();
                                    httpURLConnection.setReadTimeout(15000);
                                    httpURLConnection.setConnectTimeout(15000);
                                    httpURLConnection.setRequestMethod("GET");
                                    httpURLConnection.setDoInput(true);
                                    //httpURLConnection.setDoOutput(true);
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
                                    Log.e(TAG, "doInBackground: " + responseCode);

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
