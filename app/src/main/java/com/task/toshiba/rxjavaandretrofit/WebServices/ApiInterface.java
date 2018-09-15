package com.task.toshiba.rxjavaandretrofit.WebServices;

import com.task.toshiba.rxjavaandretrofit.Model.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("posts")
    Observable<List<Post>> getPost();
}
