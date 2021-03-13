package com.example.barcodescannerv3;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RESTMethods {
    OkHttpClient client = new OkHttpClient();
    private String port = "8080";

    public void request(String EAN, ItemViewModel model, String ip){
        Request request = new Request.Builder()
                .url("http://" + ip + ":"+ port +"/get")
                .addHeader("EAN", EAN)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Gson gson = new Gson();
                Item item = new Item();
                item = gson.fromJson(myResponse, Item.class);
                model.getMutableItem().postValue(item);
            }
        });

    }
    public void put(Item item, String ip){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        String json = gson.toJson(item);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://" + ip + ":"+ port +"/put")
                .put(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
    public void ping(String ip, BooleanViewModel aBoolean) throws IOException {
        Request request = new Request.Builder()
                .url("http://" + ip + ":"+ port +"/ping")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
                aBoolean.getMutableBoolean().postValue(false);
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                aBoolean.getMutableBoolean().postValue(response.isSuccessful());
            }
        });

    }
}
