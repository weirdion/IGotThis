/*
 * Copyright (C) 2017 Ankit Sadana
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boulder.igotthis

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.support.annotation.Nullable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import com.boulder.igotthis.task.util.ActionType
import com.boulder.igotthis.task.util.EventType
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Class Description
 *
 * @author asadana
 * @since 12/24/17
 */

class TaskCreationView {
    private var TAG: String? = this.javaClass.name
    private var rootView: View? = null
    private var context: Context? = null

    // Event Elements
    private var eventDropDownSpinner:Spinner? = null
    private var itemActionLayout: LinearLayout? = null
    // Action Elements
    private var actionDropDownSpinner: Spinner? = null
    private var addActionButton: ImageButton? = null
    private var mAddEventActionButton:Button? = null
    private var mResetEventActionButton: Button? = null

    fun View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        rootView = inflater.inflate(R.layout.task_creation_layout, container, false);
        context = rootView.getContext();
        eventDropDownSpinner = (Spinner) rootView.findViewById(R.id.event_drop_down_list);
        itemActionLayout = (LinearLayout) rootView.findViewById(R.id.item_action_select_layout);
        itemActionLayout.addView(View.inflate(context, R.layout.task_creation_item_action, null));
        actionDropDownSpinner = (Spinner) itemActionLayout.findViewById(R.id.item_drop_down_list);
        addActionButton = (ImageButton) itemActionLayout.findViewById(R.id.item_action_add_action_button);
        mAddEventActionButton = (Button) rootView.findViewById(R.id.button_add);
        mResetEventActionButton = (Button) rootView.findViewById(R.id.button_reset);
        init();
        return rootView;
    }

    private void init() {
        Log.d(TAG, "init");
        addActionButton.setEnabled(false);   // TODO: Keep disabled till multiple actions can be linked to one event.
        mAddEventActionButton.setEnabled(false);
        mResetEventActionButton.setEnabled(false);
        addButtonListeners();
        populateEventSpinner();
    }

    private void addButtonListeners() {
        mAddEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
                        addActionView(EventType.valueAt(position));
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

    private void addActionView(EventType eventType) {
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
