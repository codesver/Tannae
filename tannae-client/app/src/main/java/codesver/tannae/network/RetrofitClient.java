package codesver.tannae.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://" + Network.ip + "/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
