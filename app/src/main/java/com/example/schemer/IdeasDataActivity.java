package com.example.schemer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class IdeasDataActivity extends AppCompatActivity {

    public static boolean creating;
    public static int PID;
    public static int ID;
    public static int quantityOfRecords;
    public static SQLiteDatabase appDataBase;
    public static String ideaText;

    public ImageButton applyIB;
    public ImageButton deleteIB;
    public EditText activity_idea_data_idea_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas_data);

        activity_idea_data_idea_fragment = findViewById(R.id.activity_ideas_data_task_fragment);
        if(!creating){
            activity_idea_data_idea_fragment.setText(ideaText);
        }
        applyIB = findViewById(R.id.activity_ideas_data_applyIB);
        applyIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!activity_idea_data_idea_fragment.getText().toString().equals("")){
                    if(creating){
                        ContentValues row = new ContentValues();
                        row.put("ID", quantityOfRecords+1);
                        row.put("PID", PID);
                        row.put("Data", activity_idea_data_idea_fragment.getText().toString());
                        appDataBase.insert("Ideas", null, row);
                        finish();
                    }
                    else {
                        ContentValues row = new ContentValues();
                        row.put("Data", activity_idea_data_idea_fragment.getText().toString());
                        appDataBase.update("Ideas", row, "ID = ?", new String[]{String.valueOf(ID)});
                        finish();
                    }
                }
            }
        });
        deleteIB = findViewById(R.id.activity_ideas_data_deleteIB);
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
                    appDataBase.delete("Ideas", "ID = ?", new String[]{String.valueOf(ID)});
                    finish();
                }
            }
        });
    }
}