package com.example.gson;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Post> profiles;

    public MyAdapter(Context c , ArrayList<Post> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(profiles.get(position).getTitle());
        String t = "";
        try{
            t = (profiles.get(position).getText()).substring(0,50);
        }catch(Exception e){
            t = profiles.get(position).getText();
        }
        holder.text.setText(t);
        holder.likes.setText(profiles.get(position).getRating() + "");
        holder.author.setText(profiles.get(position).getAuthor());
        holder.comments.setText(profiles.get(position).getComments());
        Picasso.get().load(profiles.get(position).getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        View v;
        TextView title,text, likes, author, comments;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;

            title = (TextView) itemView.findViewById(R.id.title);
            likes = (TextView) itemView.findViewById(R.id.likes);
            text = (TextView) itemView.findViewById(R.id.text);
            image = (ImageView) itemView.findViewById(R.id.image);
            author = (TextView) itemView.findViewById(R.id.author);
            comments = (TextView) itemView.findViewById(R.id.comments);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PostPage.class);
                    //intent.putExtra("url",results.get(position).getUrl().toString());
                    context.startActivity(intent);
                }
            });
        }


    }
}
