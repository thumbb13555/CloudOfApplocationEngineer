package com.example.noahliu.cloudofapplocationengineer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] positionArray;
    public static String[] worktypeArray;
    public static String[] datatimeArray;
    //public static String[] ageArray;
    //以下為制定索引資料，資料內容須與資料表相符
    public static final String KEY_position="position";
    public static final String KEY_worktype="worktype";
    public static final String KEY_datatime="datatime";
    //public static final String KEY_AGE="age";

    private String json;


    public ParseJSON(String json)    {
        this.json = json;
    }

    protected void parseJSON(){
        try{
            JSONArray jArray = new JSONArray(json);
            positionArray = new String[jArray.length()];
            worktypeArray = new String[jArray.length()];
            datatimeArray = new String[jArray.length()];
            //ageArray = new String[jArray.length()];


            for (int i =0; i < jArray.length(); i++){
                JSONObject jo = jArray.getJSONObject(i);

                positionArray[i] = jo.getString(KEY_position);
                worktypeArray[i] = jo.getString(KEY_worktype);
                datatimeArray[i] = jo.getString(KEY_datatime);
                //ageArray[i] = jo.getString(KEY_AGE);


            }
        }catch (JSONException e){
            e.printStackTrace();
        }


    }
}
