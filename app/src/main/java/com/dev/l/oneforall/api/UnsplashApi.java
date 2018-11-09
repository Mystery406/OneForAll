package com.dev.l.oneforall.api;

import com.dev.l.oneforall.entity.unsplash.PhotoDetail;
import com.dev.l.oneforall.entity.unsplash.PhotoInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by L on 2018/4/10.
 * Description:
 */
public interface UnsplashApi {
    @GET("photos?client_id=6c18f0d4f3c1fcd37b2388ec2c543f272777584f8ed62a4bcd0fba0fe904c6f8")
    Observable<List<PhotoInfo>> getUnsplashData(@Query("page") int page, @Query("per_page") int per_page);

    @GET("photos/{photo_id}?client_id=6c18f0d4f3c1fcd37b2388ec2c543f272777584f8ed62a4bcd0fba0fe904c6f8")
    Observable<PhotoDetail> getPhotoDetial(@Path(value = "photo_id") String photoId);
}
