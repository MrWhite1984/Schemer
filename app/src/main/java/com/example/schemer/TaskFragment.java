package com.example.schemer;

import static android.content.Context.VIRTUAL_DEVICE_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static classes.ButtonCreator.DropButton;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import classes.ButtonCreator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private LinearLayout linearLayout;

    private ImageButton task_fragment_top_bar_add_button;

    public SQLiteDatabase appDataBase;
    public int PID;
    private Cursor appData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_task, container, false);
        linearLayout = inf.findViewById(R.id.task_fragment_ll);
        task_fragment_top_bar_add_button = inf.findViewById(R.id.task_fragment_top_bar_add_button);
        //appData = appDataBase.rawQuery("SELECT * FROM Tasks WHERE PID = ?", new String[]{String.valueOf(PID)});
        task_fragment_top_bar_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appData = appDataBase.rawQuery("SELECT * FROM Tasks WHERE PID = ?", new String[]{String.valueOf(PID)});
                TaskDataActivity.creating = true;
                TaskDataActivity.PID = PID;
                TaskDataActivity.appDataBase = appDataBase;
                TaskDataActivity.quantityOfRecords = appData.getCount();
                startActivity(new Intent(getContext(), TaskDataActivity.class));
            }
        });



        return inf;
    }

    @Override
    public void onResume() {
        super.onResume();
        appData = appDataBase.rawQuery("SELECT * FROM Tasks WHERE PID = ?", new String[]{String.valueOf(PID)});
        linearLayout.removeAllViews();
        while (appData.moveToNext()){
            Button button = ButtonCreator.CreateButton(appData.getString(2), getContext(), appData.getInt(0));
            if(appData.getInt(3) == 1){
                button.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(!button.getPaint().isStrikeThruText()){
                        button.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        ContentValues row = new ContentValues();
                        row.put("isCompleted", true);
                        appDataBase.update("Tasks", row, "ID = ?", new String[]{button.getContentDescription().toString()});
                    }
                    else {
                        button.setPaintFlags(0);
                        ContentValues row = new ContentValues();
                        row.put("isCompleted", false);
                        appDataBase.update("Tasks", row, "ID = ?", new String[]{button.getContentDescription().toString()});
                    }

                    return true;
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDataActivity.creating = false;
                    TaskDataActivity.PID = PID;
                    TaskDataActivity.appDataBase = appDataBase;
                    TaskDataActivity.ID = Integer.parseInt(button.getContentDescription().toString());
                    TaskDataActivity.taskText = button.getText().toString();
                    startActivity(new Intent(getContext(), TaskDataActivity.class));
                }
            });
            linearLayout.addView(button);
        }
    }
}