package com.example.newsSearch;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newsSearch.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class pagetwo extends AppCompatActivity {
    TextView title;
    ListView lv;
    ArrayList<Myitem> data;
    myAdapter adap;
    String selectDBSum = null;
    String dbLink, dbKeyWord;
    static boolean calledAlready = false;

    class Myitem {
        String keyword;
        String link;
        String textsumm;
//        String tesum;

        //        Myitem(String keyword, String link,String tesum) {
//            this.keyword = keyword;
//            this.link = link;
//            this.tesum = tesum;
//        }
        Myitem(String keyword, String link, String textsumm) {
            this.keyword = keyword;
            this.link = link;
            this.textsumm = textsumm;
        }

    }

    class myAdapter extends BaseAdapter {
        Context con;


        myAdapter(Context con) {
            this.con = con;

        }

        @Override
        public int getCount() {

            return data.size();
        }

        @Override
        public Myitem getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = ((LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item, parent, false);
            }

            TextView key = (TextView) convertView.findViewById(R.id.keyword);
            key.setText(data.get(position).keyword);
            TextView link = (TextView) convertView.findViewById(R.id.link);
            link.setText(data.get(position).link);
            TextView tui = (TextView) convertView.findViewById(R.id.tw);
            tui.setText(data.get(position).textsumm);
            tui.setVisibility(View.GONE);

            return convertView;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagetwo);
        if (!calledAlready) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true); // 다른 인스턴스보다 먼저 실행되어야 한다.
            calledAlready = true;

        }
        title = (TextView) findViewById(R.id.title);
        lv = (ListView) findViewById(R.id.listview);


        Intent it = getIntent();
        final String importantWord = it.getExtras().getString("tword");
        title.setText(importantWord);

        data = new ArrayList<Myitem>();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("data");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int i, q, r = 0;
                String dbli = null;
                String dbsumm = null;
                for (DataSnapshot fineSnapshot : dataSnapshot.getChildren()) {
                    String st = fineSnapshot.getValue().toString();
                    i = st.indexOf("{") + 1;
                    q = st.indexOf("=");
                    String pandan = st.substring(i, q);

                    if (pandan.trim().equalsIgnoreCase("textsum")) {
                        i = st.indexOf("=") + 1;
                        q = st.indexOf("}");

                        dbsumm = st.substring(i, q);
                    }

                    if (pandan.trim().equalsIgnoreCase("link")) {
                        i = st.indexOf("=") + 1;
                        q = st.indexOf("}");
                        dbli = st.substring(i, q).trim();

                    }

                    if (pandan.trim().equalsIgnoreCase("keyword")) {
                        i = st.indexOf("[") + 1;
                        q = st.indexOf("]");
                        String dbkey = st.substring(i, q);
                        String[] array = dbkey.split(",");
                        for (int num = 0; num < 5; num++) {
                            if (array[num].trim().equals(importantWord.trim())) {
                                dbKeyWord = dbkey;
                                dbLink = dbli;
                                selectDBSum = dbsumm;
                                data.add(new Myitem(dbkey, dbli,dbsumm));
                            }
                        }

                    }


                }
                adap.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };


        myRef2.addChildEventListener(childEventListener);
        adap = new myAdapter(this);
        lv.setAdapter(adap);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), pagethree.class);
                String keykey = ((Myitem) data.get(position)).keyword;
                String linklink = ((Myitem) data.get(position)).link;
                String summsumm = ((Myitem) data.get(position)).textsumm;
                intent.putExtra("keyword", keykey);
                intent.putExtra("link", linklink);
                intent.putExtra("summ", summsumm);
                startActivity(intent);
            }
        });


    }
}
