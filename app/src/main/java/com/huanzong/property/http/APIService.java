package com.huanzong.property.http;

import com.huanzong.property.activity.login.LoginTokenData;
import com.huanzong.property.database.CommunityDataBase;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.database.JuheBean;
import com.huanzong.property.database.Visitor;
import com.huanzong.property.database.ZushouBean1;
import com.huanzong.property.fragment.admin.data.User;
import com.huanzong.property.fragment.admin.data.UserData;
import com.huanzong.property.fragment.admin.data.UserDataBase;
import com.huanzong.property.fragment.firstpage.TongJiDataBase;
import com.huanzong.property.fragment.sale.SaleData;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

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
     * @return
     */

    @POST("/api/admin/Public/tongji")
    Call<DataBase<TongJiDataBase>> tongji();

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
    Call<DataBase<UserDataBase<UserData<User>>>> onUserList(@Query("c_id")int c_id, @Query("status")int status,
                                                            @Query("mobile")String mobile, @Query("ident")int ident,
                                                            @Query("role")int role, @Query("page")int page);
    @Multipart
    @POST("/api/admin/User/index")
    Call<DataBase<UserDataBase<UserData<User>>>> onUserList(@PartMap HashMap<String,Integer> map);

    @POST("/api/admin/User/index")
    Call<DataBase<UserDataBase<UserData<User>>>> onUserMobile(@Query("mobile")String mobile);

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
    Call<DataBase<String>> updataStatus(@Query("oid")int oid,@Query("ss")String s,
                                        @Query("vv")int v);

    /**
     * 访客记录
     * @param s
     * @return
     */
    @POST("/api/admin/User/vister")
    Call<DataBase<UserDataBase<UserData<Visitor>>>> onVisiter(@Query("mobile")String s);

    /**
     * 访客记录
     * @return
     */
    @Multipart
    @POST("/api/admin/User/vister")
    Call<DataBase<UserDataBase<UserData<Visitor>>>> onVisiter(@PartMap HashMap<String,Integer> map);
    /**
     * 租售房屋管理
     * @param c_id 物业下属小区id 格式 [22,32]
     * @param status 租售状态 0 未通过审核  1通过审核 2已租或已售  3下架
     * @param mobile 手机号码
     * @param zs 租售类型 0租 1售
     * @return
     */
    @POST("/api/admin/House/index")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> onGetHouse(@Query("c_id")int c_id,
                                                                @Query("status")int status,
                                                                @Query("mobile")String mobile,
                                                                @Query("zs")int zs);

    @Multipart
    @POST("/api/admin/House/index")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> onGetHouse(@PartMap HashMap<String,Integer> map);

    @POST("/api/admin/House/index")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> onSearchHouse(@Query("mobile")String mobile);

    /**
     * 删除租售信息
     */
    @POST("/api/admin/House/del")
    Call<DataBase<String>> deteleHouse(@Query("id")int id);


    /**
     * 更改用户信息
     * @param oid
     * @param status 修改的字段： 拉黑 status   认证 ident
     * @param v 修改值 同上 v=1 拉黑 0 取消拉黑 v=1通过认证 0取消认证
     * @return
     */
    @POST("/api/admin/User/upstatus")
    Call<DataBase<String>> upDataUserStatus(@Query("oid")int oid,
                                            @Query("ss")String status,
                                            @Query("vv")int v);

    /**
     * 更改租售信息
     * @param id 房屋ID
     * @param status 修改的字段： 拉黑 status   认证 ident
     * @param v 修改值 同上 v=1 拉黑 0 取消拉黑 v=1通过认证 0取消认证
     *设置完成 出租或者出售：
     *  app.hzmtkj.com/api/admin/House/upstatus?id=房屋ID&ss=status&vv=2
     * @return
     */
    @POST("/api/admin/House/upstatus")
    Call<DataBase<String>> upDataHouseStatus(@Query("id")int id,@Query("ss")String status,@Query("vv")int v);

    /**
     * 未处理预约记录
     * 已处理 vv = 1
     * @return
     */
    @POST("/api/admin/House/yuyue")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> yuyue();

    /**
     * 未处理预约记录
     * 已处理 vv = 1
     * @return
     */
    @Multipart
    @POST("/api/admin/House/yuyue")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> yuyue(@PartMap HashMap<String,Integer> map);

    /**
     * 查询未处理预约记录
     * 已处理 vv = 1
     * @return
     */
    @POST("/api/admin/House/yuyue")
    Call<DataBase<UserDataBase<UserData<SaleData>>>> queryMoblie(@Query("mobile")String mobile);


    @GET("/portal/house/getHouseone.html")
    Call<DataBase<ZushouBean1>> getHouseone(@Query("id")int id);

    /**
     * 获取绑定小区列表
     * @return
     */
    @GET("user/register/getCommunityList.html")
    Call<CommunityDataBase> postAddressList(@Query("uid")int uid);

    //    上传图片
    @POST("/portal/house/thumb.html")
    Call<JuheBean> loadPicture(@Body RequestBody body);

    @POST("/portal/house/setHouse.html")
    Call<JuheBean> setHouse(@Body RequestBody data);
}
