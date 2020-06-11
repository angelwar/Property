package com.huanzong.property.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.android.flexbox.FlexboxLayout;
import com.huanzong.property.R;
import com.huanzong.property.adapter.GridImageAdapter;
import com.huanzong.property.adapter.SelectCommunityAdapter;
import com.huanzong.property.database.Community;
import com.huanzong.property.database.CommunityDataBase;
import com.huanzong.property.database.JuheBean;
import com.huanzong.property.http.HttpServer;
import com.huanzong.property.util.ImgUtils;
import com.huanzong.property.util.SharedPreferencesUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.youth.xframe.base.XActivity;
import com.youth.xframe.widget.XToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishSaleActivity extends XActivity {

    private int themeId;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int aspect_ratio_x, aspect_ratio_y;

    private Spinner spCommunity;
    private FlexboxLayout.LayoutParams layoutParams;

    private RecyclerView rvPicture;
    private LinearLayout llShuru;
    private LinearLayout llXuanze;
    private EditText etDetail;
    private int cx;
    private int cw;
    private int dt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sale_house;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("租房发布");

        getData();

        //设置样式
        themeId = R.style.picture_default_style;
        rvPicture = findViewById(R.id.rv_picture);
        llShuru = findViewById(R.id.ll_shuru);
        llXuanze = findViewById(R.id.ll_xuanze);
        etDetail = findViewById(R.id.et_detail);
        etDetail.addTextChangedListener(wu);

        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        rvPicture.setLayoutManager(manager);

        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        //选择最多图片
        adapter.setSelectMax(9);
        rvPicture.setAdapter(adapter);

        adapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(PublishSaleActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                        break;
                    case 2:
                        // 预览视频
                        PictureSelector.create(PublishSaleActivity.this).externalPictureVideo(media.getPath());
                        break;
                    case 3:
                        // 预览音频
                        PictureSelector.create(PublishSaleActivity.this).externalPictureAudio(media.getPath());
                        break;
                }
            }
        });
    }

    //有无车位
    List<String> cwItems = new ArrayList<>();
    List<String> dtItems = new ArrayList<>();
    List<String> cxItems = new ArrayList<>();
    List<String> lcItems = new ArrayList<>();
    List<String> hxItems1 = new ArrayList<>();
    List<String> hxItems2 = new ArrayList<>();
    List<String> hxItems3 = new ArrayList<>();
    List<String> fkfsItems = new ArrayList<>();
    List<String> zxItems = new ArrayList<>();
    private void getData() {
        juheBeans = new ArrayList<>();
        cwItems.add("无车位");
        cwItems.add("有车位");

        dtItems.add("无电梯");
        dtItems.add("有电梯");

        cxItems.add("东");
        cxItems.add("南");
        cxItems.add("西");
        cxItems.add("北");
        cxItems.add("东南");
        cxItems.add("东北");
        cxItems.add("西南");
        cxItems.add("西北");
        cxItems.add("南北");
        cxItems.add("东西");

        for (int i = 1;i<100;i++){
            lcItems.add(i+"");
        }

        hxItems1.add("一室");
        hxItems1.add("二室");
        hxItems1.add("三室");
        hxItems1.add("四室");
        hxItems1.add("五室");
        hxItems1.add("六室");

        hxItems2.add("一厅");
        hxItems2.add("二厅");
        hxItems2.add("三厅");
        hxItems2.add("四厅");
        hxItems2.add("五厅");

        hxItems3.add("一卫");
        hxItems3.add("二卫");
        hxItems3.add("三卫");
        hxItems3.add("四卫");
        hxItems3.add("五卫");

        fkfsItems.add("押一付一");
        fkfsItems.add("押一付三");
        fkfsItems.add("半年付");
        fkfsItems.add("年付");

        zxItems.add("毛坯装修");
        zxItems.add("简单装修");
        zxItems.add("精致装修");
        zxItems.add("豪华装修");
    }

    private Switch aSwitch;

    private int cid;

    public void initView() {
        initET();
        spCommunity = findViewById(R.id.sp_community);
        aSwitch = findViewById(R.id.sw_tg);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tg = isChecked;
        });
        layoutParams = new FlexboxLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(35, 10, 10, 20);
        setSpCommunity();
    }
    private EditText et_xqmc,et_jzmj,et_fwzj,et_name,et_tel;
    private EditText et_dong,et_danyuan,et_ceng,et_hao;

    private TextView tv_hx,tv_cw,tv_zx,tv_cx,tv_dt;
    private void initET() {
        et_xqmc = findViewById(R.id.et_community);
        et_jzmj = findViewById(R.id.et_house_size);
        et_fwzj = findViewById(R.id.et_money);
        et_name = findViewById(R.id.et_lxr);
        et_tel = findViewById(R.id.et_lxrhm);
        et_dong = findViewById(R.id.et_dong);
        et_danyuan = findViewById(R.id.et_danyuan);
        et_ceng = findViewById(R.id.et_ceng);
        et_hao = findViewById(R.id.et_hao);

        tv_hx = findViewById(R.id.tv_hx);
        tv_cw= findViewById(R.id.tv_cw);
        tv_zx= findViewById(R.id.tv_zx);
        tv_cx= findViewById(R.id.tv_cx);
        tv_dt= findViewById(R.id.tv_dt);

    }

    private void setSpCommunity() {
       /* HttpServer.getAPIService().postAddressList(SharedPreferencesUtil.queryUid(this)).enqueue(new Callback<CommunityDataBase>() {
            @Override
            public void onResponse(Call<CommunityDataBase> call, Response<CommunityDataBase> response) {
                if (response.body()!=null&&response.body().getInfo()!=null&&response.body().getInfo().size()!=0){
                    spCommunity.setAdapter(new SelectCommunityAdapter(PublishSaleActivity.this,response.body().getInfo()));
                    spCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            xqmc = response.body().getInfo().get(position).getName();
                            cid = response.body().getInfo().get(position).getC_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CommunityDataBase> call, Throwable t) {

            }
        });*/

        List<Community> list = new ArrayList<>();
        Community c = new Community();
        c.setName(SharedPreferencesUtil.queryCidsName(this));
        c.setC_id(SharedPreferencesUtil.queryCid(this));
        list.add(c);
        spCommunity.setAdapter(new SelectCommunityAdapter(PublishSaleActivity.this,list));
        spCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xqmc = list.get(position).getName();
                cid = list.get(position).getC_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    TextWatcher wu = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len>=200){
                Toast toast = Toast.makeText(PublishSaleActivity.this,"字符不能超过200字",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,235);
                toast.show();
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = true;
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(PublishSaleActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(9)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE )// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(false)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(PublishSaleActivity.this)
                        .openCamera(PictureMimeType.ofAll())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(themeId)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(5)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode( PictureConfig.SINGLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);

//                    model.setImgs(selectList);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public void hindEdit() {
        llShuru.setVisibility(View.GONE);
        llXuanze.setVisibility(View.VISIBLE);
    }

    public void hindSelect() {
        llShuru.setVisibility(View.VISIBLE);
        llXuanze.setVisibility(View.GONE);
    }

    //点击键盘以外的地方，键盘消失
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //提交网络数据
    private void onDataSubmit() {
        try{
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (int i = 0;i<juheBeans.size();i++){
                jsonArray.put(juheBeans.get(i).getData());
            }
            jsonObject.put("img",jsonArray);
            jsonObject.put("uid",SharedPreferencesUtil.queryUid(this));
            jsonObject.put("c_id",cid);
            if (llShuru.getVisibility()==View.VISIBLE){
                jsonObject.put("xqmc",et_xqmc.getText().toString().trim());
            }else {
                jsonObject.put("xqmc",xqmc);
            }
            jsonObject.put("jzmj",et_jzmj.getText().toString().trim());
            jsonObject.put("cx",cx);
            jsonObject.put("cw",cw);
            jsonObject.put("dt",dt);
            jsonObject.put("fy",et_fwzj.getText().toString().trim());
//            jsonObject.put("fkfs",fkfsNumber);
            jsonObject.put("lxrxm",et_name.getText().toString().trim());
            jsonObject.put("sjhm",et_tel.getText().toString().trim());
            jsonObject.put("tg",tg);//是否托管，默认不托管、
            jsonObject.put("status",0);
            jsonObject.put("zs",1);//zs = 1 是售房
            jsonObject.put("hx",hxStr);//户型
            jsonObject.put("zhdz",et_dong.getText().toString().trim()+"-"+et_danyuan.getText().toString().trim()+"-"+et_ceng.getText().toString().trim());
            jsonObject.put("room",et_hao.getText().toString().trim());
//            jsonObject.put("fwpz",fwpz);//房屋配置
//            jsonObject.put("fwld",fwld);//房屋亮点
//            jsonObject.put("czyq",ccyq);//出租要求
            jsonObject.put("xxjs",etDetail.getText().toString().trim());//详情介绍
            jsonObject.put("zx",zxStr);//装修
            String data = jsonObject.toString();
            Log.e("tag",data);
            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), data);
            HttpServer.getAPIService().setHouse(requestBody).enqueue(new Callback<JuheBean>() {
                @Override
                public void onResponse(Call<JuheBean> call, Response<JuheBean> response) {
                    XToast.info("上传房屋成功");
                   PublishSaleActivity.this.finish();
                }

                @Override
                public void onFailure(Call<JuheBean> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<JuheBean> juheBeans ;
    private void uploadFilr(){
        juheBeans.clear();
        for (int i = 0;i<selectList.size();i++){
            String path  = "";
            if (TextUtils.isEmpty(selectList.get(i).getCompressPath())) {
                path = selectList.get(i).getPath();
            }else {
                path = selectList.get(i).getCompressPath();
            }

            //为什么base64方法返回的字符串都是一样的呢？,是因为上传时间接近，后台返回数据一样，已更正
            String base64 = ImgUtils.imageToBase64(path);
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("img","data:image/jpeg;base64,"+base64)
                    .build();

            HttpServer.getAPIService().loadPicture(requestBody).enqueue(new Callback<JuheBean>() {
                @Override
                public void onResponse(Call<JuheBean> call, Response<JuheBean> response) {
                    juheBeans.add(response.body());
                    if (juheBeans.size()==selectList.size()){
                        onDataSubmit();
                    }
                }

                @Override
                public void onFailure(Call<JuheBean> call, Throwable t) {
                }
            });

        }
    }

    private String hxShow,hxStr;
    private String cxStr;
    private String zxStr;
    private String cwStr;
    private String dtStr;
    private String xqmc;
    private boolean tg;

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_hx:
                //条件选择器
                OptionsPickerView hx = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                    //返回的分别是三个级别的选中位置
                    String tx = hxItems1.get(options1);
                    String tx1 = hxItems2.get(option2);
                    String tx2 = hxItems3.get(options3);
                    hxShow = tx+tx1+tx2;
                    hxStr = (options1+1)+"-"+(option2+1)+"-"+(options3+1);
                    tv_hx.setText(hxShow);
                }).build();
                hx.setNPicker(hxItems1,hxItems2,hxItems3);
                hx.show();
                break;
            case R.id.tv_cx:
                OptionsPickerView cx = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                    String tx = cxItems.get(options1);
                    cxStr = tx;
                    tv_cx.setText(cxStr);
                    this.cx = options1;
                }).build();
                cx.setPicker(cxItems);
                cx.show();
                break;
            case R.id.tv_zx:
                OptionsPickerView zx = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                    String tx = zxItems.get(options1);
                    zxStr = tx;
                    tv_zx.setText(zxStr);
                }).build();
                zx.setPicker(zxItems);
                zx.show();
                break;
            case R.id.tv_cw:
                OptionsPickerView cw = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                    String tx = cwItems.get(options1);
                    cwStr = tx;
                    tv_cw.setText(cwStr);
                    this.cw = options1;
                }).build();
                cw.setPicker(cwItems);
                cw.show();
                break;
            case R.id.tv_dt:
                OptionsPickerView dt = new OptionsPickerBuilder(this, (options1, option2, options3, v1) -> {
                    String tx = dtItems.get(options1);
                    dtStr = tx;
                    tv_dt.setText(dtStr);
                    this.dt = options1;
                }).build();
                dt.setPicker(dtItems);
                dt.show();
                break;
            case R.id.tv_submit:
                checkNull();
                break;
            case R.id.bt_Edit:
                hindSelect();
                break;
            case R.id.bt_select:
                hindEdit();
                break;
        }
    }

    private void checkNull() {
        if (selectList.size()==0){
            XToast.info("请至少上传一张图片");
            return;
        }

        if (TextUtils.isEmpty(et_xqmc.getText().toString().trim())&&llShuru.getVisibility()==View.VISIBLE){
            XToast.info("请输入小区名称");
            return;
        }

        if (TextUtils.isEmpty(et_jzmj.getText().toString().trim())){
            XToast.info("请输入房屋面积");
            return;
        }

        if (TextUtils.isEmpty(et_fwzj.getText().toString().trim())){
            XToast.info("请输入房屋售价");
            return;
        }
        if (TextUtils.isEmpty(et_name.getText().toString().trim())){
            XToast.info("请输入联系人姓名");
            return;
        }
        if (TextUtils.isEmpty(et_tel.getText().toString().trim())){
            XToast.info("请输入手机号码");
            return;
        }

        if (TextUtils.isEmpty(et_dong.getText().toString().trim())||TextUtils.isEmpty(et_danyuan.getText().toString().trim())
                ||TextUtils.isEmpty(et_ceng.getText().toString().trim())||TextUtils.isEmpty(et_hao.getText().toString().trim())){
            XToast.info("请输入完整楼座(单元)");
            return;
        }


        //没有问题之后，开始上传图片
        uploadFilr();
    }

    public void onBack(View v){
        finish();
    }
}
