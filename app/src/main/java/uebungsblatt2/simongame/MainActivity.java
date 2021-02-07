package uebungsblatt2.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //AppCompatActivity: Basis Klasse von Activitys, die neue Features auf älteren Android Devicen verwenden wollen
    //Androidx stellt backward-compatibilität sicher, bietet weitere features und Libraries
    //android für die vorwärtskompatibilität


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Zugriff auf den Code der Elternklasse
        setContentView(R.layout.activity_main); //Basis von jeder Activity, auf dem baut alles weitere auf -- Root View -- Body Tag bei html
    }

    public void normalMode (View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("duration",1200);
        startActivity(intent);
    }

    public void fastMode (View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("duration",600);
        startActivity(intent);
    }
}
//Die Navigation zwischen den einzelnen Activities wird über Intents durchgeführt --> Nachrichtenaustausch
// Damit die jeweiligen Activities nicht mehrmals auf den Back Stack gelegt werden, sollten die Intents mit dem Flag FLAG_ACTIVITY_CLEAR_TOP versehen