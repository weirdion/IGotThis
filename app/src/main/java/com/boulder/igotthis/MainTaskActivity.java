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

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * MainTaskActivity
 * This class is used to handle the tasking activity with a bottom navigation layout.
 *
 * @author asadana
 */
public class MainTaskActivity extends AppCompatActivity {

    private FrameLayout mainTaskFragmentLayout;
    private BottomNavigationView navigation;

    private TaskCreationFragment taskCreationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);
        // TODO: remove this flag after initial testing
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mainTaskFragmentLayout = (FrameLayout) findViewById(R.id.main_task_fragment);
        navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        if (savedInstanceState == null) {
            taskCreationFragment = new TaskCreationFragment();
            taskCreationFragment.setArguments(getIntent().getExtras());
        }
        init();
    }

    private void init() {
        getFragmentManager().beginTransaction().add(R.id.main_task_fragment, taskCreationFragment).commit();
        addListeners();
    }

    private void addListeners() {
        final FragmentManager fragmentManager = getFragmentManager();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.task_creation_menu:
                    default:
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_task_fragment, taskCreationFragment).commit();
                        return true;
                    case R.id.pre_deploy_menu:
                        Toast.makeText(getApplicationContext(), "Pre-Deploy selected!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.active_tasks_menu:
                        Toast.makeText(getApplicationContext(), "Active-Tasks selected!", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
    }
}
