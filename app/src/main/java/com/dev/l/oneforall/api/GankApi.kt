package com.dev.l.oneforall.api

import com.dev.l.oneforall.entity.gank.GankResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {
    @GET("福利/{size}/{page}")
    fun getGankData(@Path("size") size: Int, @Path("page") page: Int): Observable<GankResult>
}