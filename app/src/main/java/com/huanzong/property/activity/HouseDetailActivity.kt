package com.huanzong.property.activity

import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.huanzong.property.R
import com.huanzong.property.database.DataBase
import com.huanzong.property.database.ZushouBean1
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.GlideImageLoader
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.enitity.ThumbViewInfo
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.xframe.base.XActivity
import com.youth.xframe.widget.XToast
import kotlinx.android.synthetic.main.activity_house_detail.*
import kotlinx.android.synthetic.main.item_house.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HouseDetailActivity : XActivity(){

    var id = 0
    override fun initView() {

        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        banner.isAutoPlay(false)

        getData()

    }

    private fun getData() {
        HttpServer.getAPIService().getHouseone(id).enqueue(object : Callback<DataBase<ZushouBean1>>{
            override fun onFailure(call: Call<DataBase<ZushouBean1>>, t: Throwable) {
            }

            override fun onResponse(call: Call<DataBase<ZushouBean1>>, response: Response<DataBase<ZushouBean1>>) {
                if(response.body()!=null){
                    var data = response.body()?.data
                    setViewData(data)
                    setBanner(data)
                }
            }

        })
    }

    private fun setViewData(data: ZushouBean1?) {

        var zs = "售"
        var unit = "万元"
        if(data?.zs==0){
            zs = "租"
            unit = "元/月"
        }
        tv_yongjin.text = "佣金："+data?.price+"元"
        tv_title.text = data?.xqmc
        tv_money.text = data?.getFy()+unit
        fwhx.text = data?.getHx()
        jzmj.text = data?.getJzmj()
        lc.text = data?.getLc()+"层"
        var cx = "东"
        when (data?.getCx()) {
            0 -> cx = "东"
            1 -> cx = "南"
            2 -> cx = "西"
            3 -> cx = "北"
            4 -> cx = "东南"
            5 -> cx = "东北"
            6 -> cx = "西南"
            7 -> cx = "西北"
            8 -> cx = "南北"
            9 -> cx = "东西"
        }

        tv_cx.text = cx
    }

    private fun setBanner(data:ZushouBean1?) {
        if (data != null&&data.thumb!=null&&data.thumb.size!=0) {
            val images = data!!.getThumb()

            val thumbViewInfos = ArrayList<ThumbViewInfo>()
            for (imgBean in images) {
                val info = ThumbViewInfo(imgBean.getThumb())
                thumbViewInfos.add(info)
            }
            //设置图片集合
            banner.setImages(images)
            banner.setOnBannerListener(OnBannerListener { position ->
                //打开预览界面
                GPreviewBuilder.from(this@HouseDetailActivity)
                        //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                        .to(ImageLookActivity::class.java!!)
                        .setData(thumbViewInfos)
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        // 小圆点
                        //  .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start()//启动
            })

            //banner设置方法全部调用完毕时最后调用
            banner.start()
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        id = intent.getIntExtra("id",0)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_house_detail
    }

    fun onBack(v : View){
        finish()
    }

    fun onClick(v : View){
        when(v.id){
            R.id.bt_delete->onDeleteHouse(id)
            R.id.bt_ident->{
                val dialog = SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                dialog.titleText = "请输入佣金"
                dialog.cancelText = "取消"
                dialog.confirmText = "确认"
                dialog.setConfirmClickListener {
                    dialog.dismiss()
                    onUpdata(id,"price",dialog.editText.toInt()) }
                dialog.isEdit = true
                dialog.setEditTextHint("金额：单位元")
                dialog.show()
            }

        }
    }

    private fun onUpdata(oid: Int, status: String, v: Int) {
        val dialog = SweetAlertDialog(this)

        HttpServer.getAPIService().upDataHouseStatus(oid, status, v).enqueue(object : Callback<DataBase<String>> {
            override fun onResponse(call: Call<DataBase<String>>, response: Response<DataBase<String>>) {
                if(response.body()?.code == 1){
                   dialog.titleText = "设置成功！"
                    //更新佣金数据
                    getData()
                    dialog.show()
                }
            }
            override fun onFailure(call: Call<DataBase<String>>, t: Throwable) {
                dialog.titleText = "设置失败！"
                dialog.show()
            }
        })

    }

    private fun onDeleteHouse(id: Int) {
        HttpServer.getAPIService().deteleHouse(id).enqueue(object : Callback<DataBase<String>> {
            override fun onResponse(call: Call<DataBase<String>>, response: Response<DataBase<String>>) {

            }

            override fun onFailure(call: Call<DataBase<String>>, t: Throwable) {

            }
        })
    }
}