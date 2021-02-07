package uebungsblatt2.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();

        if (intent.hasExtra("rounds")) {      //wenn der rounds Intent rein kommt
            String message = intent.getStringExtra("rounds");  //String auslesen
            TextView textMessage = findViewById(R.id.textView);  //TextView-Feld ansprechen
            textMessage.setText(message);       //Text im TextView festlegen

        }

    }

    public void restart (View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}