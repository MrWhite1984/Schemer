package com.example.schemer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {

    static SharedPreferences app_settings;

    private SwitchCompat activity_settings_coup_behavior_switch;

    private ImageButton settings_activity_top_bar_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        activity_settings_coup_behavior_switch = findViewById(R.id.activity_settings_coup_behavior_switch);
        activity_settings_coup_behavior_switch.setChecked(app_settings.getBoolean("coup_behavior", false));
        activity_settings_coup_behavior_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                app_settings.edit().putBoolean("coup_behavior", activity_settings_coup_behavior_switch.isChecked()).apply();
            }
        });

        settings_activity_top_bar_back_button = findViewById(R.id.settings_activity_top_bar_back_button);
        settings_activity_top_bar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}