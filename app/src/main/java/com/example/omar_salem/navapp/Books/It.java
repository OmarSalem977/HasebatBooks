package com.example.omar_salem.navapp.Books;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar_salem.navapp.Homescreen.Datamodel;
import com.example.omar_salem.navapp.Homescreen.Myadapter;
import com.example.omar_salem.navapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class It extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Datamodel> ITBooks;
    //--------API-------//
    private static String Constant_URL="https://www.googleapis.com/books/v1/volumes?q=";
    private static String Key="informationtechnology";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it);

        Toast.makeText(this,"Welcome in Is Activity ",Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.rec_id);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ItBooks");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        LoadBooksFromServer();

    }

    private void LoadBooksFromServer() {//the uses of Volley



        String URL = Constant_URL+Key;
        // Log.d(TAG, "URL: ".concat(URL));
        StringRequest request =new StringRequest(Request.Method.GET, URL, this, this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


    @Override
    public void onErrorResponse (VolleyError error){
        Toast.makeText(getApplicationContext(), "Error: Check your internet connection ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse (String response){
        ITBooks = new ArrayList<>();
        int id;
        String Obj;
        Toast.makeText(getApplicationContext(), "API Running ", Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("items");
            for (int i = 0; i < array.length(); i++) {

                JSONObject Object = array.getJSONObject(i);
                ITBooks.add(new Datamodel(
                        Object.getJSONObject("volumeInfo").getString("title"),
                        Object.getJSONObject("volumeInfo").getString("authors").replace("[", "").replace("\"", "").replace("]", ""),
                        Object.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail")

                ));


            }
            adapter = new Myadapter(ITBooks, getApplicationContext());
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
