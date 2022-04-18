package com.example.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.data.Data;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class MainActivity extends AppCompatActivity {

    private final SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss");
    private RecyclerView recyclerView;
    private final List<Data> dataList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();
    }

    private void initData() {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("api.yshyqxx.com")
                .addPathSegment("api")
                .addPathSegment("v1")
                .addPathSegment("aggTrades")
                .addQueryParameter("symbol", "BTCUSDT")
                .addQueryParameter("limit", "40")
                .build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();
                parseInitJson(result);
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }
        });
    }

    public void getData() {
        String mWbSocketUrl = "wss://stream.yshyqxx.com/ws/btcusdt@aggTrade\n";
        OkHttpClient mClient = new OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(mWbSocketUrl)
                .build();
        WebSocket mWebSocket = mClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                parseContinueJson(text);
            }
        });
    }

    private void parseInitJson(String json) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Data data = new Data(sdFormat.format(jsonObject.get("T").getAsLong()), jsonObject.get("p").getAsString(), jsonObject.get("q").getAsString(),
                    jsonObject.get("m").getAsBoolean());
            dataList.add(data);
        }
        initAdapter(dataList);
    }

    private void parseContinueJson(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        Data data = new Data(sdFormat.format(jsonObject.get("E").getAsLong()), jsonObject.get("p").getAsString(), jsonObject.get("q").getAsString(),
                jsonObject.get("m").getAsBoolean());
        dataList.add(0, data);
        dataList.remove(40);
        myAdapter.notifyDataSetChanged();
    }

    private void initAdapter(List<Data> dataList) {
        myAdapter = new MyAdapter(dataList);
        recyclerView.setAdapter(myAdapter);
        getData();
    }

}