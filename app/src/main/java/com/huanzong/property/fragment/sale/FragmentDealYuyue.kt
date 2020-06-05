package com.huanzong.property.fragment.sale

import android.os.Bundle
import android.os.Handler
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
import com.huanzong.property.util.SharedPreferencesUtil
import com.huanzong.property.util.SpacesItemDecoration
import com.youth.xframe.adapter.XRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_sale_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentDealYuyue : Fragment(){

    var rv : RecyclerView? =null
    var sw_sale : PocketSwipeRefreshLayout? = null
    var page = 1
    var lastpage = 1
    var adapter : SaleHouseAdapter?=null
    var list: MutableList<SaleData> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var content = inflater.inflate(R.layout.fragment_sale_list,null)
        rv = content.findViewById<RecyclerView>(R.id.rv_list)
        val lay = LinearLayoutManager(activity)
        rv?.layoutManager = lay
        rv?.addItemDecoration(SpacesItemDecoration(20))
        sw_sale = content.findViewById(R.id.sw_sale)
        sw_sale?.setOnRefreshListener {
            page = 1
            //清空数据再次刷新
            Log.e("tag","setOnRefreshListener")
            adapter?.setDataLists(null)
            setListData()
        }

        adapter = SaleHouseAdapter(activity, rv!!,list,R.layout.item_house )
        rv?.adapter = adapter

        adapter?.isLoadMore(true)//开启加载更多功能,默认关闭
        adapter?.setOnLoadMoreListener(object : XRecyclerViewAdapter.OnLoadMoreListener {
            override fun onRetry() {
                Log.e("tag","onRetry")
                setListData()
            }

            override fun onLoadMore() {
                if (sw_sale?.isRefreshing == true){
                    return
                }
                if (page > lastpage) {
                    Handler().postDelayed({
                        adapter?.showLoadComplete()//没有更多数据了
                    }, 2000)
                } else {
                    Log.e("tag","onLoadMore  setListData")
                    setListData()
                }
            }
        })
        return content
    }

    fun setListData() {

        //租售状态 0 未通过审核  1通过审核 2已租或已售  3下架
        var hashMap = hashMapOf("ss" to 1)
        HttpServer.getAPIService().yuyue(hashMap)
                .enqueue(object : Callback<DataBase<UserDataBase<UserData<SaleData>>>> {
                    override fun onResponse(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, response: Response<DataBase<UserDataBase<UserData<SaleData>>>>) {
                        sw_sale?.isRefreshing = false
                        if (response.body() != null) {

                            if (response.body() != null && response.body()!!.code == 1) {
                                val userList = response.body()!!.data.users
                                if (userList.data.size==0){
                                    showNullView()
                                    return
                                }
                                lastpage = userList.last_page!!
                                if(page<=lastpage){
                                    page++
                                }
                                if (adapter==null){
                                    adapter = SaleHouseAdapter(activity, rv!!,list,R.layout.item_house )
                                }
                                adapter?.addAll(userList.data)
                                hideNullView()
                            }else{
                                showNullView()
                                return
                            }

                        }
                    }

                    override fun onFailure(call: Call<DataBase<UserDataBase<UserData<SaleData>>>>, t: Throwable) {
                        showNullView()
                        sw_sale?.isRefreshing = false
                    }
                })
    }

    fun showNullView(){
        rv?.visibility = View.GONE
        tv_null?.visibility = View.VISIBLE
    }

    fun hideNullView(){
        rv?.visibility = View.VISIBLE
        tv_null?.visibility = View.GONE
    }
}