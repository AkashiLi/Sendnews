package com.example.news.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.news.R;
import com.example.news.Utils;
import com.example.news.models.Article;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {
    // Variables
    private int limit = 10;
    private String TAG = "NewsListAdapter";
    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public NewsListAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
        //Sort By Date --> Make this into function later
        Collections.sort(this.articles, new CustomComparator());
        Collections.reverse(this.articles);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }
    // Binds Information to Cards
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Log.d("Adapter", "Creating Adapter " + position);
        Article model = articles.get(position);
        //Request Options
//        requestOptions = new RequestOptions();
//        requestOptions.centerCrop().placeholder(R.mipmap.ic_send_news).error(R.mipmap.ic_send_news);
//        requestOptions.placeholder(Utils.getRandomDrawbleColor());
//        requestOptions.error(Utils.getRandomDrawbleColor());
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
//        requestOptions.centerCrop();
        //Set Cards
        holder.title.setText("Title: " + model.getTitle());
        holder.desc.setText("ID: " + model.getId());
        holder.time.setText(model.getTime());
        holder.source.setText(model.getType());
        holder.publisher.setText("Temp");
        holder.author.setText("Temp");
        //Glide.with(context).load("https://i.imgur.com/bIRGzVO.jpg").into(holder.imageView);

        // Since There are No Images
        holder.imageView.setVisibility(View.GONE);
        holder.shadowImageView.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);

        //Test Output

        /*None of them have Images, If there are, UNCOMMENT*/
//        Glide.with(context)
//            .load("https://i.imgur.com/bIRGzVO.jpg")
//            .listener(new RequestListener<Drawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                    Log.d("Adapter", "onLoadFailed");
//                    holder.progressBar.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                    Log.d("Adapter", "onResourceReady is ready");
//                    holder.progressBar.setVisibility(View.GONE);
//                    return false;
//                }
//            })
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        //Limit Number of Articles
        if(articles.size() > limit) { return limit; }
        else { return articles.size(); }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        TextView title, desc, author, publisher, source, time;
        ImageView imageView, shadowImageView;
        CardView cardView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            //Find Views
            cardView = itemView.findViewById(R.id.article_cardView);
            title = itemView.findViewById(R.id.card_title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.card_author_textView);
            source = itemView.findViewById(R.id.card_source);
            publisher = itemView.findViewById(R.id.publishedAt);
            time = itemView.findViewById(R.id.card_time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.card_progressBar);
            shadowImageView = itemView.findViewById(R.id.card_shadow_bottom);

            this.onItemClickListener = onItemClickListener;
        }
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Item Clicked " + getAdapterPosition());
            this.onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }





    public class CustomComparator implements Comparator<Article> {
        @Override
        public int compare(Article o1, Article o2) {
            return o1.getTime().compareTo(o2.getTime());
        }

    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


}
