package classes;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.example.schemer.AddProjectPanel;
import com.example.schemer.R;


public class ButtonCreator {
    public static Button CreateButton(String projectName, Context context){
        Button button = new Button(context);
        button.setText(projectName);
        button.setBackgroundColor(context.getResources().getColor(R.color.projectColor));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), AddProjectPanel.class));
            }
        });
        return button;
    }
}
