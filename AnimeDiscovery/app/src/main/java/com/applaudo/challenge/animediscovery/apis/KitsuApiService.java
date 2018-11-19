package com.applaudo.challenge.animediscovery.apis;

import com.applaudo.challenge.animediscovery.apis.responses.AnimeResponse;
import com.applaudo.challenge.animediscovery.apis.responses.GenresResponse;
import com.applaudo.challenge.animediscovery.apis.responses.MangaResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//Definning Services for implementing retrofit
public interface KitsuApiService {

    @GET("genres")
    Single<GenresResponse> getGenres();

    @GET("anime")
    Single<AnimeResponse> getAnimesByGenre(@Query("filter[genres]") String genre);

    @GET("anime")
    Single<AnimeResponse> getAnimesByText(@Query("filter[text]") String genre);

    @GET("manga")
    Single<MangaResponse> getMangasByGenre(@Query("filter[genres]") String genre);

    @GET("{fullUrl}")
    Single<GenresResponse> getMediaGenres(@Path(value = "fullUrl", encoded = true) String fullUrl);
}
