package com.boulder.igotthis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.boulder.igotthis.task.TaskCreation;

/**
 * MainTaskActivity
 * This class is used to handle the tasking activity with a bottom navigation layout.
 *
 * @author asadana
 */
public class MainTaskActivity extends AppCompatActivity {

    private FrameLayout menuContentLayout;
    private BottomNavigationView navigation;

    private TaskCreation taskCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);

        menuContentLayout = (FrameLayout) findViewById(R.id.menu_content_layout);
        navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);

        init();
    }

    private void init() {
        taskCreation = new TaskCreation(getApplicationContext());
        addListeners();
    }

    private void addListeners() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                menuContentLayout.removeAllViews();
                switch (item.getItemId()) {
                    case R.id.task_creation_menu:
                        menuContentLayout.addView(taskCreation);
                        return true;
                    case R.id.pre_deploy_menu:
                        Toast.makeText(getApplicationContext(), "Pre-Deploy selected!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.active_tasks_menu:
                        Toast.makeText(getApplicationContext(), "Active-Tasks selected!", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }

        });
    }
}
