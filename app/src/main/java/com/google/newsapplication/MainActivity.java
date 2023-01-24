package com.google.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRvAdapter.CategoryClickInterface {

    private RecyclerView newsRv,categoryRv;
    private ProgressBar loadImgPB;
    private ArrayList<Articles>articlesArrayList;
    private ArrayList<CategoryRvModel> categoryRvModelArrayList;
    private CategoryRvAdapter categoryRvAdapter;
    private NewsRvAdapter newsRvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRv = findViewById(R.id.idRvNews);
        categoryRv=findViewById(R.id.idRvCategories);
        loadImgPB=findViewById(R.id.progress);

        articlesArrayList = new ArrayList<>();
        categoryRvModelArrayList = new ArrayList<>();
        newsRvAdapter = new NewsRvAdapter(articlesArrayList,this);
        categoryRvAdapter = new CategoryRvAdapter(categoryRvModelArrayList,this,this::onCategoryClick);
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        newsRv.setAdapter(newsRvAdapter);
        categoryRv.setAdapter(categoryRvAdapter);

        getCategories();
        getNews("All");
        newsRvAdapter.notifyDataSetChanged();
    }

    private void getCategories()
    {
        categoryRvModelArrayList.add(new CategoryRvModel("All","https://images.unsplash.com/photo-1504711434969-e33886168f5c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8bmV3c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("Technology","https://images.unsplash.com/photo-1519389950473-47ba0277781c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("Science","https://images.unsplash.com/photo-1614935151651-0bea6508db6b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("Sports","https://images.unsplash.com/photo-1614935151651-0bea6508db6b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("General","https://plus.unsplash.com/premium_photo-1661757194630-b83ff50070f1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("Business","https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzd8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModelArrayList.add(new CategoryRvModel("Entertainment","https://images.unsplash.com/photo-1510511233900-1982d92bd835?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fGVudGVydGFpbm1lbnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60 "));
        categoryRvModelArrayList.add(new CategoryRvModel("Health","https://plus.unsplash.com/premium_photo-1661757194630-b83ff50070f1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60 "));
         categoryRvAdapter.notifyDataSetChanged();

    }

    private void getNews(String category)
    {
        loadImgPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category" + category +  "&apiKey=e48a1450cbe54e7f955994b2cca1eb2d";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=e48a1450cbe54e7f955994b2cca1eb2d";
        String BASE_URL = "https://newsapi.org/";
        Retrofit  retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel>call;
        if(category.equals("All"))
        {
            call = retrofitAPI.getAllNews(url);
        }
        else
        {
            call=retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadImgPB.setVisibility(View.GONE);
                ArrayList<Articles>articles = newsModel.getArticles();
                for(int i=0; i<articles.size(); i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
                }

                newsRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"fail to get news",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
String category = categoryRvModelArrayList.get(position).getCategory();
        getNews(category);


    }
}