package codesver.tannae.network;

import codesver.tannae.dto.user.FoundAccountDTO;
import codesver.tannae.dto.user.SignUpUserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("/users/check-id")
    Call<Boolean> checkId(@Query("id") String id);

    @GET("/users/check-private")
    Call<Boolean> checkPrivate(@Query("name") String name, @Query("rrn") String rrn);

    @POST("/users/sign-up")
    Call<Boolean> signUp(@Body SignUpUserDTO dto);

    @GET("/users/find-account")
    Call<FoundAccountDTO> findAccount(@Query("name") String name, @Query("rrn") String rrn);
}
