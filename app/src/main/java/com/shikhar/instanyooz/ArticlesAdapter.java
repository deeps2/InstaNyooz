package com.shikhar.instanyooz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    //TODO context may be needed to open url link when clicked..try it in web view or whatever it is called
        private List<Article> listOfArticles;

        public static class ArticleViewHolder extends RecyclerView.ViewHolder {

            /*@BindView(R.id.thumbnail)*/ImageView thumbnail;
            /*@BindView(R.id.news_title)*/TextView title;
            /*@BindView(R.id.published_time)*/TextView publishedTime;
            /*@BindView(R.id.share_button)*/ImageView share;
            /*@BindView(R.id.expand_text_view)*/ExpandableTextView newsDescription;

            public ArticleViewHolder(View v) {
                super(v);
               // ButterKnife.bind(this, v);
                thumbnail = (ImageView)v.findViewById(R.id.thumbnail);
                title = (TextView) v.findViewById(R.id.news_title);
                publishedTime = (TextView) v.findViewById(R.id.published_time) ;
                share = (ImageView) v.findViewById(R.id.share_button);
                newsDescription = (ExpandableTextView)v.findViewById(R.id.expand_text_view);


                //TODO set onclick listener web view..butterknife way
            }
        }

        public ArticlesAdapter(List<Article> articlesList) {

            //TODO may be context will be needed when opening webview
            this.listOfArticles = articlesList;
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
            return new ArticleViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ArticleViewHolder holder, final int position) {
            //image

            holder.title.setText(listOfArticles.get(position).getTitle());
            holder.publishedTime.setText(listOfArticles.get(position).getPublishedAt());
            holder.newsDescription.setText(listOfArticles.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return listOfArticles.size();
        }

        public void setDataAdapter(List<Article>g){
            this.listOfArticles = g;
        }
}
