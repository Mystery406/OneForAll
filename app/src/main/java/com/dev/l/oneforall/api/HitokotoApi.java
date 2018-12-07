package com.dev.l.oneforall.api;

import com.dev.l.oneforall.entity.hitokoto.Hitokoto;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HitokotoApi {
    @GET("/")
    Observable<Hitokoto> getHitokotoData();
}
