package com.huanzong.property.fragment.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huanzong.property.R
import com.huanzong.property.util.SpacesItemDecoration
import java.util.*

class FragmentZushou : Fragment(){

    var rv : RecyclerView? =null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var content = inflater.inflate(R.layout.fragment_sale_list,null)
        rv = content.findViewById<RecyclerView>(R.id.rv_list)
        val lay = LinearLayoutManager(activity)
        rv?.layoutManager = lay
        rv?.addItemDecoration(SpacesItemDecoration(20))
        return content
    }

    fun setListData(list : List<SaleData>){
        rv?.adapter = rv?.let { SaleHouseAdapter(it,list,R.layout.item_house) }
    }
}