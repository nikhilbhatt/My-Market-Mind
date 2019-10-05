package com.example.motivational.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.motivational.recyclerviewcards.Examplecard;
import com.example.motivational.R;

import java.util.ArrayList;

public class Adapterclass extends RecyclerView.Adapter<Adapterclass.Exampleviewholder> {
    private ArrayList<Examplecard> m_list_item;
    private Context context;
    public static class Exampleviewholder extends RecyclerView.ViewHolder{
          public TextView mtextview;
          public ImageView mimage;
          public Exampleviewholder(@NonNull View itemView) {
              super(itemView);
              mtextview=itemView.findViewById(R.id.samplecard_text);
              mimage=itemView.findViewById(R.id.samplecard_share);
         }
     }
     public  Adapterclass(ArrayList<Examplecard> list_item,Context context){
         m_list_item=list_item;
         this.context=context;
     }

    @NonNull
    @Override
    public Exampleviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.samplecard,viewGroup,false);
        Exampleviewholder evh=new Exampleviewholder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull Exampleviewholder exampleviewholder, int i) {
          final Examplecard currentitem=m_list_item.get(i);
          exampleviewholder.mtextview.setText(currentitem.getMsample());
          exampleviewholder.mimage.setImageResource(currentitem.getMimage());
          exampleviewholder.mimage.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent sendIntent = new Intent();
                  sendIntent.setAction(Intent.ACTION_SEND);
                  sendIntent.putExtra(Intent.EXTRA_TEXT, currentitem.getMsample()+"\n\n\t\t~My Market Mind");
                  sendIntent.setType("text/plain");

                  Intent shareIntent = Intent.createChooser(sendIntent, null);
                  context.startActivity(shareIntent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return m_list_item.size();
    }

}
