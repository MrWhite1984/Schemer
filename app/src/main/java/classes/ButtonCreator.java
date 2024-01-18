package classes;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.schemer.ProjectCardPanel;
import com.example.schemer.R;



public class ButtonCreator {
    public static Button CreateButton(String projectName, Context context, int count){
        Button button = new Button(context);
        button.setText(projectName);
        button.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.projectColor)));
        button.setPadding(5, 5, 5, 5);
        button.setTop(5);
        button.setContentDescription(String.valueOf(count));

        return button;
    }

    public static void DropButton(Button button, LinearLayout linearLayout){
        linearLayout.removeView(button);
    }
}
