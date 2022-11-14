package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.viewholder> {

    ArrayList<AudioModel> songsList;
    Context mycontext;


    public MusicAdapter(ArrayList<AudioModel> songsList, Context mycontext) {
        this.songsList = songsList;
        this.mycontext = mycontext;
    }

    @Override
    public viewholder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mycontext).inflate(R.layout.item_list,parent,false);
        return new MusicAdapter.viewholder(view);

    }

    @Override
    public void onBindViewHolder(viewholder holder, @SuppressLint("RecyclerView") int position) {

        AudioModel songData = songsList.get(position);
        holder.titletextview.setText(songData.getTitle());

       if(MyMediaPlayer.currentIndex == position){
            holder.titletextview.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.titletextview.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent i = new Intent(mycontext,MusicPlayerActivity.class);
                i.putExtra("LIST",songsList);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mycontext.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView iconmusic;
        TextView titletextview;
        RelativeLayout r;

        public viewholder( View itemView) {
            super(itemView);

            titletextview = itemView.findViewById(R.id.music_title_text);
            iconmusic = itemView.findViewById(R.id.icon_view);
            r = itemView.findViewById(R.id.relatativelayout);
        }
    }
}
