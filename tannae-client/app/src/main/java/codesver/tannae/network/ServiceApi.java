package codesver.tannae.network;

import java.util.List;

import codesver.tannae.dto.ContentDTO;
import codesver.tannae.dto.ContentFaqDTO;
import codesver.tannae.dto.HistoryDTO;
import codesver.tannae.dto.LostDTO;
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
    // User
    @GET("/users")
    Call<AccountDTO> getUsers(@Query("id") String id, @Query("pw") String pw);

    @POST("/users")
    Call<Boolean> postUser(@Body SignUpUserDTO dto);

    @GET("/users/account")
    Call<FoundAccountDTO> getAccount(@Query("name") String name, @Query("rrn") String rrn);

    @PATCH("/users/{usn}/point")
    Call<Integer> patchPoint(@Path("usn") Integer usn, @Body Integer point);

    @GET("/users/duplicate-private")
    Call<Boolean> duplicatePrivate(@Query("name") String name, @Query("rrn") String rrn);

    @GET("/users/duplicate-id")
    Call<Boolean> duplicateId(@Query("id") String id);

    // Vehicle
    @PATCH("/vehicles/{vsn}/running")
    Call<Boolean> patchRunning(@Path("vsn") Integer vsn, @Body Boolean running);

    @PATCH("/vehicles/{vsn}/users/score")
    Call<Boolean> patchUserScore(@Query("vsn") Integer vsn, @Body Float score);

    // History
    @GET("/histories")
    Call<List<HistoryDTO>> getHistories(@Query("usn") Integer usn);

    @GET("/histories/{hsn}")
    Call<HistoryDTO> getReceiptWithHsn(@Path("hsn") Integer hsn);

    @GET("/histories/receipts")
    Call<HistoryDTO> getReceiptWithUsn(@Query("usn") Integer usn);

    // Content
    @GET("/contents")
    Call<List<ContentDTO>> getContents();

    @POST("/contents")
    Call<Boolean> postContent(@Body RegisterContentDTO dto);

    @GET("/contents/{csn}")
    Call<ContentDTO> getContent(@Path("csn") Integer csn);

    @DELETE("/contents/{csn}")
    Call<Boolean> deleteContent(@Path("csn") Integer csn);

    @GET("/contents/faqs")
    Call<List<ContentFaqDTO>> getFaqs();

    @PATCH("/contents/{csn}/question")
    Call<Boolean> patchQuestion(@Path("csn") Integer csn, @Body StringDTO question);

    @PATCH("/contents/{csn}/answer")
    Call<Boolean> patchAnswer(@Path("csn") Integer csn, @Body StringDTO answer);

    // Service
    @POST("/service/request")
    Call<ServiceResponseDTO> request(@Body ServiceRequestDTO dto);

    // Lost
    @GET("/losts")
    Call<List<LostDTO>> getLosts();
}
