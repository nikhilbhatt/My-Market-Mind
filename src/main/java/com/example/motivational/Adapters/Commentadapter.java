package com.example.motivational.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.motivational.R;
import com.example.motivational.recyclerviewcards.Examplecomment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Commentadapter extends RecyclerView.Adapter<Commentadapter.Examplecommentholder>  {
    private ArrayList<Examplecomment> m_list_comment;
    private Context mcontext;
    public static class Examplecommentholder extends  RecyclerView.ViewHolder{

        public TextView comment_user_name,comment_reply,comment_current_time;
        public CircleImageView comment_mcircleimage;
        public Examplecommentholder(@NonNull View itemView) {
            super(itemView);
            comment_user_name=itemView.findViewById(R.id.commentcard_user_name);
            comment_reply=itemView.findViewById(R.id.commentcard_reply);
            comment_current_time=itemView.findViewById(R.id.commentcard_time);
            comment_mcircleimage=itemView.findViewById(R.id.commentcard_image);
        }
    }

    public Commentadapter(ArrayList<Examplecomment> m_list_comment,Context mcontext)
    {
        this.m_list_comment=m_list_comment;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Examplecommentholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.commentcard,viewGroup,false);
        Examplecommentholder ech=new Examplecommentholder(v);
        return  ech;
    }

    @Override
    public void onBindViewHolder(@NonNull Examplecommentholder examplecommentholder, int i) {
      Examplecomment currentitem=m_list_comment.get(i);
      examplecommentholder.comment_user_name.setText(currentitem.getMusername());
      examplecommentholder.comment_reply.setText(currentitem.getMcomment());
      examplecommentholder.comment_current_time.setText(currentitem.getMtime());
      if(currentitem.getMimageuri().equals("null"))
          examplecommentholder.comment_mcircleimage.setImageResource(R.drawable.person);
      else
       Glide.with(mcontext).load(currentitem.getMimageuri()).into(examplecommentholder.comment_mcircleimage);
    }

    @Override
    public int getItemCount() {
        return m_list_comment.size();
    }
}
