package com.example.newsSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.newsSearch.R;

public class pagethree extends AppCompatActivity {

    TextView keytext, summtext, linktext;
    static String dbsumm = null;
    String selectDBSum = null;
    String keydb, linkdb,textsummdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagethree);

        keytext = (TextView) findViewById(R.id.tv_keyword);
        summtext = (TextView) findViewById(R.id.tv_sum);
        linktext = (TextView) findViewById(R.id.tv_link);

        Intent intent = getIntent();
        keydb = intent.getStringExtra("keyword");
        linkdb = intent.getStringExtra("link");
        textsummdb = intent.getStringExtra("summ");

        keytext.setText(keydb);
        summtext.setText(textsummdb);
        summtext.setMovementMethod(new ScrollingMovementMethod());
        linktext.setText(linkdb);


    }
}
