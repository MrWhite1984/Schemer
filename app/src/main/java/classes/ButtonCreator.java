package classes;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.schemer.AddProjectPanel;
import com.example.schemer.MainActivity;
import com.example.schemer.ProjectCardPanel;
import com.example.schemer.R;


public class ButtonCreator {
    public static Button CreateButton(String projectName, Context context){
        Button button = new Button(context);
        button.setText(projectName);
        button.setBackgroundColor(context.getResources().getColor(R.color.projectColor));
        button.setPadding(5, 5, 5, 5);
        button.setTop(5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectCardPanel.projectName = projectName;
                context.startActivity(new Intent(context.getApplicationContext(), ProjectCardPanel.class));
            }
        });

        return button;
    }

    public static void DropButton(Button button, LinearLayout linearLayout){
        linearLayout.removeView(button);
        linearLayout.getContext().startActivity(new Intent(linearLayout.getContext().getApplicationContext(), MainActivity.class));
    }
}
