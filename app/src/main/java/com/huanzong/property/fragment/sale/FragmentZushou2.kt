package com.huanzong.property.fragment.sale

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huanzong.property.R
import com.huanzong.property.database.DataBase
import com.huanzong.property.fragment.admin.UserData
import com.huanzong.property.fragment.admin.UserDataBase
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.PocketSwipeRefreshLayout
import com.huanzong.property.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_sale_list.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class FragmentZushou2 : Fragment(){

    var rv : RecyclerView? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var content = inflater.inflate(R.layout.fragment_sale_list,null)
        rv = content.findViewById<RecyclerView>(R.id.rv_list)
        val lay = LinearLayoutManager(activity)
        rv?.layoutManager = lay
        rv?.addItemDecoration(SpacesItemDecoration(20))
        setListData()
        sw_sale = content.findViewById(R.id.sw_sale)
        sw_sale?.setOnRefreshListener { setListData() }
        return content
    }
    var sw_sale : PocketSwipeRefreshLayout? = null
    fun setListData(){

        //租售状态 0 未通过审核  1通过审核 2已租或已售  3下架
        var hashMap = hashMapOf("status" to 2)
        HttpServer.getAPIService().onGetHouse(hashMap)
                .enqueue(object : Callback<DataBase<UserDataBase<UserData<SaleData>>>> {

                    override fun onResponse(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, response: Response<DataBase<UserDataBase<UserData<SaleData>>>>) {
                        sw_sale?.isRefreshing = false
                        if (response.body() != null) {

                            if (response.body() != null && response.body()!!.code == 1) {
                                val list = response.body()!!.data.users
                                if (list.data.size==0){
                                    showNullView()
                                    return
                                }
                                rv?.adapter = rv?.let { SaleHouseAdapter(activity, it,list.data,R.layout.item_house) }
                                hideNullView()
                            }else{
                                showNullView()
                                return
                            }

                        }
                    }

                    override fun onFailure(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, t: Throwable) {
                        tv_null.visibility = View.VISIBLE
                        sw_sale?.isRefreshing = false
                    }
                })
    }

    fun showNullView(){
        rv?.visibility = View.GONE
        tv_null.visibility = View.VISIBLE
    }

    fun hideNullView(){
        rv?.visibility = View.VISIBLE
        tv_null.visibility = View.GONE
    }
}