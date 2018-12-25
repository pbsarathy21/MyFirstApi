package in.parthabobbysarathy.myfirstapi.utils;

import in.parthabobbysarathy.myfirstapi.pojo.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("createuser")
    Call<RegisterResponse> register(
            @Field("name") String name,
            @Field("school") String school,
            @Field("email") String email,
            @Field("password") String password
    );

}
