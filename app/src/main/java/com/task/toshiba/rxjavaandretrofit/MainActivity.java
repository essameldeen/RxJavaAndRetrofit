package com.task.toshiba.rxjavaandretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.task.toshiba.rxjavaandretrofit.Adapter.RecycleAdapter;
import com.task.toshiba.rxjavaandretrofit.Model.Post;
import com.task.toshiba.rxjavaandretrofit.WebServices.ApiInterface;
import com.task.toshiba.rxjavaandretrofit.WebServices.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private ApiInterface apiInterface;
   private RecycleAdapter recycleAdapter;
   private CompositeDisposable compositeDisposable = new CompositeDisposable();
   private List<Post> postss;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       postss = new ArrayList<>();
        // init retrofit
       Retrofit retrofit = RetrofitInstance.getInstance();
       apiInterface = retrofit.create(ApiInterface.class);
       //
       recyclerView = (RecyclerView)findViewById(R.id.rv_listPost);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setHasFixedSize(true);
       recycleAdapter = new RecycleAdapter(postss,this);
       recyclerView.setAdapter(recycleAdapter);
       
       GetDataFromServer();


    }

    private void GetDataFromServer() {
      compositeDisposable.add(apiInterface.getPost()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(new Consumer<List<Post>>() {
               @Override
               public void accept(List<Post> posts) throws Exception {
                 setData(posts);
               }
           }));

    }

    private void setData(List<Post> posts) {
          postss=posts;
          recycleAdapter = new RecycleAdapter(postss,this);
          recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    protected void onStop() {

       compositeDisposable.clear();
       super.onStop();
    }
}
