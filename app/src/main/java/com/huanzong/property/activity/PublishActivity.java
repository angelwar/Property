package com.huanzong.property.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.flexbox.FlexboxLayout;
import com.huanzong.property.R;
import com.youth.xframe.base.XActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublishActivity extends XActivity {

  /*  private int themeId;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private int aspect_ratio_x, aspect_ratio_y;

    private AppCompatSpinner spCommunity;
    private FlexboxLayout.LayoutParams layoutParams;

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolBar();
        setTitle("租房发布");
        initView();

        dataBind.setVm(model);
        dataBind.setP(p);
        p.initData();

        setPiker();

        //设置样式
        themeId = R.style.picture_default_style;
        GridLayoutManager manager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        dataBind.rvPicture.setLayoutManager(manager);

        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        //选择最多图片
        adapter.setSelectMax(9);
        dataBind.rvPicture.setAdapter(adapter);

        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(PublishActivity.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(PublishActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(PublishActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private void setPiker(){

        List<String> options1Items = new ArrayList<>();
        options1Items.add("有");
        options1Items.add("无");
        dataBind.llCwDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(PublishActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1);
                        Toast.makeText(PublishActivity.this,"点击了"+tx,Toast.LENGTH_SHORT).show();

                    }
                }).build();
                pvOptions.setPicker(options1Items);
                pvOptions.show();
            }
        });
    }*/


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    /*//    private String zxqk = "";
    private String fwpz = "";
    private String fwld = "";
    private String ccyq = "";
    private String xqjs = "";
    private void initView() {
        spCommunity = dataBind.spCommunity;
        layoutParams = new FlexboxLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(35, 10, 10, 20);
        setFwpz();
//        setZxqk();
        setfwld();
        setCzyq();

        dataBind.etDetail.addTextChangedListener(wu);
        dataBind.swTg.setOnCheckedChangeListener((buttonView, isChecked) -> model.setTg(isChecked?1:0));

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
                Toast toast = Toast.makeText(PublishActivity.this,"字符不能超过200字",Toast.LENGTH_SHORT);
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
                PictureSelector.create(PublishActivity.this)
                        .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
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
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(selectList)// 是否传入已选图片
                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled(true) // 裁剪是否可旋转图片
                        //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(PublishActivity.this)
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
                        .compress(false)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .selectionMedia(selectList)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
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
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }

                    model.setImgs(selectList);
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    //获得绑定小区列表
    public void setData(List<UserBean> doorData) {
        if(doorData==null||doorData.size()==0){
            CommonUtils.showToast(this,"您目前没有绑定小区");
            dataBind.spCommunity.setVisibility(View.GONE);
        }else{
            dataBind.spCommunity.setVisibility(View.VISIBLE);
        }
        spCommunity.setAdapter(new SelectCommunityAdapter(this,doorData));
        spCommunity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model.setXqmc(doorData.get(position).getName());
                model.setC_id(doorData.get(position).getC_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onFinishLoad() {
    }

    public void hindEdit() {
        dataBind.llShuru.setVisibility(View.GONE);
        dataBind.llXuanze.setVisibility(View.VISIBLE);
    }

    public void hindSelect() {
        dataBind.llShuru.setVisibility(View.VISIBLE);
        dataBind.llXuanze.setVisibility(View.GONE);
    }

    private Set<TextView> setCzyq;
    private void setCzyq() {
        setCzyq = new HashSet<>();
        String[] strings = {"禁烟","作息正常","租户稳定","一年起租","半年起租","一家人","只限女生","禁止养宠物"};
        for (int i = 0; i < strings.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(strings[i]);
            textView.setPadding(20, 10, 20, 10);
            textView.setOnClickListener(view -> {
                textView.setSelected(!textView.isSelected());
                if (textView.isSelected()) {
                    setCzyq.add(textView);
                }else {
                    if (setCzyq.contains(textView))  setCzyq.remove(textView);
                }
            });
            textView.setBackgroundResource(R.drawable.select_tab);
            textView.setLayoutParams(layoutParams);
            dataBind.flBoxCcyq.addView(textView);
        }
    }

    private Set<TextView> listFwld;
    private void setfwld() {
        listFwld = new HashSet<>();
        String[] strings = {"南北通透","首次出租"};
        for (int i = 0; i < strings.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(strings[i]);
            textView.setPadding(20, 10, 20, 10);
            textView.setOnClickListener(view -> {
                textView.setSelected(!textView.isSelected());
                if (textView.isSelected()) {
                    listFwld.add(textView);
                }else {
                    if (listFwld.contains(textView))  listFwld.remove(textView);
                }
            });
            textView.setBackgroundResource(R.drawable.select_tab);
            textView.setLayoutParams(layoutParams);
            dataBind.flBoxFwld.addView(textView);
        }
    }

    private Set<TextView> setFwpz;
    private void setFwpz() {
        setFwpz = new HashSet<>();
        String[] strings = {"冰箱", "电视", "洗衣机", "热水器",
                "空调",
                "宽带",
                "沙发",
                "床(独)",
                "暖气",
                "衣柜",
                "独立卫生间",
                "独立阳台",
                "智能门锁",
                "微波炉",
                "电磁炉",
                "油烟机",
                "燃气灶",
                "桌椅"};

        for (int i = 0; i < strings.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(strings[i]);
            textView.setPadding(20, 10, 20, 10);
            textView.setOnClickListener(view -> {
                textView.setSelected(!textView.isSelected());
                if (textView.isSelected()) {
                    setFwpz.add(textView);
                } else {
                    if (setFwpz.contains(textView)) {
                        setFwpz.remove(textView);
                    }
                }
            });
            textView.setBackgroundResource(R.drawable.select_tab);
            textView.setLayoutParams(layoutParams);
            dataBind.flBoxFwpz.addView(textView);
        }

    }

    public void onSubmit(){
        //装修情况
//        for (TextView tv : listZxqk){
//            if (tv.isSelected()){
//                zxqk = tv.getText().toString().trim();
//                break;
//            }
//        }
        //房屋配置
        for (TextView tv : setFwpz){
            fwpz = fwpz+tv.getText().toString().trim()+",";
        }
        //房屋亮点
        for (TextView tv :listFwld){
            fwld = fwld+tv.getText().toString().trim()+",";
        }
        //出租要求
        for(TextView tv :setCzyq){
            if (tv.isSelected()){
                ccyq = ccyq+tv.getText().toString().trim()+",";
            }
        }
        //详情介绍
        xqjs = dataBind.etDetail.getText().toString().trim();
        //去掉最后一个逗号
        if (fwpz.length()>0){
            fwpz = fwpz.substring(0,fwpz.length()-1);
        }

        if (fwld.length()>0){
            fwld = fwld.substring(0,fwld.length()-1);
        }
        if (ccyq.length()>0){
            ccyq = ccyq.substring(0,ccyq.length()-1);
        }

        Log.e("tag","房屋配置："+fwpz+"房屋亮点："+fwld+"出租要求："+ccyq+"详情介绍："+xqjs);
        p.submit(fwpz,fwld,ccyq,xqjs);
    }*/

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

}
