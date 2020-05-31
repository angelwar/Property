package com.huanzong.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.huanzong.property.R;
import com.huanzong.property.activity.login.LoginActivity;
import com.huanzong.property.fragment.AdministrationFragment;
import com.huanzong.property.fragment.CenterFragment;
import com.huanzong.property.fragment.FirstFragment;
import com.huanzong.property.fragment.FirstPageFragment;
import com.huanzong.property.fragment.SaleFragment;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.SharedPreferencesUtil;
import com.youth.xframe.widget.XToast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragment_rg;
    RadioGroup rb_group;
    FragmentTransaction trans;

    Fragment myFragment1;
    Fragment myFragment2;
    Fragment myFragment3;
    Fragment myFragment4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //toLowerCase() C37874C7FD06B93DB8A5CEEF697CC084
//        String a = "C37874C7FD06B93DB8A5CEEF697CC084";
//        String b = a.toLowerCase();
//        Log.e("tag",b);

        trans = getSupportFragmentManager().beginTransaction();
        myFragment1 = new FirstPageFragment();
        myFragment2 = new AdministrationFragment();
        myFragment3 = new SaleFragment();
        myFragment4 = new CenterFragment();
        trans.replace(R.id.fragment_rg,myFragment1);
        trans.commit();
    }

    private void initView() {
        fragment_rg = findViewById(R.id.fragment_rg);
        rb_group = findViewById(R.id.rb_group);

        rb_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                trans = getSupportFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.rb_main://首页
                        trans.replace(R.id.fragment_rg,myFragment1);
                        break;
                    case R.id.rb_order://小区管理
                        trans.replace(R.id.fragment_rg,myFragment2);
                        break;
                    case R.id.rb_notice://房屋租售
                        trans.replace(R.id.fragment_rg,myFragment3);
                        break;
                    case R.id.rb_mine://管理中心
                        trans.replace(R.id.fragment_rg,myFragment4);
                        break;
                }
                trans.commit();
            }
        });
    }

    public void onLogout(View view){
        new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE).setTitleText("确定退出登录？")
                .setCancelText("取消")
                .setConfirmText("确定")
                .setConfirmClickListener(sweetAlertDialog -> {
                    SharedPreferencesUtil.addToken(MainActivity.this,null);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }).show();

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.rl_total:
                break;
            case R.id.ll_no_house:toSaleFragment();
                break;
            case R.id.ll_no_user:toAdministrationFragment();
                break;
            case R.id.tn_yezhu:toAdministrationFragment();
                break;
            case R.id.tn_zuke:toAdministrationFragment();
                break;
            case R.id.tn_fagnke:toAdministrationFragment();
                break;
            case R.id.tn_lahei:toAdministrationFragment();
                break;
            case R.id.tn_zushou:toSaleFragment();
                break;
            case R.id.tn_finish:toSaleFragment();
                break;
            case R.id.tn_no_yuyue:toSaleFragment();
                break;
            case R.id.tn_deal_yuyue:toSaleFragment();
                break;
            case R.id.modify_passeord:
                modifyPassword();
                break;
        }

    }

    private void modifyPassword() {

        startActivity(new Intent(this,PasswordActivity.class));
    }

    private void toSaleFragment(){
        rb_group.check(R.id.rb_notice);
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_rg,myFragment3);
        trans.commit();
    }

    private void toAdministrationFragment(){
        rb_group.check(R.id.rb_order);
        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragment_rg,myFragment2);
        trans.commit();
    }
}
