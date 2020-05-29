package com.huanzong.property.fragment.sale

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huanzong.property.R
import java.util.*

class FragmentNoYuyue : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var content = inflater.inflate(R.layout.fragment_sale_list,null)
        var rv = content.findViewById<RecyclerView>(R.id.rv_list)
        val lay = LinearLayoutManager(activity)
        rv.layoutManager = lay
        var list = ArrayList<HouseData>()
        list.add(HouseData("蓝光金悦派"))
        list.add(HouseData("蓝光coco"))
        list.add(HouseData("恒大绿洲"))
        list.add(HouseData("远大都市"))
        list.add(HouseData("幸福家园"))
        rv.adapter = HouseAdapter(rv,list,R.layout.text)
        return content
    }
}