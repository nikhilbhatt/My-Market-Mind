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
import com.example.motivational.Activities.comment;
import com.example.motivational.R;
import com.example.motivational.recyclerviewcards.Examplequestion;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Questionadapter extends RecyclerView.Adapter<Questionadapter.ExampleQuestionviewholder> {

    public ArrayList<Examplequestion> m_ques_list;
    public Context mcontext;
    public static class ExampleQuestionviewholder extends RecyclerView.ViewHolder{
        public TextView mques,musername,mcurrenttime;
        public RelativeLayout mques_relativelayout;
        public CircleImageView mcircleimageview;
        public ExampleQuestionviewholder(@NonNull View itemView) {
            super(itemView);
            mques=itemView.findViewById(R.id.questioncard_ques);
            musername=itemView.findViewById(R.id.questioncard_username);
            mques_relativelayout=itemView.findViewById(R.id.question_relative);
            mcurrenttime=itemView.findViewById(R.id.questioncard_currenttime);
            mcircleimageview=itemView.findViewById(R.id.questioncard_circleimage);
        }

    }
    public Questionadapter(ArrayList<Examplequestion> ques_list,Context mcontext)
    {
        m_ques_list=ques_list;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public ExampleQuestionviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.questioncard,viewGroup,false);
        ExampleQuestionviewholder eqvh=new ExampleQuestionviewholder(v);
        return  eqvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleQuestionviewholder exampleQuestionviewholder, int i) {
           final Examplequestion currentitem=m_ques_list.get(i);
           exampleQuestionviewholder.mques.setText(currentitem.getMquestion());
           exampleQuestionviewholder.musername.setText(currentitem.getMusername());
           exampleQuestionviewholder.mcurrenttime.setText(currentitem.getMcurrenttime());
           if(!currentitem.getMimageuri().equals("null"))
                 Glide.with(mcontext).load(currentitem.getMimageuri()).into(exampleQuestionviewholder.mcircleimageview);
           else
               exampleQuestionviewholder.mcircleimageview.setImageResource(R.drawable.person);
           exampleQuestionviewholder.mques_relativelayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(mcontext, comment.class);
                   intent.putExtra("ques_username",currentitem.getMusername());
                   intent.putExtra("ques_comment",currentitem.getMquestion());
                   intent.putExtra("ques_document_id",currentitem.getMdocumentid());
                   intent.putExtra("ques_time",currentitem.getMcurrenttime());
                   intent.putExtra("ques_image_url",currentitem.getMimageuri());
                   mcontext.startActivity(intent);
               }
           });
    }

    @Override
    public int getItemCount() {
        return m_ques_list.size();
    }
}
