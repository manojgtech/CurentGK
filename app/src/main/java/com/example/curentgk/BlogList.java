package com.example.curentgk;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class BlogList extends BaseActivity {
    private static final String JSON_URL = "https://www.googleapis.com/blogger/v2/blogs/1295524969366832914/posts?key=AIzaSyCdJSznqBwiGnEE-expv4vLMuZsM5JN7m0";
    private static final String Page_JSON_URL = "https://www.googleapis.com/blogger/v2/blogs/1295524969366832914/pages?key=AIzaSyCdJSznqBwiGnEE-expv4vLMuZsM5JN7m0";

    //listview object
    RecyclerView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Blog> blogList;



    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blog_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setTitle("Top Recipes");
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.bloglist);
        blogList = new ArrayList<>();

        adapter = new ListViewAdapter(getApplicationContext(), blogList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(listView.getContext(), linearLayoutManager.getOrientation());

        //listView.setHasFixedSize(true);
        listView.setLayoutManager(linearLayoutManager);
        listView.addItemDecoration(dividerItemDecoration);
        listView.setAdapter(adapter);


        //this method will fetch and parse the data
       loadHeroList();


    }

    private  void loadHeroList(){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.nprogbar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("items");
                           // Log.d("JSONARR", heroArray.toString());

                         //   Toast.makeText(getApplicationContext(), heroArray.toString(), Toast.LENGTH_LONG).show();

                            //now looping through all the elements of the json array
                           for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);
                               // Log.d("JSONob", heroObject.toString());

                                //creating a hero object and giving them the values from json object
                                Blog hero = new Blog(heroObject.getString("title"),heroObject.getString("id"));

                                //adding the hero to herolist
                               blogList.add(hero);
                            }

                            //creating custom adapter object

                            adapter.notifyDataSetChanged();
                            //adding the adapter to listview
                            //listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            e.printStackTrace();
                            Log.d("Error1",e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("Error", error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    /*pages */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
            Toast.makeText(getApplicationContext(), "back press",
                    Toast.LENGTH_LONG).show();

        return false;
        // Disable back button..............
    }





}
