package com.huanzong.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.huanzong.property.R;
import com.huanzong.property.fragment.AdministrationFragment;
import com.huanzong.property.fragment.CenterFragment;
import com.huanzong.property.fragment.FirstPageFragment;
import com.huanzong.property.fragment.SaleFragment;
import com.huanzong.property.util.SharedPreferencesUtil;

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
            case R.id.ll_no_house:toSaleFragment(0);
                break;
            case R.id.ll_no_user:toAdministrationFragment(0);
                break;
            case R.id.tn_yezhu:toAdministrationFragment(1);
                break;
            case R.id.tn_zuke:toAdministrationFragment(2);
                break;
            case R.id.tn_fagnke:toAdministrationFragment(3);
                break;
            case R.id.tn_lahei:toAdministrationFragment(4);
                break;
            case R.id.tn_zushou:toSaleFragment(1);
                break;
            case R.id.tn_finish:toSaleFragment(2);
                break;
            case R.id.tn_no_yuyue:toSaleFragment(4);
                break;
            case R.id.tn_deal_yuyue:toSaleFragment(3);
                break;
            case R.id.modify_passeord:
                modifyPassword();
                break;

//            case R.id.tv_title_right:
//                startActivity(new Intent(this,PasswordActivity.class));
//                XToast.info("发布房源");
//                break;
        }

    }

    private void modifyPassword() {
        startActivity(new Intent(this,PasswordActivity.class));
    }

    private void toSaleFragment(int index){
        rb_group.check(R.id.rb_notice);
        trans = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putBoolean("isTo", true);
        SharedPreferencesUtil.setZs(this,3);
        SharedPreferencesUtil.addisMove(this,true);
        myFragment3.setArguments(bundle);
        trans.replace(R.id.fragment_rg,myFragment3);
        trans.commit();
    }

    private void toAdministrationFragment(int index){
        rb_group.check(R.id.rb_order);
        trans = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        SharedPreferencesUtil.addisMove(this,true);
        myFragment2.setArguments(bundle);
        trans.replace(R.id.fragment_rg,myFragment2);
        trans.commit();
    }
}
