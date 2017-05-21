package com.ks.hihi.haha.request;

import com.ks.hihi.haha.items.BaseObj;
import com.ks.hihi.haha.items.Users;
import com.ks.hihi.haha.utill.Config;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by jo on 2017-05-05.
 */

public interface RequestOne {

    interface selectOne {
        @Headers({
                "Accept: application/json",
        })
        @GET("/rest/{gubun}/list/id/{id}")
        Call<BaseObj> createTask(@Path("gubun") String gubun, @Path("id") String id);

        @GET("/rest/{gubun}/id/{id}")
        Call<Users> getUser(@Path("gubun") String gubun, @Path("id") String id);

        @POST("/rest/{gubun}/insert")
        Call<Users> insertUser(@Path("gubun") String gubun, @Body Users user);

        @PUT("/rest/{gubun}/id/{id}")
        Call<Users> updateUser(@Path("gubun") String gubun, @Path("id") String id, @Body Users user);

    }

    Retrofit retrofitHttp = new Retrofit.Builder()
            .baseUrl(Config.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Retrofit retrofitHttps = new Retrofit.Builder()
            .baseUrl(Config.BASE_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
