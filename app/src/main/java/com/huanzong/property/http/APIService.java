package com.huanzong.property.http;

import com.huanzong.property.activity.login.LoginTokenData;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.fragment.first.TongJiDataBase;

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

    /**
     * 退出登录
     * @return
     */
    @POST("/api/admin/Public/logout")
    Call<String> logout();

    /**
     * 小区统计数据
     * @param cids 小区id  样式： [22,33,44]
     * @return
     */
    @POST("/api/admin/Public/tongji")
    Call<DataBase<TongJiDataBase>> tongji(@Query("cids")String cids);

    /**
     * 修改密码
     * @param uid
     * @param password
     * @return
     */
    @POST("/api/admin/Public/chword")
    Call<DataBase<String>> modifyPassword(@Query("uid")int uid,@Query("password")String password);

    /**
     *
     * @param c_id 小区id
     * @param status 0正常 1拉黑
     * @param mobile 手机号码
     * @param ident  1认证 0未认证
     * @param role   1业主 2家属 3租客
     * @param page   分页页数
     * @return
     */
    @POST("/api/admin/User/index")
    Call<DataBase<String>> onUserList(@Query("c_id")int c_id,@Query("status")int status,
                                      @Query("mobile")String mobile,@Query("ident")int ident,
                                      @Query("role")int role,@Query("page")int page);

    /**
     *
     * @param oid 用户id
     * @return
     */
    @POST("/api/admin/User/del")
    Call<DataBase<String>> deleteUser(@Query("oid")int oid);

    /**
     * 更新用户信息
     * @param oid 住户id
     * @param s 修改的字段： 拉黑 status   认证 ident
     * @param v 修改值 同上 v=1 拉黑 0 取消拉黑 v=1通过认证 0取消认证
     * @return
     */
    @POST("/api/admin/User/upstatus")
    Call<DataBase<String>> updataStatus(@Query("oid")int oid,@Query("s")String s,
                                        @Query("v")int v);

    /**
     * 访客记录
     * @param s
     * @return
     */
    @POST("/api/admin/User/vister")
    Call<DataBase<String>> onVisiter(@Query("mobile")String s);
}
