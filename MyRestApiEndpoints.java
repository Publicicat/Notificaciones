package com.publicicat.pgram;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyRestApiEndpoints {

    @FormUrlEncoded
    @POST(MyRestApiConstantes.KEY_POST_ID_TOKEN)
    Call<MyRestApiConstructor> registrarTokenID(@Field("token") String token, @Field("yogato") String yogato, @Field("otrogato") String otrogato);

    @GET(MyRestApiConstantes.KEY_TAP_PIC)
    Call<MyRestApiConstructor> toqueFoto(@Path("id") String id, @Path("otrogato") String otrogato);

}
