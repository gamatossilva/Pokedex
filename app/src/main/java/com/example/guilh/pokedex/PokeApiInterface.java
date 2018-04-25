package com.example.guilh.pokedex;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface PokeApiInterface {

    @GET("pokemon")
    Call<PokeList> getPokeList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokeDetails> getPokeDetail(@Path("id") int id);
}
