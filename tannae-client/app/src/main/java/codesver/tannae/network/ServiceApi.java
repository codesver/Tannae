package codesver.tannae.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("/account/checkId")
    Call<Boolean> checkId(@Query("id") String id);
}
