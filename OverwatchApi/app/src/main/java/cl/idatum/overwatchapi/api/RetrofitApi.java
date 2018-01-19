package cl.idatum.overwatchapi.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApi {


    @GET("api/v1/hero")
    Call<JsonObject> getHeros();


}
