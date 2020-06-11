package com.huanzong.property.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.huanzong.property.R
import com.huanzong.property.database.DataBase
import com.huanzong.property.fragment.admin.UserAdapter
import com.huanzong.property.fragment.admin.data.User
import com.huanzong.property.fragment.admin.data.UserData
import com.huanzong.property.fragment.admin.data.UserDataBase
import com.huanzong.property.http.HttpServer
import com.huanzong.property.util.SpacesItemDecoration
import com.youth.xframe.base.XActivity
import com.youth.xframe.widget.XToast
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : XActivity() ,UserAdapter.onRefreshListener{
    override fun onRefresh() {
        searchData()
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(SpacesItemDecoration(20))
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }
    private fun searchData() {

        HttpServer.getAPIService().onUserMobile(editText).enqueue(object : Callback<DataBase<UserDataBase<UserData<User>>>> {
            override fun onResponse(call: Call<DataBase<UserDataBase<UserData<User>>>>, response: Response<DataBase<UserDataBase<UserData<User>>>>) {
                val list = response.body()?.data?.users?.data
                if (list==null){
                    XToast.error("没有搜索到数据")
                    return
                }
                val adapter = UserAdapter(this@SearchActivity, recyclerView,list, R.layout.item_user)
                adapter.setLin(this@SearchActivity)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<DataBase<UserDataBase<UserData<User>>>>, t: Throwable) {

            }
        })
    }

    fun onBack(view: View) {
        finish()
    }

    var editText = ""
    fun onSearch(view: View) {
        hideInput()
        if (TextUtils.isEmpty(et_content.text.toString())){
            XToast.info("请输入手机号码！")
            return
        }
        editText = et_content.text.toString()
        searchData()
    }

    /**
     * 隐藏键盘
     */
    fun hideInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = getWindow().peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
        }
    }
}
