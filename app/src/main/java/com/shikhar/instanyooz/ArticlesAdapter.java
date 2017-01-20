package com.shikhar.instanyooz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

        private List<Article> listOfArticles;
        private Context mContext;

        public static class ArticleViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.thumbnail)ImageView thumbnail;
            @BindView(R.id.news_title)TextView title;
            @BindView(R.id.published_time)TextView publishedTime;
            @BindView(R.id.share_button)ImageView share;
            @BindView(R.id.expand_text_view)ExpandableTextView newsDescription;

            public ArticleViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        public ArticlesAdapter(List<Article> articlesList, Context context) {
            this.listOfArticles = articlesList;
            this.mContext = context;
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
            return new ArticleViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ArticleViewHolder holder, final int position) {

            //set thumbnail associated with the article
            Glide.with(mContext)
                    .load(listOfArticles.get(position).getUrlToImage())
                    .error(R.drawable.no_data)
                    .thumbnail(0.1f) // if you pass 0.1f as the parameter, Glide will display the original image reduced to 10% of the size. If the original image has 1000x1000 pixels, the thumbnail will have 100x100 pixels
                    .crossFade() //animation
                    .into(holder.thumbnail);

            //set news title
            holder.title.setText(listOfArticles.get(position).getTitle());

            //set time
            holder.publishedTime.setText(Util.manipulateDateFormat(listOfArticles.get(position).getPublishedAt()));

            //set news detail description in expandable textview
            SparseBooleanArray mTogglePositions = new SparseBooleanArray(); //don't declare it as a global variable
            holder.newsDescription.setText(listOfArticles.get(position).getDescription(), mTogglePositions , position);

            //open full article description in url when title is clicked
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri webpage = Uri.parse(listOfArticles.get(position).getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(intent);
                    }
                }
            });

            //open share Intent when share button is clicked
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "InstaNyooz");
                    String sAux = "\n"+ listOfArticles.get(position).getTitle() + "\n\n";
                    sAux = sAux + listOfArticles.get(position).getUrl() + "\n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    mContext.startActivity(Intent.createChooser(i, "choose one"));
                }
            });
        }

        @Override
        public int getItemCount() {
            return listOfArticles.size();
        }

        public void setDataAdapter(List<Article> newArticleList){
            this.listOfArticles = newArticleList;
        }
}
