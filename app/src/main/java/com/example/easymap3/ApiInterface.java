package com.example.easymap3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("isUser.php")
    Call<ServerResponse> isUser(@Query("email") String email,@Query("password") String password);

    @GET("getUsers.php")
    Call<List<User>> getUsers();

    @GET("getActiveUsers.php")
    Call<List<User>> getActiveUsers();

    @GET("getUserByEmail.php")
    Call<List<User>> getUserByEmail(@Query("email") String email);

    @GET("saveUser.php")
    Call<ServerResponse> saveUser(@Query("email") String email,@Query("password")
          String password,@Query("lat") Double lat,@Query("lon") Double lon,@Query("status") String status
            ,@Query("question") String question,@Query("answer") String answer);

    @GET("updateUserPassword.php")
    Call<ServerResponse> updateUserPassword(@Query("email") String email,@Query("password") String password);

    @GET("shareLocation.php")
    Call<ServerResponse> shareLocation(@Query("email") String email,@Query("password")
            String password,@Query("lat") Double lat,@Query("lon") Double lon,@Query("status") String status);

    //Home Service

    @GET("savePeople.php")
    Call<ServerResponse> savePeople(@Query("name") String name,@Query("email") String email,@Query("password")
            String password,@Query("contact") String contact,@Query("nid") String nid,@Query("address") String address
            ,@Query("question") String question,@Query("answer") String answer);

    @GET("isPeople.php")
    Call<ServerResponse> isPeople(@Query("email") String email,@Query("password") String password);

    @GET("saveLocation.php")
    Call<ServerResponse> saveLocation(@Query("id") int id,@Query("latitude") Double latitude,
                                      @Query("longitude") Double longitude);

    @GET("getPeople.php")
    Call<List<People>> getPeople(@Query("email") String email,@Query("password") String password);

    @GET("getAllPeople.php")
    Call<List<People>> getAllPeople();

    @GET("getPeopleByEmail.php")
    Call<List<People>> getPeopleByEmail(@Query("email") String email);

    @GET("getPeopleById.php")
    Call<List<People>> getPeopleById(@Query("id") int id);

    @GET("updatePeoplePassword.php")
    Call<ServerResponse> updatePeoplePassword(@Query("email") String email,@Query("password") String password);

    @GET("saveElectricianPost.php")
    Call<ServerResponse> saveElectricianPost(@Query("type") String type,@Query("available") String available,@Query("experience")
            String experience,@Query("details") String details,@Query("charge") String charge,
            @Query("division") String division,@Query("subDivision") String subDivision,@Query("latitude") Double latitude,
            @Query("longitude") Double longitude,@Query("userId") int userId,@Query("requestUserId") String requestUserId);

    @GET("getMyElectricianPost.php")
    Call<List<Electrician>> getMyElectricianPost(@Query("type") String type,@Query("userId") int userId);

    @GET("updateElectricianPost.php")
    Call<ServerResponse> updateElectricianPost(@Query("id") int id,@Query("available") String available,@Query("experience")
            String experience,@Query("details") String details,@Query("charge") String charge,
            @Query("division") String division,@Query("subDivision") String subDivision,@Query("latitude") Double latitude,
            @Query("longitude") Double longitude,@Query("userId") int userId,@Query("requestUserId") String requestUserId);

    @GET("deleteElectricianPost.php")
    Call<ServerResponse> deleteElectricianPost(@Query("id") int id);

    @GET("getAllElectricianPostBySubDiv.php")
    Call<List<Electrician>> getAllElectricianPostBySubDiv(@Query("type") String type,@Query("subDivision") String subDivision,@Query("userId") int userId);

    @GET("getAllElectricianPost.php")
    Call<List<Electrician>> getAllElectricianPost(@Query("type") String type,@Query("userId") int userId);

    @GET("requestElectrician.php")
    Call<ServerResponse> requestElectrician(@Query("id") int id,@Query("requestUserId") String requestUserId);

    @GET("editProfile.php")
    Call<ServerResponse> editProfile(@Query("id") int id, @Query("name") String name, @Query("email")
            String email, @Query("password") String password, @Query("contact") String contact,
                                     @Query("address") String address);

    @GET("getAllPostByType.php")
    Call<List<Electrician>> getAllPostByType(@Query("type") String type);

    @GET("acceptRequest.php")
    Call<ServerResponse> acceptRequest(@Query("id") int id,@Query("acceptedId") String acceptedId);

    @GET("getAllAcceptedPost.php")
    Call<List<Electrician>> getAllAcceptedPost(@Query("type") String type,@Query("userId") int userId);
}
