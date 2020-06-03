package com.huanzong.property.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huanzong.property.R
import com.huanzong.property.database.DataBase
import com.huanzong.property.fragment.firstpage.TongJiData
import com.huanzong.property.fragment.firstpage.TongJiDataBase
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.SharedPreferencesUtil
import kotlinx.android.synthetic.main.fragment_main_0.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstPageFragment : Fragment(){

    fun getData(){
        HttpServer.getAPIService().tongji().enqueue(object : Callback<DataBase<TongJiDataBase>>{
            override fun onFailure(call: Call<DataBase<TongJiDataBase>>, t: Throwable) {

            }
            override fun onResponse(call: Call<DataBase<TongJiDataBase>>, response: Response<DataBase<TongJiDataBase>>) {
                var data = response.body()?.data?.data
                if (data != null) {
                    showData(data)
                }
            }
        })
    }


    var content : View? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        content = inflater.inflate(R.layout.fragment_main_0,null)
        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }
    fun showData(data : TongJiData){
        tv_total?.text = data.zrs.toString()
        tv_wrzyh?.text = "未认证用户("+data.wrzyh.toString()+")"
        tv_wrzfw?.text = "未认证房屋("+data.wshfw.toString()+")"
        tn_yezhu?.setNumber(data.yztj)
        tn_zuke?.setNumber(data.zktj)
        tn_fagnke?.setNumber(data.fktj)
        tn_lahei?.setNumber(data.lhzh)
        tn_zushou?.setNumber(data.zstj)
        tn_finish?.setNumber(data.ywcdd)
        tn_no_yuyue?.setNumber(data.wyytj)
        tn_deal_yuyue?.setNumber(data.yyytj)
    }
}