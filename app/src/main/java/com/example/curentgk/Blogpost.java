package com.example.curentgk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.List;

public class Blogpost extends BaseActivity {
    private static final String JSON_URL = "https://www.googleapis.com/blogger/v2/blogs/1295524969366832914/posts/";
    private static final String JSON_URL2 = "https://www.googleapis.com/blogger/v2/blogs/1295524969366832914/pages/";

    //listview object
    ListView listView;
    private  String key="AIzaSyCdJSznqBwiGnEE-expv4vLMuZsM5JN7m0";

    //the hero list where we will store all the hero objects after parsing json
    List<Blog> blogList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blogpost);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        Toast.makeText(getApplicationContext(),b.getString("postid"), Toast.LENGTH_LONG).show();
     String postid=   b.getString("postid");
        int type=   b.getInt("type");
        final TextView titlef=findViewById(R.id.blogtitle);
        final WebView contentf=findViewById(R.id.blogcontent);
        final TextView sharedata=findViewById(R.id.sharetxt);
        WebSettings webSettings = contentf.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //send mail
        final TextView emailtxt=findViewById(R.id.myemail);
        emailtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailtxt.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "E-baba Query");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hi Rajat");
           //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Contact Us"));
            }
        });

        //open website
        ImageButton shareicon=findViewById(R.id.postshare);
        shareicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = sharedata.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, titlef.getText().toString());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

    final  TextView weburl=findViewById(R.id.website);
        weburl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String myurl=    weburl.getText().toString();
              //  String url = "http://www.example.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(myurl));
                startActivity(i);
            }
        });


      if(postid!=null){
          String post_url="";
          if(type==1){
               post_url=JSON_URL+postid+"?key="+key;
          }else{
               post_url=JSON_URL2+postid+"?key="+key;
          }

          final ProgressBar progressBar = (ProgressBar) findViewById(R.id.prog_one);

          //making the progressbar visible
          progressBar.setVisibility(View.VISIBLE);

          //creating a string request to send request to the url
          StringRequest stringRequest = new StringRequest(Request.Method.GET, post_url,
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
                              String ptitle=obj.getString("title");
                              String pcontent=obj.getString("content");
                              //titlef.setText(ptitle);
                             // View v = inflater.inflate(R.layout.fragment, container, false);
                               sharedata.setText(android.text.Html.fromHtml(pcontent).toString());
                              setTitle(ptitle);


                              contentf.loadData(pcontent, "text/html; charset=utf-8", "utf-8");


                          } catch (JSONException e) {
                              progressBar.setVisibility(View.INVISIBLE);
                              Log.d("POSTER1",e.getMessage());
                              e.printStackTrace();
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          progressBar.setVisibility(View.INVISIBLE);
                          Log.d("POSTER",error.toString());
                          //displaying the error in toast if occurrs
                          error.printStackTrace();
                          Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                      }
                  });

          //creating a request queue
          RequestQueue requestQueue = Volley.newRequestQueue(this);

          //adding the string request to request queue
          requestQueue.add(stringRequest);

      }
    }
}
