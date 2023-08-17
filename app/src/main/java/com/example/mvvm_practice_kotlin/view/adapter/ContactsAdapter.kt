package com.example.mvvm_practice_kotlin.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.comon.Common
import com.example.mvvm_practice_kotlin.comon.Common.VIEWTYPE_CONTACT
import com.example.mvvm_practice_kotlin.comon.Common.VIEWTYPE_GROUP
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.example.mvvm_practice_kotlin.view.fragment.ContactsFragment


class ContactsAdapter(private var list: List<Contacts>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var tv_groupTitle: TextView
        init {
            tv_groupTitle = itemView.findViewById(R.id.tvGroupTitle) as TextView
        }
//         val tv_Name: TextView = itemView.findViewById(R.id.tvName)
//         val tv_PhoneNum: TextView = itemView.findViewById(R.id.tvNumberPhone)
//        fun bind(contacts: Contacts) {
//            tv_Name.text = contacts.name
//            tv_PhoneNum.text = contacts.phone
//        }
    }



    inner class ContactsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
         var tv_Name: TextView
         var tv_PhoneNum: TextView
         var contactAvatar: ImageView
        init {
            tv_Name = itemView.findViewById(R.id.tvName) as TextView
            tv_PhoneNum = itemView.findViewById(R.id.tvNumberPhone) as TextView
            contactAvatar = itemView.findViewById(R.id.imgContacsAvatar) as ImageView
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun  setFilteredList(mList: List<Contacts>){
        this.list = mList as ArrayList<Contacts>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
//        val view: View = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
//        //lay 1 layout tu moi truong giao dien sang moi truong code
//        return GroupViewHolder(view)
        val inflater = LayoutInflater.from(context)
        when(viewType){
            Common.VIEWTYPE_GROUP -> {
                val group = inflater.inflate(R.layout.group_layout,parent,false) as ViewGroup
                return GroupViewHolder(group)
            }
            Common.VIEWTYPE_CONTACT -> {
                val contactLayout = inflater.inflate(R.layout.view_item,parent,false) as ViewGroup
                return ContactsViewHolder(contactLayout)
            }
            else -> {
                val group = inflater.inflate(R.layout.group_layout,parent,false) as ViewGroup
                return GroupViewHolder(group)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
//        holder.bind(list[position])
//        holder.itemView.requestLayout()// yêu cầu cập nhật lại giao diện

        if (viewHolder is GroupViewHolder){
            viewHolder.tv_groupTitle.text = list[position].name
            viewHolder.itemView.setOnClickListener{
                (context as Activity).startActivityForResult(Intent(context,ContactsFragment::class.java),Common.RESULT_CODE)
            }
        }
        else if (viewHolder is ContactsViewHolder){
            viewHolder.tv_Name.text = list[position].name
            viewHolder.tv_PhoneNum.text = list[position].phone
            val drawable = Color.BLUE
            viewHolder.contactAvatar.setColorFilter(drawable)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }




}





