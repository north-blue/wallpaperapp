package com.example.wallpaperapidemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WallpaperAdapter extends  RecyclerView.Adapter<WallpaperViewHolder> {
    private Context context;
    private List<Wallpapermodel> wallpapermodelList;
    //when we use recycler view we have to use wallpaper adapter

    public WallpaperAdapter(Context context, List<Wallpapermodel> wallpapermodelList) {
        this.context = context;
        this.wallpapermodelList = wallpapermodelList;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);

        return new WallpaperViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //here our those images that comes is shown in here
        Glide.with(context).load(wallpapermodelList.get(position).getMediumUrl()).into(holder.imageView);
        //so when you will click on the image it will show on the new screen

        holder.imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,FullScreenWallpaper.class)
                .putExtra("originalUrl",wallpapermodelList.get(position).getOriginalUrl()));

            }
        });


    }


    @Override
    public int getItemCount() {
        return wallpapermodelList.size();
    }



}

class WallpaperViewHolder extends RecyclerView.ViewHolder{

    //we have created the view holder because we have to create separate layout
    //layout = layout resuorce file
    ImageView imageView;

    public WallpaperViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=itemView.findViewById(R.id.imageViewItem);


    }
}

