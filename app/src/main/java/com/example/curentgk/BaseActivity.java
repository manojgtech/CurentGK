package com.example.curentgk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class BaseActivity extends AppCompatActivity {

    private static final String Page_JSON_URL = "https://www.googleapis.com/blogger/v2/blogs/1295524969366832914/pages?key=AIzaSyCdJSznqBwiGnEE-expv4vLMuZsM5JN7m0";
 public static Map<String, String> menuitems = new HashMap<String, String>();

    SharedPreferences pagepreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_base);
    }
    public void setContentView(int layoutResID)
    {

         DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar =  findViewById(R.id.toolbar1);
        pagepreferences= getSharedPreferences("Pageids", Context.MODE_PRIVATE);
        if (useToolbar())
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("My Recipies");
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        NavigationView navView = (NavigationView) findViewById(R.id.navigationView);

        Menu menu = navView.getMenu();

        getallpages();
        Log.d("PAGE",menuitems.toString());



        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.action_settings){
                    updateBG();
                    return true;
                }else {
                    SharedPreferences shared = getSharedPreferences("Pageids", MODE_PRIVATE);

                    String postID = (shared.getString("PAGEID-" + id, null));
                    Intent blogin = new Intent(getApplicationContext(), Blogpost.class);
                    blogin.putExtra("postid", postID);
                    blogin.putExtra("type", 2);
                    getApplicationContext().startActivity(blogin);
                    return true;
                }

            }
        });



    }

    protected boolean useToolbar()
    {
        return true;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maintool, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout fullView1 = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        if(item.getItemId()==R.id.drawer){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "http://www.e-baba.in/apks/foodapp.apk";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download our app");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return true;
    }

    public void getallpages(){


        ;    StringRequest stringRequest = new StringRequest(Request.Method.GET, Page_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        // progressBar.setVisibility(View.INVISIBLE);
                      //  Map<String, String> menuitems1 = new HashMap<String, String>();

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            JSONArray heroArray = obj.getJSONArray("items");
                            //Log.d("JSONARR", heroArray.toString());
                            NavigationView navView = (NavigationView) findViewById(R.id.navigationView);

                            Menu menu = navView.getMenu();
                            Menu submenu = menu.addSubMenu("Pages");
                            SharedPreferences.Editor editor = pagepreferences.edit();
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);
                               Log.d("JSONobpage", heroObject.toString());
                                menuitems.put(heroObject.getString("id"), heroObject.getString("title"));
                                //creating a hero object and giving them the values from json object

                        submenu.add(1,i,i,heroObject.getString("title"));

                                editor.putString("PAGEID-"+i,heroObject.getString("id"));


                                //adding the hero to herolist

                            }

                            navView.invalidate();
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
//Log.d("MAP",menuitems.toString());
        //return menuitems1;

    }

    public  void updateBG(){
Intent newint=getIntent();
 finish();
 startActivity(newint);

    }
}
