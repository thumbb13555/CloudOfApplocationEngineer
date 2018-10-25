package com.example.noahliu.cloudofapplocationengineer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageButton btLogin,btdisplay,btBrowser;
    public TextView tvStatus,tvpos,tvMapPosition;
    private static final String SERVERIP = "120.109.18.143";
    private static final String PORTNO = "80";
    private static final String PHPSCRIPT = "/joomla/IotProject/showOnAPP.php";
    public static final String MY_JSON = "MY_JSON";
    private String help = "help";
    private TextView smsContent;
    private static String sendhelp;
    private SoundPool spool;
    private int sourceid;
    private final static String MSG_RECEIVED =
            "android.provider.Telephony.SMS_RECEIVED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //************************************************************
        //聲音酷的最大音頻流數目為10，聲音品質為5
        spool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        sourceid = spool.load(this, R.raw.help, 1);
        //************************************************************
        btLogin = (ImageButton) findViewById(R.id.btLogin);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btdisplay = (ImageButton) findViewById(R.id.btdisplay);
        btBrowser = (ImageButton) findViewById(R.id.btBrowser);
        tvpos = (TextView) findViewById(R.id.tvpos);
        tvMapPosition = (TextView) findViewById(R.id.mapPosition);
        Log.v("Bt","here1");
        //---------------------set default function & Login function----------------
        try {
            Intent intent1 = getIntent();
            String str = intent1.getStringExtra("LoginOK");
            tvStatus.setText(str);
            Log.v("Bt", str);
            if(tvStatus.getText()!=("未登入")){
                tvStatus.setTextColor(this.getResources().getColor(R.color.white));
            }

            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tvStatus.getText().equals("已登入"))
                    {
                        new  AlertDialog.Builder(MainActivity.this).setTitle("確認")
                                .setMessage("請問是否要切換使用者或重新登入?")
                                .setPositiveButton("切換使用者",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                tvStatus.setText("未登入");
                                                tvStatus.setTextColor(getResources().getColor(R.color.gray));
                                                tvpos.setText("未選擇");
                                                tvpos.setTextColor(getResources().getColor(R.color.gray));
                                                Toast.makeText(getApplicationContext(), "請重新登入，謝謝!" ,Toast.LENGTH_LONG).show();
                                            }
                                        }).setNegativeButton("取消",null).show();
                    }
                }
            });
        }catch (Exception e)
        {
            Log.v("Bt","here4");
            tvStatus.setText("未登入");
            tvStatus.setTextColor(this.getResources().getColor(R.color.gray));
            Log.v("Bt","here2");
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tvStatus.getText().equals("已登入"))
                    {
                        new  AlertDialog.Builder(MainActivity.this).setTitle("確認")
                                .setMessage("請問是否要切換使用者或重新登入?")
                                .setPositiveButton("切換使用者",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                tvStatus.setText("未登入");
                                                tvStatus.setTextColor(getResources().getColor(R.color.gray));
                                                tvpos.setText("未選擇");
                                                tvpos.setTextColor(getResources().getColor(R.color.gray));
                                                Toast.makeText(getApplicationContext(), "請重新登入，謝謝!" ,Toast.LENGTH_LONG).show();
                                            }
                                        }).setNegativeButton("取消",null).show();
                    }else
                    {
                        Intent goLogin = new Intent(MainActivity.this, login.class);
                        startActivity(goLogin);
                        finish();
                    }
                }
            });//end btLogin
        }
        try {
            Intent intent = getIntent();
            int i = intent.getIntExtra("mapPosition", 0);
            TextView mapPosition = (TextView) findViewById(R.id.mapPosition);
            mapPosition.setText(ParseJSON.positionArray[i]);
            Log.v("Bt",ParseJSON.positionArray[i]);
            tvStatus.setText("已登入");
            tvStatus.setTextColor(this.getResources().getColor(R.color.white));

            if (tvMapPosition.getText() != "unsearch")
            {
                tvpos.setText("已選擇");
                tvpos.setTextColor(this.getResources().getColor(R.color.white));
            }
            Toast.makeText(getApplicationContext(), "已選擇出工地點為:" + ParseJSON.positionArray[i], Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tvStatus.getText().equals("已登入"))
                    {
                        new  AlertDialog.Builder(MainActivity.this).setTitle("確認")
                                .setMessage("請問是否要切換使用者或重新登入?")
                                .setPositiveButton("切換使用者",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                tvStatus.setText("未登入");
                                                tvStatus.setTextColor(getResources().getColor(R.color.gray));
                                                Toast.makeText(getApplicationContext(), "請重新登入，謝謝!" ,Toast.LENGTH_LONG).show();
                                            }
                                        }).setNegativeButton("取消",null).show();
                    }else
                    {
                        Intent goLogin = new Intent(MainActivity.this, login.class);
                        startActivity(goLogin);
                        finish();
                    }
                }
            });//end btLogin
        }

    }
    //---------------End Login function---------------------
    //---------------set quit function----------------------
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            new  AlertDialog.Builder(MainActivity.this).setTitle("退出")
                    .setMessage("請問是否關閉程式?")
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish(); // 離開程式
                                    tvpos.setTextColor(getResources().getColor(R.color.gray));
                                    tvpos.setText("未選擇");
                                    System.exit(0);
                                }
                            }).setNegativeButton("否",null).show();
        }
        return false;
    }
    //-----------End quit function---------------------
    //-----------set Browser function------------------
    public void btBrowser_Click(View view){
        if(tvStatus.getText().equals("未登入"))
        {
            Toast.makeText(this,"麻煩請先登入哦!",Toast.LENGTH_SHORT).show();
            Log.v("Bt","here1");
        }
        else if(tvpos.getText().equals("未選擇")){
            Toast.makeText(this,"麻煩您先選擇地點哦!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent goBrowser = new Intent(MainActivity.this, display.class);
            startActivity(goBrowser);
            finish();
        }
    }//end btBroswer
    //--------------End Browser function-------------------
    //--------------set map function-----------------------
    public  void btmap_onClick(View view){
        if(tvpos.getText().equals("未選擇"))
        {
            Toast.makeText(this,"請點選出工資訊選擇需要前往的地點",Toast.LENGTH_SHORT).show();
            Log.v("Bt","here1");
        }
        else
        {
            Uri uri = Uri.parse("http://maps.google.com/maps? f=d&saddr=&daddr="+tvMapPosition.getText());
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
            //Log.v("Bt","可以選擇導航了!");
        }
    }//end map_onClick
    //-------------------End map function------------------
    //-------------------set Connect to Sever--------------
    public void connectToServer(View v){
        if(tvStatus.getText().equals("未登入"))
        {
            Toast.makeText(this,"請先登入",Toast.LENGTH_SHORT).show();
            Log.v("Bt","還不能使用出工資訊");
        }else {

            String strPath = "http://" + SERVERIP + ":" + PORTNO + "/" + PHPSCRIPT;
            getJSON(strPath);
            finish();
        }
    }//end connectToServer
    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,
                        "Please Wait...",null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Intent intent = new Intent(MainActivity.this, ShowResult.class);
                intent.putExtra(MY_JSON, s);
                startActivity(intent);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }//end getJSON
    //--------------------------End connect to sever-----------------
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public Context context;

        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals(MSG_RECEIVED)){
                Bundle msg = intent.getExtras();
                Object[] messages = (Object[]) msg.get("pdus");
                SmsMessage sms = SmsMessage.createFromPdu((byte[])messages[0]);

                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("From:"+sms.getDisplayOriginatingAddress()+"\n");
                strBuilder.append("text:"+sms.getMessageBody());
                smsContent.setText(strBuilder);
                sendhelp = sms.getMessageBody();
                Log.v("BT","help");
                Log.v("BT",sms.getDisplayMessageBody());
                Log.v("BT","BT");
                Log.v("BT",sendhelp);

                //***************************************

                switch (sms.getMessageBody()){
                    case "help":
                        playsud(5);
                        Log.v("BT","OK");
                        break;
                }

                //***************************************

            }/*stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spool.pause(sourceid);
                }
            });*/
        }
    };

    //*****************************************************************************************

    public void playsud(int repeatTime){
        AudioManager am = (AudioManager) getApplicationContext()
                .getSystemService(Context.AUDIO_SERVICE);
        //獲取最大音量

        float audMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);

        //獲取目前音量
        float audCurrentVolumn = am.getStreamMaxVolume(AudioManager.STREAM_ALARM);
        //左右聲道值範圍為0.0-1.0
        float volRatio = audCurrentVolumn / audMaxVolumn;

        //播放音頻，左右音量，設置優先級，重播次數，速率(速率最低0.5，最高為2，1代表正常速度)
        spool.play(sourceid,volRatio,volRatio,1,repeatTime,1);
    }

    //--------------------------End alarm & Message Receive function------------
}
