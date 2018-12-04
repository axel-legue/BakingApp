package com.legue.axel.bakingapp.retrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface BakingAppService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<ResponseBody> getAllRecipes();
}
