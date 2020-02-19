package com.example.howlong.asyncTasks;

import com.example.howlong.AsyncCallback.GetTimeCallback;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetTime extends AsyncTask<String, Integer,String> {
    public GetTimeCallback getTimeCallback=null;

    @Override
    protected String doInBackground(String... strings) {


        String api_url="https://maps.googleapis.com/maps/api/directions/json?origin=40.741926,-74.062097&destination=40.7440691,-74.1814732&mode=transit&key=AIzaSyAEVPvx1HKpch7X413NP7L2JCc-BLsLG9c";
        URL url = null;
        try {
            url = new URL(String.format(api_url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection =
                null;
        try {
            connection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        try {
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader=null;
        try {
             reader= new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer json = new StringBuffer(1024);
        String tmp="";
        while(true) {
            try {
                if (!((tmp=reader.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            json.append(tmp).append("\n");


        }
        JSONObject data=null;
        try {
          data = new JSONObject(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray item_list=(data.has("routes")?data.getJSONArray("routes"):null);
            Log.d("app1","gotRoutes");
            Log.d("app1",item_list.toString());
//            System.out.println("got Roots");
            if (item_list!=null){
//                JSONArray item_book=item_list.getJSONArray("legs");
                JSONObject routesObject=item_list.getJSONObject(0);
                if (routesObject!=null){
                    JSONArray legs= routesObject.getJSONArray("legs");
                    JSONObject legsObject=legs.getJSONObject(0);
                    if (legsObject!=null){
                        JSONObject departureTime=legsObject.getJSONObject("departure_time");
                        return departureTime.get("text").toString();

                    }

                }

//                JSONArray maps_legs=(data.has("legs")?data.getJSONArray("legs"):null);
//

            }

        }
         catch (JSONException e) {
            e.printStackTrace();
        }

        return "No";

    }
    protected void onPostExecute(String data){

        super.onPostExecute(data);

        getTimeCallback.processFinish(data);
    }
}
