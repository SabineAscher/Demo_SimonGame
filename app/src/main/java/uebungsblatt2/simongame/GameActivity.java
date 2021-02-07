package uebungsblatt2.simongame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    //AppCompatActivity: Basis Klasse von Activitys, die neue Features auf älteren Android Devicen verwenden wollen
    //Androidx stellt backward-compatibilität sicher, bietet weitere features und Libraries
    //android für die vorwärtskompatibilität


    ImageButton[] buttonarray = new ImageButton[4];
    ImageButton red;
    ImageButton green;
    ImageButton blue;
    ImageButton yellow;
    int [] seq;

    //Randoms + Counter + Intent für Aufgabe 3, Aufgabe 4
    ArrayList<Integer> randomfigures;
    Random r = new Random();
    int counter = 0;
    Intent intent; //Die Navigation zwischen den einzelnen Activities wird über Intents durchgeführt --> Nachrichtenaustausch
    int duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Zugriff auf den Code der Elternklasse
        setContentView(R.layout.activity_game); //Basis von jeder Activity, auf dem baut alles weitere auf -- Root View -- Body Tag bei html

        randomfigures = new ArrayList<>();

        createButtonArray();

        //Aufruf animateButtons Methode für Aufgabe 1
        //animateButtons(new int [] {0, 1, 2, 3});

        intent = getIntent();
        if(intent.hasExtra("duration")) {
            duration = intent.getIntExtra("duration", 2500);
        }

        //OnTouchListener für Aufgabe 2, Aufgabe 3
        for (ImageButton button : buttonarray) {
            button.setOnTouchListener(this);
        }

        convertArrayListToArray();
    }

    private void createButtonArray() {
        red = findViewById(R.id.imageButtonRed);
        green = findViewById(R.id.imageButtonGreen);
        blue = findViewById(R.id.imageButtonBlue);
        yellow = findViewById(R.id.imageButtonYellow);

        buttonarray[0] = red;
        buttonarray[1] = green;
        buttonarray[2] = blue;
        buttonarray[3] = yellow;
    }

    private void enableButtons(boolean b) {
        //de- und aktivieren für Aufgabe 3
        red.setEnabled(b);
        green.setEnabled(b);
        blue.setEnabled(b);
        yellow.setEnabled(b);
    }

    private void checkButtonOrder(int buttonId){

        //wenn der counter >= der Länge des randomfigures -1 und die Ids übereinstimmen
        if(counter >= randomfigures.size()-1 && buttonarray[randomfigures.get(counter)].getId() == buttonId){
            convertArrayListToArray(); //dann erstelle eine neue Zufallszahl und animiere die Buttons inkl. neuer blinkender Button

        }

        //wenn der Touch die richtige Farbe hat  -- Id vom Button in randomfigures an der Stelle counter = Button Id des gerade geklickten Buttons
        else if (buttonarray[randomfigures.get(counter)].getId() == buttonId){
            counter++;
        }

        //Falsche Farbe - Überleitung zu GameOverActivity
        else {
            wrongButton(randomfigures.size()-1);
        }
    }

    private void convertArrayListToArray(){
        int randomNumber = randomNumber();
        randomfigures.add(randomNumber);

        seq = new int[randomfigures.size()];
        for (int j = 0; j < seq.length; j++) {
            seq[j] = randomfigures.get(j);
        }


        animateButtons(seq);

    }

    private void animateButtons(int[] sequence) {
        counter = 0;
        enableButtons(false);

        ObjectAnimator[] obarray = new ObjectAnimator[sequence.length];  //mithilfe des ObjectAnimators können die Buttons animiert werden, es braucht mehrere, um für jeden BUtton die verschiedenen Blincker zu animieren
        for (int i = 0; i < obarray.length; i++) {
            obarray[i] = ObjectAnimator.ofFloat(buttonarray[sequence[i]], "alpha", 1f, 0.4f, 1f);   //Button herausfiltern und Alpha verändern
            obarray[i].setDuration(duration);
        }

        AnimatorSet am = new AnimatorSet();     //ermöglicht Animationen zu gruppieren und in Beziehung zueinander zu setzen und abzuspielen
        am.playSequentially(obarray);  //mithilfer dieser Methode kann das ObjectAnimator Array dem AnimatorSet hinzugefügt werden -- Animationen werden der Reihe nach ausgeführt
        am.setStartDelay(500);
        am.start();

        //Listener für Aufgabe 3 -- Buttons wieder aktivieren
        am.addListener(new AnimatorListenerAdapter() {  //Adapter stellt leere Implementierungen der Methoden bereit, die ich  überschreiben kann
            @Override
            public void onAnimationEnd(Animator animation) {  //Was tun wenn die Animation zu Ende ist?
                enableButtons(true);
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        ImageButton clickbutton = (ImageButton) v;

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                animateButtonsDark(clickbutton);
                break; //ansonsten würde DOWN und UP gleichzeitig ausgeführt werden

            case(MotionEvent.ACTION_UP):
                animateButtonsLight(clickbutton);
                checkButtonOrder(clickbutton.getId());
                break;

        }
        return true;
    }

    //animateButtons Methode für Aufgabe 2
    private void animateButtonsDark(ImageButton buttonToChangealpha){
        ObjectAnimator oa = ObjectAnimator.ofFloat(buttonToChangealpha, "alpha",0.4f);
        oa.setDuration(400);
        AnimatorSet am = new AnimatorSet();
        am.playSequentially(oa);
        am.start();
    }

    //animateButtons Methode für Aufgabe 2
    private void animateButtonsLight(ImageButton buttonToChangealpha) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(buttonToChangealpha, "alpha",1f);
        AnimatorSet am = new AnimatorSet();
        am.playSequentially(oa);
        am.start();
    }

    public int randomNumber(){
        return (int) (Math.random() * 4);
    }

 /*   //OnTouch Methode für Aufgabe 2
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        ImageButton clickbutton = (ImageButton) v;

        switch (action) {

            case (MotionEvent.ACTION_DOWN):
                if (clickbutton == red) {
                    animateButtonsDark(new int[]{0});
                }
                if (clickbutton == green) {
                    animateButtonsDark(new int[]{1});
                }
                if (clickbutton == blue) {
                    animateButtonsDark(new int[]{2});
                }
                if (clickbutton == yellow) {
                    animateButtonsDark(new int[]{3});
                }

            case (MotionEvent.ACTION_UP):
                if (clickbutton == red) {
                    animateButtonsLight(new int[]{0});
                }
                if (clickbutton == green) {
                    animateButtonsLight(new int[]{1});
                }
                if (clickbutton == blue) {
                    animateButtonsLight(new int[]{2});
                }
                if (clickbutton == yellow) {
                    animateButtonsLight(new int[]{3});
                }

            default:
                return false;
        }
    }*/

    private void wrongButton(int size) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("rounds","Amazing you played \n" + size + " rounds straight!");
        startActivity(intent);

        // Damit die jeweiligen Activities nicht mehrmals auf den Back Stack gelegt werden, sollten die Intents mit dem Flag FLAG_ACTIVITY_CLEAR_TOP versehen werden.
    }
}

