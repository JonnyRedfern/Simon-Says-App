package com.example.android.simonsays;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList currPattern = new ArrayList(); // this will hold the computer generated patter
    static int currLevel = 1; //this will hold the current level that the user is on
    boolean lost = false; // this will turn true if the user selects a wrong button
    private int playerCurrButton = 0; // this is the current place in the patter that the player is on. every single new level it will be reset back to 0
    private int prevButtonColor;
    private Button bb;
    private Button rb;
    private Button gb;
    private Button yb;
    //button sounds
    ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    ToneGenerator toneGen2 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    ToneGenerator toneGen3 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    ToneGenerator toneGen4 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        bb = (Button)findViewById(R.id.blue);
        rb = (Button)findViewById(R.id.red);
        gb = (Button)findViewById(R.id.green);
        yb = (Button)findViewById(R.id.yellow);
        //make button unclickable until the first patter is created and shown
        turnOffButtons();
    }

    //when this screen is returned to or started for the first time this method is executed
    public void onResume() {
        super.onResume();
        //wait 1.5 seconds before showing initial pattern
        new CountDownTimer(1500, 100) {
            public void onFinish() {
                currLevel = 1; // reset curr level
                createPattern();
            }

            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void createPattern() {
        //make button inabled for duration of the animation
        turnOffButtons();
        lost = false; //reset lost
        //currPattern.clear(); // clear the currPattern
        int tempLevel = 0; // this will be used to keep track of the number of buttons in a patter.
        int tempButton; // this will be used to hold the value of the random number generated
        Random r = new Random();
        while (tempLevel < currLevel) {
            tempButton = r.nextInt(4) + 1; // generates random number between 1 and 4
            if(currLevel > 1) {
               /* while(prevButtonColor == tempButton) {
                    tempButton = r.nextInt(4) + 1;
                }*/
            }
            prevButtonColor = tempButton;
            currPattern.add(tempButton);
            tempLevel++;
            System.out.println("current Level is:" + currLevel + ":" + tempButton); // display the button that was chosen
        }
        showPattern(0, (int) currPattern.get(0));
    }

    public void onClick(View v) {
        Button button = (Button) v; // the button that was clicked
        if (button.getId() == R.id.green) {
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,250);
            if (((int) currPattern.get(playerCurrButton)) != 1) {
                youLost();
            }
        } else if (button.getId() == R.id.red) {
            toneGen2.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,250);
            if (((int) currPattern.get(playerCurrButton)) != 2) {
                youLost();
            }
        } else if (button.getId() == R.id.yellow) {
            toneGen3.startTone(ToneGenerator.TONE_CDMA_ABBR_INTERCEPT,250);
            if (((int) currPattern.get(playerCurrButton)) != 3) {
                youLost();
            }
        } else if (button.getId() == R.id.blue) {
            toneGen4.startTone(ToneGenerator.TONE_CDMA_PIP,250);
            if (((int) currPattern.get(playerCurrButton)) != 4) {
                youLost();
            }
        }
        if (!lost) { //if the player selected the right button
            playerCurrButton++;
            //if the player selected all the right buttons then level up the system and create a new patter
            if (playerCurrButton == currLevel) {
                currLevel++; // incriment currLevel
                playerCurrButton = 0; // reset the playerCurrButton
                turnOffButtons();
                new CountDownTimer(1000, 100) {

                    public void onFinish() {
                        createPattern();
                    }
                    public void onTick(long millisUntilFinished) {
                        // millisUntilFinished    The amount of time until finished.
                    }
                }.start();
            }
        } else {
            //createPattern();
            startActivity(new Intent(getApplicationContext(), playagainscreen.class));
        }

    }

    //player lost
    private void youLost() {
        lost = true;
        playerCurrButton = 0;
        currPattern.clear();
    }

    private void showPattern(final int toShow, final int currButton) {
        final Button b;

        //display button with noise
        if (currButton == 1) {
            toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,250);
            b = (Button) findViewById(R.id.green);
            b.setBackgroundColor(Color.parseColor("#81C784"));
        } else if (currButton == 2) {
            toneGen2.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT,250);
            b = (Button) findViewById(R.id.red);
            b.setBackgroundColor(Color.parseColor("#E57373"));
        } else if (currButton == 3) {
            toneGen3.startTone(ToneGenerator.TONE_CDMA_ABBR_INTERCEPT,250);
            b = (Button) findViewById(R.id.yellow);
            b.setBackgroundColor(Color.parseColor("#FFF176"));
        } else {
            toneGen4.startTone(ToneGenerator.TONE_CDMA_PIP,250);
            b = (Button) findViewById(R.id.blue);
            b.setBackgroundColor(Color.parseColor("#90CAF9"));
        }

        //buffer time before method is recursively called to show next button in pattern
        if (toShow + 1 < currLevel) {
            new CountDownTimer(1000, 100) {

                public void onFinish() {
                    if (currButton == 1) {
                        b.setBackgroundColor(Color.parseColor("#7FFF00"));
                        showPattern(toShow + 1, ((int) currPattern.get(toShow + 1)));
                    } else if (currButton == 2) {
                        b.setBackgroundColor(Color.parseColor("#DC143C"));
                        showPattern(toShow + 1, ((int) currPattern.get(toShow + 1)));
                    } else if (currButton == 3) {
                        b.setBackgroundColor(Color.parseColor("#FFFF00"));
                        showPattern(toShow + 1, ((int) currPattern.get(toShow + 1)));
                    } else {
                        b.setBackgroundColor(Color.parseColor("#2196F3"));
                        showPattern(toShow + 1, ((int) currPattern.get(toShow + 1)));
                    }
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();
        } else {
            new CountDownTimer(1000, 100) {
                public void onFinish() {


                    if (currButton == 1)
                        b.setBackgroundColor(Color.parseColor("#7FFF00"));
                        else if (currButton == 2)
                                b.setBackgroundColor(Color.parseColor("#DC143C"));
                            else if (currButton == 3)
                                b.setBackgroundColor(Color.parseColor("#FFFF00"));
                            else
                                b.setBackgroundColor(Color.parseColor("#2196F3"));


                            //enable buttons once animation is over
                            turnOnButton();
                        }



                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();
        }
    }

    //turns off buttons
    private void turnOffButtons() {
        bb.setClickable(false);
        gb.setClickable(false);
        rb.setClickable(false);
        yb.setClickable(false);
    }

    //turns on buttons
    private void turnOnButton() {
        bb.setClickable(true);
        gb.setClickable(true);
        rb.setClickable(true);
        yb.setClickable(true);
    }
}





