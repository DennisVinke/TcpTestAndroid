package com.proto.dennis.tcptest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    TcpClient mTcpClient;
    Button connectButton, race, fire, police, act1, act2, act0, act3;
    EditText ipField;
    private static String RESET = "act0$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        connectButton = (Button) findViewById(R.id.connectButton);
        race = (Button) findViewById(R.id.race);
        fire = (Button) findViewById(R.id.Fire);
        police = (Button) findViewById(R.id.Police);
        act1 = (Button) findViewById(R.id.action1);
        act2 = (Button) findViewById(R.id.Action2);
        act3 = (Button) findViewById(R.id.Action3);
        act0 = (Button) findViewById(R.id.resetButton);
        ipField = (EditText) findViewById(R.id.ip);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Connecting", Toast.LENGTH_SHORT).show();
                String ip = ipField.getText().toString();
                new ConnectTask().execute(ip);
                if (mTcpClient != null) {
                    Toast.makeText(MainActivity.this, "verstuur een message", Toast.LENGTH_SHORT).show();

                    mTcpClient.sendMessage("testing$");
                }
            }
        });

        race.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Spawning Race set", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("race$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        fire.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Spawning Race set", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("fire$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        police.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Spawning Race set", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("police$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        act1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Action 1", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("act1$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        act2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Action 2", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("act2$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
        act3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Action3", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("act3$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });

        act0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "Reseting actions!", Toast.LENGTH_SHORT).show();
                if (mTcpClient != null) {
                    sendMessage("act0$", v, event);
                    return true;
                }
                else{
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });




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


    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {
            Log.d("Terst", message[0]);
            //we create a TCPClient object
                mTcpClient = new TcpClient(message[0] ,new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run(message[0]);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            //process server response here....

        }
    }

    void sendMessage(String msg, View v, MotionEvent event){
        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Toast.makeText(this, "Doing stuff"+ msg, Toast.LENGTH_SHORT).show();
                Log.d("TouchEvent", "Action_Down:"+msg);
                mTcpClient.sendMessage(msg);
                break;
            case MotionEvent.ACTION_UP:
                Toast.makeText(this, "Ending stuff"+ msg, Toast.LENGTH_SHORT).show();
                Log.d("TouchEvent", "Action_Up:"+RESET);
                mTcpClient.sendMessage(RESET);
        }
    }

}

