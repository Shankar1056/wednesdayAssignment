package com.wednesdayassignment.data.network

import com.wednesdayassignment.data.model.ArtistModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("search?term")
    fun getRepoList(
        @Query("term") repoName: String
    ): Observable<ArtistModel>


}