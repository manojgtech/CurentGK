package com.example.curentgk;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<Blog> blogList;

    //the context object
    private Context mCtx;

    public ListViewAdapter(Context mCtx, List<Blog> blogList) {
        this.mCtx = mCtx;
        this.blogList = blogList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setTag(blogList.get(position));
        Blog blog = blogList.get(position);

        holder.pName.setText(blog.getBlogTitle());

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView pName;


        public ViewHolder(View itemView) {
            super(itemView);

            pName = (TextView) itemView.findViewById(R.id.blogtitletext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Blog cpu = (Blog) view.getTag();
             String  postID = cpu.getPostId();

              // Toast.makeText(getApplicationContext(), cpu.getPostId(), Toast.LENGTH_LONG).show();
               Intent blogin=new Intent(mCtx,Blogpost.class);
               blogin.putExtra("postid", postID);
               blogin.putExtra("type", 1);
               mCtx.startActivity(blogin);

                }
            });

        }
    }

}