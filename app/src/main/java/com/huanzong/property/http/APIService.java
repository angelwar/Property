package com.huanzong.property.http;

import com.huanzong.property.activity.login.LoginTokenData;
import com.huanzong.property.database.DataBase;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface APIService {

    /**
     * login
     * @param username
     * @param password
     * @param device_type 设备（['mobile', 'android', 'iphone', 'ipad', 'web', 'pc', 'mac']）
     * @return
     */
    @POST("/api/admin/Public/login")
    Call<DataBase<LoginTokenData>> login(@Query("username")String username,
                                         @Query("password")String  password,
                                         @Query("device_type")String device_type);

    @POST("/api/admin/Public/logout")
    Call<String> logout();

    /**
     * 小区统计数据
     * @param cids 小区id  样式： [22,33,44]
     * @return
     */
    @POST("/api/admin/Public/tongji")
    Observable<String> tongji(@Query("cids")String cids);
}
