package com.example.wallpaperapidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpaperapidemo.view.DarkActivity;
import com.example.wallpaperapidemo.view.ForestActivity;
import com.example.wallpaperapidemo.view.MoonActivity;
import com.example.wallpaperapidemo.view.NatureActivity;
import com.example.wallpaperapidemo.view.SkyActivity;
import com.example.wallpaperapidemo.view.SpaceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Wallpapemain extends AppCompatActivity {

    RecyclerView recyclerView;
    WallpaperAdapter wallpaperAdapter;
    List<Wallpapermodel> wallpapermodelList;
    int pageNumber=1;

    Boolean isScrolling=false;
    int currenItems,totalItems,scrollOutItems;



    AppCompatButton nature,sky,space,dark,moon,forest;
    String url ="https://api.pexels.com/v1/curated/?page="+pageNumber+"&per_page=20";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapemain);





        recyclerView =findViewById(R.id.recylerView);
        nature=findViewById(R.id.nature);
        nature.setOnClickListener(this::onClick);

        sky=findViewById(R.id.sky);
        sky.setOnClickListener(this::onClick);

        space=findViewById(R.id.space);
        space.setOnClickListener(this::onClick);

        forest=findViewById(R.id.forest);
        forest.setOnClickListener(this::onClick);

        moon=findViewById(R.id.moon);
        moon.setOnClickListener(this::onClick);

        dark=findViewById(R.id.dark);
        dark.setOnClickListener(this::onClick);










        wallpapermodelList =new ArrayList<>();
        wallpaperAdapter =new WallpaperAdapter(this, wallpapermodelList);
        //set the adapter to the recyclerview

        recyclerView.setAdapter(wallpaperAdapter);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling =true;
                }
            }


            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currenItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currenItems + scrollOutItems == totalItems)){
                    //it has come  to an end
                    isScrolling =false;
                    fetchWallpaper();

                }


            }
        });
        fetchWallpaper();
        //finish();

    }



    public void onClick(View v){
        switch (v.getId()) {
            case R.id.nature:
                Intent intent = new Intent(this, NatureActivity.class);
                this.startActivity(intent);
                break;

            case R.id.dark:

                Intent intent_ent = new Intent(this, DarkActivity.class);
                this.startActivity(intent_ent);
                break;

            case R.id.moon:
                Intent intent_one = new Intent(this, MoonActivity.class);
                this.startActivity(intent_one);
                break;


            case R.id.space:
                Intent intent_two = new Intent(this, SpaceActivity.class);
                this.startActivity(intent_two);
                break;
            case R.id.sky:

                Intent intent_three = new Intent(this, SkyActivity.class);
                this.startActivity(intent_three);
                break;
            case R.id.forest:
                Intent intent_four = new Intent(this, ForestActivity.class);
                this.startActivity(intent_four);
                break;

        }


    }








    public void fetchWallpaper(){
        //after curated give the slash and write data about page too +page number + for the page next pages
        StringRequest request =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            //for the photo array in api
                            JSONArray  jsonArray =jsonObject.getJSONArray("photos");

                            int length =jsonArray.length();

                            for(int i=0;i<length;i++){
                                //to handel the response of the src
                                JSONObject object =jsonArray.getJSONObject(i);
                                //here we are storing the response of the id that we will get from the api
                                int id=object.getInt("id");

                                JSONObject objectImage =object.getJSONObject("src");
                                String originalUrl= objectImage.getString("original");
                                String mediumUrl= objectImage.getString("medium");

                                Wallpapermodel wallpapermodel =new Wallpapermodel(id,originalUrl,mediumUrl);
                                wallpapermodelList.add(wallpapermodel);



                            }
                            wallpaperAdapter.notifyDataSetChanged();
                            //here increment  the page number
                            pageNumber ++;






                        }catch(JSONException e){}



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //we have to do the authoriazation in key value pair so we using the map
                Map<String,String> params= new HashMap<>();
                params.put("Authorization","add your api key");  //add api key for authorization
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }

    //here we will inflate our menu with java file


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.nav_search){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText editText =new EditText(this);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alert.setMessage("Enter Category");
            alert.setTitle("Search Wallpaper");
            alert.setView(editText);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String query = editText.getText().toString().toLowerCase();

                    //make the nature dynamic
                    url ="https://api.pexels.com/v1/search?page="+pageNumber+"&per_page=20&query="+query;
                    wallpapermodelList.clear();
                    fetchWallpaper();



                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();

        }



        return super.onOptionsItemSelected(item);
    }
}