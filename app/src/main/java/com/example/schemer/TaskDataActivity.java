package com.example.schemer;

import static classes.ButtonCreator.DropButton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.ai.client.generativeai.type.Content;

import classes.ButtonCreator;

public class TaskDataActivity extends AppCompatActivity {

    public static boolean creating;
    public static int PID;
    public static int ID;
    public static int quantityOfRecords;
    public static SQLiteDatabase appDataBase;
    public static String taskText;

    public ImageButton applyIB;
    public ImageButton deleteIB;
    public EditText activity_task_data_task_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_data);

        activity_task_data_task_fragment = findViewById(R.id.activity_task_data_task_fragment);
        if(!creating){
            activity_task_data_task_fragment.setText(taskText);
        }
        applyIB = findViewById(R.id.activity_task_data_applyIB);
        applyIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!activity_task_data_task_fragment.getText().equals("")){
                    if(creating){
                        ContentValues row = new ContentValues();
                        row.put("ID", quantityOfRecords+1);
                        row.put("PID", PID);
                        row.put("Data", activity_task_data_task_fragment.getText().toString());
                        row.put("isCompleted", false);
                        appDataBase.insert("Tasks", null, row);
                        finish();
                    }
                }
            }
        });
        deleteIB = findViewById(R.id.activity_task_data_deleteIB);
        deleteIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creating){
                    finish();
                }
                else {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Вы уверены, что хотите удалить задачу?");
                    //builder.setMessage("Задача " + activity_task_data_task_fragment + " будет удален");
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            appDataBase.delete("Tasks", "ID = ?", new String[]{String.valueOf(ID)});
                        }
                    });
                    builder.setNegativeButton("Нет", null);
                    builder.show();*/
                    appDataBase.delete("Tasks", "ID = ?", new String[]{String.valueOf(ID)});
                    finish();
                }
            }
        });
    }
}