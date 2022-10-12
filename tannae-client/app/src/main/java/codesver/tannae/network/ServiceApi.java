package codesver.tannae.network;

import java.util.List;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.dto.LoginDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.dto.SignUpUserDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("/users/duplicate-id")
    Call<Boolean> duplicateId(@Query("id") String id);

    @GET("/users/duplicate-private")
    Call<Boolean> duplicatePrivate(@Query("name") String name, @Query("rrn") String rrn);

    @POST("/users/sign-up")
    Call<Boolean> signUp(@Body SignUpUserDTO dto);

    @GET("/users/find-account")
    Call<FoundAccountDTO> findAccount(@Query("name") String name, @Query("rrn") String rrn);

    @GET("/users/login")
    Call<LoginDTO> login(@Query("id") String id, @Query("pw") String pw);

    @PATCH("/users/{usn}/charge")
    Call<Integer> charge(@Path("usn") Integer usn, @Query("point") Integer point);

    @PATCH("/users/rate")
    Call<Boolean> rate(@Query("vsn") Integer vsn, @Query("rate") Float rate);

    @POST("/service/request")
    Call<ServiceResponseDTO> request(@Body ServiceRequestDTO dto);

    @PATCH("/vehicles/{vsn}")
    Call<Boolean> switchRun(@Path("vsn") Integer vsn, @Query("running") Boolean running);

    @GET("/histories")
    Call<List<HistoryDTO>> getHistories(@Query("usn") Integer usn);

    @GET("/histories/{hsn}")
    Call<HistoryDTO> getReceiptWithHsn(@Path("hsn") Integer hsn);

    @GET("/histories/users")
    Call<HistoryDTO> getReceiptWithUsn(@Query("usn") Integer usn);

    @GET("/contents")
    Call<List<ContentDTO>> getContents();

    @POST("/contents")
    Call<Boolean> registerContent(@Body RegisterContentDTO dto);

    @GET("/contents/{csn}")
    Call<ContentDTO> getContent(@Path("csn") Integer csn);
}
