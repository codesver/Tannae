package codesver.tannae.network;

import java.util.List;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.dto.RegisterContentDTO;
import codesver.tannae.dto.ServiceRequestDTO;
import codesver.tannae.dto.FoundAccountDTO;
import codesver.tannae.dto.AccountDTO;
import codesver.tannae.dto.ServiceResponseDTO;
import codesver.tannae.dto.SignUpUserDTO;
import codesver.tannae.dto.StringDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {
    @POST("/users")
    Call<Boolean> postUser(@Body SignUpUserDTO dto);

    @GET("/users/account")
    Call<AccountDTO> login(@Query("id") String id, @Query("pw") String pw);

    @GET("/users/private")
    Call<FoundAccountDTO> findAccount(@Query("name") String name, @Query("rrn") String rrn);

    @GET("/users/duplicate-id")
    Call<Boolean> duplicateId(@Query("id") String id);

    @GET("/users/duplicate-private")
    Call<Boolean> duplicatePrivate(@Query("name") String name, @Query("rrn") String rrn);

    @POST("/users/{usn}/point")
    Call<Integer> charge(@Path("usn") Integer usn, @Body Integer point);

    @POST("/service/request")
    Call<ServiceResponseDTO> request(@Body ServiceRequestDTO dto);

    @POST("/vehicles/{vsn}/users/score")
    Call<Boolean> rate(@Query("vsn") Integer vsn, @Body Float score);

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

    @POST("/contents/{csn}/question")
    Call<Boolean> postQuestion(@Path("csn") Integer csn, @Body StringDTO dto);

    @DELETE("/contents/{csn}")
    Call<Boolean> deleteContent(@Path("csn") Integer csn);
}
