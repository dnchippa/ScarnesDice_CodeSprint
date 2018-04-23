package com.dhanraj.apps.scarnesdice_codesprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int userScore=0,userScoreFinal=0,computerScore=0,computerScoreFinal=0, roll1 =0,roll2=0,FinalScore=100;
    Button rollbtn,holdbtn,resetbtn;
    TextView textViewUser,textViewComp,diceScore,diceScore2;
    ImageView image1,image2;

    Handler timerHandler = new Handler();
    Random r = new Random();

    private int diceIcons [] = {
            R.drawable.dice1, R.drawable.dice2, R.drawable.dice3,
            R.drawable.dice4, R.drawable.dice5, R.drawable.dice6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollbtn=(Button) findViewById(R.id.rollBtn);
        holdbtn=(Button) findViewById(R.id.holdBtn);
        resetbtn=(Button) findViewById(R.id.resetBtn);
        textViewUser = (TextView) findViewById(R.id.textUserScore);
        textViewComp = (TextView) findViewById(R.id.textCompScore);

        image1 = (ImageView) findViewById(R.id.imageView1);
        image2 = (ImageView) findViewById(R.id.imageView2);
        diceScore=(TextView) findViewById(R.id.DiceS);
        diceScore2=(TextView) findViewById(R.id.DiceS2);

        rollbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("RollOnClick","Roll Click Entered..");

                RollDice();
                if(roll1 !=1 && roll2!=1){
                    userScore+= (roll1 + roll2);
                }
                else if(roll1==1 && roll2==1){
                    userScore=0;
                    userScoreFinal=0;
                    holdDice();

                }
                else {
                    userScore=0;
                    holdDice();

                }
            }
        });

        holdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HoldOnClick","Roll Click Entered..");

                holdDice();

            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDice();
            }
        });

    }


    private void resetDice() {
        computerScore=0;
        computerScoreFinal=0;
        userScoreFinal=0;
        userScore=0;
        roll1 =0;
        roll2=0;
        textViewUser.setText("0");
        textViewComp.setText("0");

        image1.setImageResource(R.drawable.dice1);
        image2.setImageResource(R.drawable.dice1);
        DiceScoreUpdate();
        rollbtn.setEnabled(true);
        holdbtn.setEnabled(true);

    }


    private void holdDice() {
        Log.i("HoldDice","Hold Entered..");
        userScoreFinal+=userScore;
        textViewUser.setText(String.valueOf(userScoreFinal));
        userScore=0;
        boolean userWon=false;
        if(userScoreFinal>=FinalScore){
            userWon=true;
            Toast.makeText(getApplicationContext(),"Congratulations You Won!",Toast.LENGTH_LONG).show();
            rollbtn.setEnabled(false);
            holdbtn.setEnabled(false);
        }
        if(!userWon)
            ComputerPlays();
        Log.i("HoldDice","Hold Finished..");
    }

    private void RollDice() {
        Log.i("RollDice","Roll Entered..");

        int no1 = r.nextInt(6) + 1;
        int no2 = r.nextInt(6) + 1;

        image1.setImageResource(diceIcons[no1-1]);
        image2.setImageResource(diceIcons[no2-1]);

        roll1 = no1;
        roll2 = no2;
        DiceScoreUpdate();
        Log.i("RollDice","Roll Finished..");

    }


    void DiceScoreUpdate(){
        diceScore.setText(String.valueOf(roll1));
        diceScore2.setText(String.valueOf(roll2));

    }

    Runnable runnablefun=new Runnable() {
        @Override
        public void run() {
            RollDice();

            if (roll1 != 1 && roll2!=1) {
                computerScore += (roll1 + roll2);
                if(r.nextBoolean())
                    timerHandler.postDelayed(this,500);
                else {

                    computerScoreFinal += computerScore;
                    textViewComp.setText(String.valueOf(computerScoreFinal));
                    computerScore = 0;

                    if(computerScoreFinal>=FinalScore){

                        Toast.makeText(getApplicationContext(),"Computer Wins!",Toast.LENGTH_LONG).show();
                        rollbtn.setEnabled(false);
                        holdbtn.setEnabled(false);
                    }
                    else
                    {
                        rollbtn.setEnabled(true);
                        holdbtn.setEnabled(true);
                    }

                }
            }else if (roll1==1 && roll2==1){
                computerScore=0;
                computerScoreFinal=0;
                textViewComp.setText(String.valueOf(computerScoreFinal));
                rollbtn.setEnabled(true);
                holdbtn.setEnabled(true);
            }
            else {
                computerScore = 0;
                computerScoreFinal += computerScore;
                textViewComp.setText(String.valueOf(computerScoreFinal));
                rollbtn.setEnabled(true);
                holdbtn.setEnabled(true);
            }
        }
    };


    void ComputerPlays() {
        rollbtn.setEnabled(false);
        holdbtn.setEnabled(false);
        timerHandler.postDelayed(runnablefun, 500);


    }
}