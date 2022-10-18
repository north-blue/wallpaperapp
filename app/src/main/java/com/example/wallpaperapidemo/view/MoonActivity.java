package com.example.wallpaperapidemo.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wallpaperapidemo.R;
import com.example.wallpaperapidemo.WallpaperAdapter;
import com.example.wallpaperapidemo.Wallpapermodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoonActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    WallpaperAdapter wallpaperAdapter;
    List<Wallpapermodel> wallpapermodelList;
    int pageNumber=1;

    Boolean isScrolling=false;
    int currenItems,totalItems,scrollOutItems;

    GridLayoutManager gridLayoutManager;

    String url ="https://api.pexels.com/v1/curated/?page="+pageNumber+"&per_page=20";

    String searchUrl ="https://api.pexels.com/v1/search?page="+pageNumber+"&per_page=20&query=moon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);

        recyclerView =findViewById(R.id.recylerViewMoon);


        wallpapermodelList =new ArrayList<>();
        wallpaperAdapter =new WallpaperAdapter(this, wallpapermodelList);
        //set the adapter to the recyclerview

        recyclerView.setAdapter(wallpaperAdapter);
        gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);


        recyclerData();
        fetchWallpaper();
    }



    private void recyclerData(){
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
    }






    private void fetchWallpaper(){

        StringRequest request =new StringRequest(Request.Method.GET, searchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray =jsonObject.getJSONArray("photos");

                            int length =jsonArray.length();

                            for(int i=0;i<length;i++){

                                JSONObject object =jsonArray.getJSONObject(i);

                                int id=object.getInt("id");

                                JSONObject objectImage =object.getJSONObject("src");
                                String originalUrl= objectImage.getString("original");
                                String mediumUrl= objectImage.getString("medium");

                                Wallpapermodel wallpapermodel =new Wallpapermodel(id,originalUrl,mediumUrl);
                                wallpapermodelList.add(wallpapermodel);



                            }
                            wallpaperAdapter.notifyDataSetChanged();

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

                Map<String,String> params= new HashMap<>();
                params.put("Authorization","563492ad6f91700001000001aea2af4886b8453e813570733825eaeb");
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);


    }


}