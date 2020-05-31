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
import com.huanzong.property.fragment.admin.User
import com.huanzong.property.fragment.admin.UserAdapter
import com.huanzong.property.fragment.admin.UserData
import com.huanzong.property.fragment.admin.UserDataBase
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_sale_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FragmentZushou3 : Fragment(){

    var rv : RecyclerView? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var content = inflater.inflate(R.layout.fragment_sale_list,null)
        rv = content.findViewById<RecyclerView>(R.id.rv_list)
        val lay = LinearLayoutManager(activity)
        rv?.layoutManager = lay
        rv?.addItemDecoration(SpacesItemDecoration(20))
        setListData()
        return content
    }

    fun setListData(){
        HttpServer.getAPIService().yuyue(0).enqueue(object : Callback<DataBase<UserDataBase<UserData<SaleData>>>> {

            override fun onResponse(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, response: Response<DataBase<UserDataBase<UserData<SaleData>>>>) {

                if (response.body() != null) {
                    if (response.body() != null && response.body()!!.code == 1) {
                        val list = response.body()!!.data.users
                        if (list.data.size==0){showNullView()
                            return}
                        rv?.adapter = rv?.let { SaleHouseAdapter(activity, it,list.data,R.layout.item_house) }
                        hideNullView()
                    }else{
                        showNullView()
                    }

                }
            }

            override fun onFailure(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, t: Throwable) {

                tv_null.visibility = View.VISIBLE
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