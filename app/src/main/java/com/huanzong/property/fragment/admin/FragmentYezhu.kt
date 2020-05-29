package com.huanzong.property.fragment.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huanzong.property.R
import com.huanzong.property.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_sale_list.*

class FragmentYezhu : Fragment(){

    var content : View? = null
    var rv:RecyclerView?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (content!=null){
//            var parent : ViewGroup? = content?.parent as ViewGroup?
//            if (parent!=null){
//                parent.removeView(content)
//            }
            return content
        }
        content = inflater.inflate(R.layout.fragment_sale_list,container, false)
        val lay = LinearLayoutManager(activity)
        rv = content?.findViewById(R.id.rv_list)
        rv?.layoutManager = lay
        rv?.addItemDecoration(SpacesItemDecoration(20))
        return content
    }

    fun setListData(list : List<User>){

    }

}