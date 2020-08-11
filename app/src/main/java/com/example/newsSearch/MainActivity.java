package com.example.newsSearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.newsSearch.R;


public class MainActivity extends AppCompatActivity {
    EditText word;
    Button pc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    word = (EditText) findViewById(R.id.searchWord);
    pc = (Button) findViewById(R.id.pageChange1);

    pc.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String st = word.getText().toString();
            Intent intent = new Intent(getApplicationContext(),pagetwo.class);
            intent.putExtra("tword",st);
            startActivity(intent);
        }
    });
    }
}
