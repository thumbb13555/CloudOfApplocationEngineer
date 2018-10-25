package com.example.noahliu.cloudofapplocationengineer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShowResult extends AppCompatActivity {
    private ListView lv;

    private String myJSONString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        lv = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        myJSONString = intent.getStringExtra(MainActivity.MY_JSON);

        showJSON(myJSONString);
    }

    private void showJSON(String json) {

        ParseJSON pj = new ParseJSON(json);

        pj.parseJSON();

        final CustomList cl = new CustomList(this,ParseJSON.positionArray,
                ParseJSON.worktypeArray, ParseJSON.datatimeArray/*, ParseJSON.ageArray*/);
        lv.setAdapter(cl);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(getApplicationContext(),"Click id="+positionArray[i],Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Click id="+i,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowResult.this, MainActivity.class);
                intent.putExtra("mapPosition", i);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ShowResult.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
