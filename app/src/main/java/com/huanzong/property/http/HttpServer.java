package com.huanzong.property.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TypeAdapters;
import com.huanzong.property.MyApplication;
import com.huanzong.property.base.Global;
import com.huanzong.property.util.NullOnEmptyConverterFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.gson.internal.bind.TypeAdapters.INTEGER;
import static com.google.gson.internal.bind.TypeAdapters.LONG;

public class HttpServer {

    public static APIService getAPIService(){

        /*
         **打印retrofit信息部分
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog","retrofitBack = "+message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
//                .addInterceptor(loggingInterceptor)
//                .build();
        Authenticator authenticator = new Authenticator() {//当服务器返回的状态码为401时，会自动执行里面的代码，也就实现了自动刷新token
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                Log.d("RetrofitLog","==========>   重新刷新了token");//这里可以进行刷新 token 的操作
//                instance.getUploadToken()
                return response.request().newBuilder()
                        .addHeader("token", "")
                        .build();
            }
        };
        Interceptor tokenInterceptor = new Interceptor() {//全局拦截器，往请求头部添加 token 字段，实现了全局添加 token
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();//获取请求
                Request tokenRequest = null;
                if (TextUtils.isEmpty(MyApplication.getToken())) {//对 token 进行判空，如果为空，则不进行修改
                    return chain.proceed(originalRequest);
                }
                tokenRequest = originalRequest.newBuilder()//往请求头中添加 token 字段
                        .header("XX-Token", MyApplication.getToken())
                        .header("XX-Device-Type", "android")
                        .build();
                return chain.proceed(tokenRequest);
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(tokenInterceptor)
                .addInterceptor(loggingInterceptor)//使用上面的拦截器
                .authenticator(authenticator)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.BASE_API) //设置网络请求的Url地址
                .client(client)
                .addConverterFactory(new MyRetrofitFactory())
//                .addConverterFactory(new NullOnEmptyConverterFactory()) //必须是要第一个//设置为空也能解析
//                .addConverterFactory(GsonConverterFactory.create(buildGson())) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }

   /* *//**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     *//*

    private static Gson gson;
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                    .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                    .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(long.class, new LongDefaultAdapter())
                    .registerTypeAdapter(String.class, new StringNullAdapter())
                    .create();
        }
        return gson;
    }*/

//    public static Gson buildGson() {
//        //这里构造Gson，注册自定义的int和long的解析器
//        Gson gson = new GsonBuilder().registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, INTEGER))
//                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, LONG))
//                .create();
//        //这里通过反射的方法把自定义的MyReflectiveTypeAdapterFactory替换进去
//        try {
//            Field field = gson.getClass().getDeclaredField("constructorConstructor");
//            field.setAccessible(true);
//            ConstructorConstructor constructorConstructor = (ConstructorConstructor) field.get(gson);
//            Field factories = gson.getClass().getDeclaredField("factories");
//            factories.setAccessible(true);
//            List<TypeAdapterFactory> data = (List<TypeAdapterFactory>) factories.get(gson);
//            List<TypeAdapterFactory> newData = new ArrayList<>(data);
//            newData.remove(data.size() - 1);
//            newData.add(new MyReflectiveTypeAdapterFactory(constructorConstructor, FieldNamingPolicy.IDENTITY, Excluder.DEFAULT));
//            newData = Collections.unmodifiableList(newData);
//            factories.set(gson, newData);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return gson;
//    }

}
