package com.shikhar.instanyooz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_news);

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);

// IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText("hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog. hello. a quick brown fox jumps over a lazy dog.");
    }
}
