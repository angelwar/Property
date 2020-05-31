package com.huanzong.property.activity.login;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.huanzong.property.R;
import com.huanzong.property.activity.MainActivity;
import com.huanzong.property.database.DataBase;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.SharedPreferencesUtil;
import com.youth.xframe.base.XActivity;
import com.youth.xframe.widget.XLoadingDialog;
import com.youth.xframe.widget.XToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends XActivity {

    //登录
    private void login(String username,String password){

        HttpServer.getAPIService().login(username,password,"android").enqueue(new Callback<DataBase<LoginTokenData>>() {
            @Override
            public void onResponse(Call<DataBase<LoginTokenData>> call, Response<DataBase<LoginTokenData>> response) {

                Log.e("tag","login success");
                if (response.body()!=null){
                    switch (response.body().getCode()){
                        case 0:
                            XToast.error(response.body().getMsg());
                            break;
                        case 1:
                            SharedPreferencesUtil.addToken(LoginActivity.this,response.body().getData().getToken());
                            SharedPreferencesUtil.addUid(LoginActivity.this,response.body().getData().getData().getUid());
                            if (response.body().getData().getData().getXiaoqu()!=null&&response.body().getData().getData().getXiaoqu().size()>0){
                                SharedPreferencesUtil.addCid(LoginActivity.this,response.body().getData().getData().getXiaoqu().get(0).getC_id());
                            }
                            toMainActivity();
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<DataBase<LoginTokenData>> call, Throwable t) {
                Log.e("tag","login error!"+t.getMessage());
                XLoadingDialog.with(LoginActivity.this).setMessage("登录失败！").show();
                XLoadingDialog.with(LoginActivity.this).dismiss();
            }
        });
    }

    private void toMainActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(SharedPreferencesUtil.queryToken(this))){
            toMainActivity();
        }

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (user==null||password==null){
                    XToast.error("请输入账号或密码");
                }else {
                    XLoadingDialog.with(LoginActivity.this).setMessage("正在登录");
                    login(user,password);
                }
            }
        });
    }

    @Override
    public void initView() {

    }
}
