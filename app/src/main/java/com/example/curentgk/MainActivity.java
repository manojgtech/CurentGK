package com.example.curentgk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.example.curentgk.NetworkUtil.getConnectivityStatusString;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private static final String EMAIL = "email";
   // private CallbackManager callbackManager;
    private TextView textView;

    private BroadcastReceiver MyReciever = null;
    AlertDialog.Builder builder;
    @Override




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        NetworkUtil netu = new NetworkUtil();
        String xs = netu.getConnectivityStatusString(this);

            if(xs==null){
                showAlert();
            }else{

                new Handler().postDelayed(new Runnable() {


                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        Intent mainint=new Intent(MainActivity.this, BlogList.class);
                        startActivity(mainint);
                        finish();
                    }
                }, 5000);
            }


        Toast.makeText(this, xs, Toast.LENGTH_LONG).show();


    }










    //chck net
      public  void showAlert()
      {
          builder = new AlertDialog.Builder(this);
          /** show alert*/

          builder.setMessage("Please enable internet").setTitle("No internet");

          //Setting message manually and performing action on button click
          builder.setMessage("Please enable internet to access app")
                  .setCancelable(false)
                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          finish();

                      }
                  })
                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                          //  Action for 'NO' Button
                          dialog.cancel();

                      }
                  });
          //Creating dialog box
          AlertDialog alert = builder.create();
          //Setting the title manually

          alert.show();
      }



}
