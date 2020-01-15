package com.example.drtherapist.common.Interface;


import com.example.drtherapist.therapist.model.DrSignInModel;
import com.example.drtherapist.therapist.model.Profile_Update_Model;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * Created by nitin on 25-09-2017.
 */

public interface UserInterface {

    //http://logicalsofttech.com/neibar/index.php/Api/add_Post
    //http://logicalsofttech.com/neibar/Api/login?user_email=kk@gmail.com&password=12345
    //-------- login --------------------
    @POST("login")
    Call<ResponseBody> login(
            @Query("user_email") String user_email,
            @Query("password") String password
    );

    //---- upload_image -------
    @Multipart
    @POST("add_Post")
    Call<ResponseBody> add_Post(
            @Query("user_id") String user_id,
            @Query("post_question") String post_question,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("jobPost")
    Call<ResponseBody> jobPost(
            @Query("user_id") String user_id,
            @Query("title") String title,
            @Query("location") String location,
            @Query("distance") String distance,
            @Query("user_job_msg") String user_job_msg,
            @Query("full_part_tym") String full_part_tym,
            @Part List<MultipartBody.Part> days,
            @Query("cat_id") String cat_id,
            @Query("cate_name") String cate_name,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Part List<MultipartBody.Part> startTime,
            @Part List<MultipartBody.Part> endTime,
            @Query("max_price") String max_price,
            @Query("min_price") String min_price,
            @Query("client_count") String client_count,
            @Query("doller_rate") String doller_rate
            );
//*********************************************

    @Multipart
    @POST("edit_Doc")
    Call<Profile_Update_Model> update_profile
            (@Part("dr_id")RequestBody dr_id,
             @Part("name") RequestBody name,
             @Part("address") RequestBody address1,
             @Part("gender")RequestBody gender,
             @Part("dob") RequestBody dob,
             @Part("city") RequestBody city,
             @Part("zip") RequestBody zip,
             @Part("state")RequestBody state,
             @Part("email") RequestBody email,
             @Part("mobile") RequestBody mobile,
             @Part("age")RequestBody age,
             @Part("licence_nmbr")RequestBody licence_nmbr,
             @Part("linked_url") RequestBody linked_url,
             @Part("high_education")RequestBody high_education,
             @Part MultipartBody.Part body,
             @Part MultipartBody.Part pdfbody);

    @Multipart
    @POST("SignUp")
    Call<DrSignInModel> dr_signIn
            (@Part("name")RequestBody dr_name,
             @Part("email") RequestBody email1,
             @Part("password")RequestBody password1,
             @Part("mobile") RequestBody mobile1,
             @Part("address")RequestBody address1,
             @Part("experience")RequestBody experience1,
             @Part("lat") RequestBody lat,
             @Part("lng") RequestBody lng,
             @Part("cat_id") RequestBody cat_id,
             @Part("dr_unique_key")RequestBody dr_unique_key,
             @Part("dob") RequestBody dob,
             @Part("fcm_id") RequestBody fcm_id,
             @Part MultipartBody.Part body,
             @Part("resume") RequestBody pdf);
}
