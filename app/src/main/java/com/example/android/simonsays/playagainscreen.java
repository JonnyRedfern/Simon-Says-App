package com.example.android.simonsays;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class playagainscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playagainscreen);
        //hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView score =(TextView) findViewById(R.id.score);
        score.setText(""+MainActivity.currLevel);

        //change font
        TextView text = (TextView)findViewById(R.id.textview);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/Raleway-ExtraBold.ttf");
        text.setTypeface(myCustomFont);
        score.setTypeface(myCustomFont);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playagainscreen, menu);
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

    public void playAgain(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
