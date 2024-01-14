package com.example.schemer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddProjectPanel extends Activity {
    private Button addNewProjectButton;
    private Button cancelAddNewProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_project);

        addNewProjectButton = findViewById(R.id.addNewProject);
        addNewProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        addNewProjectButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return false;
            }
        });

        cancelAddNewProject = findViewById(R.id.cancelAddNewProject);
        cancelAddNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
