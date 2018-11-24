package com.snwnw.snwnw.domain.interactors;

/**
 * Created by afaf.elshafey on 6/13/2017.
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.snwnw.snwnw.domain.models.FAQ_result_model;
import com.snwnw.snwnw.domain.models.add_place_model;
import com.snwnw.snwnw.domain.models.categoriesResult;
import com.snwnw.snwnw.domain.models.code_result;
import com.snwnw.snwnw.domain.models.favInterestModel;
import com.snwnw.snwnw.domain.models.fav_result;
import com.snwnw.snwnw.domain.models.homeCitiesModel;
import com.snwnw.snwnw.domain.models.homeCountriesModel;
import com.snwnw.snwnw.domain.models.mapsPlacesLoaderModel;
import com.snwnw.snwnw.domain.models.msgModel;
import com.snwnw.snwnw.domain.models.offerModel;
import com.snwnw.snwnw.domain.models.password_model;
import com.snwnw.snwnw.domain.models.place_details_model;
import com.snwnw.snwnw.domain.models.places_result;
import com.snwnw.snwnw.domain.models.profileResult;
import com.snwnw.snwnw.domain.models.register_result_model;
import com.snwnw.snwnw.domain.models.review_result;
import com.snwnw.snwnw.domain.models.service_cat_model;
import com.snwnw.snwnw.domain.models.social_model;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by jean on 29/07/16.
 */

public class SNWNWServices {
    //  private static String BASE_URL = "http://bahr.al7osam.net/api/Bahr/";
   // private static String DOMAIN_URL = "http://test.atsegypt.com/snwnwapp/";
    private static  String DOMAIN_URL = "http://snawnawapp.com/";

    public interface SNWNWApis{
        @POST("api/register")
        Call<register_result_model> CreateUser(@Body HashMap<String, Object> user_Data,
                                               @Header("Content-Type") String header_type);


        @POST("api/get_countries")
        Call<homeCountriesModel> homeCountries(@Body HashMap<String, Object> user_Data,
                                               @Header("Content-Type") String header_type);


        @POST("api/get_cities")
        Call<homeCitiesModel> homeCities(@Body HashMap<String, Object> user_Data,
                                         @Header("Content-Type") String header_type);

        @POST("api/register_social")
        Call<social_model> CreateSocialUser(@Body HashMap<String, Object> user_Data,
                                            @Header("Content-Type") String header_type);


        @POST("api/login")
        Call<register_result_model>LoginUser(@Body HashMap<String, Object> user_Data,
                                               @Header("Content-Type") String header_type);

        @POST("api/forget_password")
        Call<password_model>ForgetPass(@Body HashMap<String, Object> user_Data,
                                       @Header("Content-Type") String header_type);

        //profileResult

        @POST("api/get_user_byid")
        Call<profileResult>userProfileData(@Body HashMap<String, Object> user_Data,
                                           @Header("Content-Type") String header_type,
                                           @Header("Authorization")String authorizedToken);

        @POST("api/change_password_bycode")
        Call<code_result>updatePasswordbycode(@Body HashMap<String, Object> user_Data,
                                           @Header("Content-Type") String header_type);


        @POST("api/update_password")
        Call<code_result>updatePassword(@Body HashMap<String, Object> user_Data,
                                              @Header("Content-Type") String header_type,
                                        @Header("Authorization")String authorizedToken);


        @POST("api/user_update")
        Call<code_result>updateProfileData(@Body HashMap<String, Object> user_Data,
                                           @Header("Content-Type") String header_type,
                                           @Header("Authorization")String authorizedToken);

        @POST("api/active_user")
        Call<code_result>sendCode(@Body HashMap<String, Object> user_Data,
                                  @Header("Content-Type") String header_type,
                                  @Header("Authorization")String authorizedToken);

        @GET("api/resend_active_code")
        Call<code_result>resendCode(@Header("Content-Type") String header_type,
                                    @Header("Authorization")String authorizedToken);

        @POST("api/contactus")
        Call<code_result>sendOpinion(@Body HashMap<String, Object> user_Data,
                                  @Header("Content-Type") String header_type,
                                  @Header("Authorization")String authorizedToken);

        @POST("api/rate_review_place")
        Call<code_result>sendRateReview(@Body HashMap<String, Object> user_Data,
                                     @Header("Content-Type") String header_type,
                                     @Header("Authorization")String authorizedToken);

        @POST("api/get_rate_by_place_id")
        Call<review_result>getPlaceReviews(@Body HashMap<String, Object> user_Data,
                                           @Header("Content-Type") String header_type,
                                           @Header("Authorization")String authorizedToken);

        //review_result

        @POST("api/get_faq")
        Call<FAQ_result_model>getFAQS(@Body HashMap<String, Object> user_Data,
                                      @Header("Content-Type") String header_type,
                                      @Header("Authorization")String authorizedToken);


        @POST("api/suggest_place")
        Call<add_place_model>addNewPlace(@Body HashMap<String, Object> user_Data,
                                         @Header("Content-Type") String header_type,
                                         @Header("Authorization")String authorizedToken);
        @POST("api/get_map_places")
        Call<mapsPlacesLoaderModel>getPlaces(@Body HashMap<String, Object> user_Data,
                                             @Header("Content-Type") String header_type,
                                             @Header("Authorization")String authorizedToken);


        @POST("api/get_offers")
        Call<offerModel>Offers(@Body HashMap<String, Object> user_Data,
                               @Header("Content-Type") String header_type,
                               @Header("Authorization")String authorizedToken);


        @POST("api/search_place")
        Call<places_result>searchPlaces(@Body HashMap<String, Object> user_Data,
                                        @Header("Content-Type") String header_type,
                                        @Header("Authorization")String authorizedToken);

        @POST("api/get_place_byid")
        Call<place_details_model>placeDetails(@Body HashMap<String, Object> user_Data,
                                              @Header("Content-Type") String header_type,
                                              @Header("Authorization")String authorizedToken);

        @POST("api/list_favourite_place")
        Call<places_result>getMyFavList(@Body HashMap<String, Object> user_Data,
                                        @Header("Content-Type") String header_type,
                                        @Header("Authorization")String authorizedToken);


        @POST("api/search_place")
        Call<places_result>searchList(@Body HashMap<String, Object> user_Data,
                                        @Header("Content-Type") String header_type,
                                        @Header("Authorization")String authorizedToken);


        @POST("api/list_user_interest")
        Call<favInterestModel>myFav(@Body HashMap<String, Object> user_Data,
                                    @Header("Content-Type") String header_type,
                                    @Header("Authorization")String authorizedToken);


        @POST("api/new_user_interest")
        Call<msgModel>addToMyFav(@Body HashMap<String, Object> user_Data,
                                 @Header("Content-Type") String header_type,
                                 @Header("Authorization")String authorizedToken);


        @POST("api/list_suggest_place")
        Call<places_result>getMyUploadedPlaces(@Body HashMap<String, Object> user_Data,
                                        @Header("Content-Type") String header_type,
                                        @Header("Authorization")String authorizedToken);

        @POST("api/save_place")
        Call<fav_result>addPlaceToFav(@Body HashMap<String, Object> user_Data,
                                      @Header("Content-Type") String header_type,
                                      @Header("Authorization")String authorizedToken);


        @POST("api/get_category")
        Call<categoriesResult>getCategories(@Body HashMap<String, Object> user_Data,
                                            @Header("Content-Type") String header_type);


    }


    public SNWNWApis getAPI() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(DOMAIN_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                /// .client(okHttpClient)
                .client(getUnsafeOkHttpClient())
                .build();
        return retrofit.create(SNWNWApis.class);
    }


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}