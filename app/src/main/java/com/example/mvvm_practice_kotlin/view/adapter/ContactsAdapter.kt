package com.example.mvvm_practice_kotlin.view.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.App
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.comon.Common
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.example.mvvm_practice_kotlin.view.fragment.ContactsFragment


class ContactsAdapter(private var list: List<Contacts>, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var tv_groupTitle: TextView
        init {
            tv_groupTitle = itemView.findViewById(R.id.tvGroupTitle) as TextView
        }
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

        val inflater = LayoutInflater.from(context)
        return if (viewType == Common.VIEWTYPE_GROUP){
            val group = inflater.inflate(R.layout.group_layout,parent,false) as ViewGroup
                return GroupViewHolder(group)
        } else{
            val contactLayout = inflater.inflate(R.layout.view_item,parent,false) as ViewGroup
                return ContactsViewHolder(contactLayout)
        }
    }



    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is GroupViewHolder){
            viewHolder.tv_groupTitle.text = list[position].name
            viewHolder.itemView.setOnClickListener{
                (context as Activity).startActivityForResult(Intent(context,ContactsFragment::class.java),Common.RESULT_CODE)
            }
        }
        else if (viewHolder is ContactsViewHolder){
            viewHolder.tv_Name.text = list[position].name
            viewHolder.tv_PhoneNum.text = list[position].phone
            viewHolder.contactAvatar.setColorFilter(Color.GRAY)
            // Call
            viewHolder.itemView.setOnClickListener {
                val phoneNumber = list[position].phone
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                //check quyền và thực hiện cuộc gọi
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(callIntent)
                } else {
                    // Yêu cầu cấp quyền CALL_PHONE nếu chưa có
                    ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.CALL_PHONE), 111)
                }
            }
        }
        viewHolder.itemView.requestLayout()// yêu cầu cập nhật lại giao diện
    }


    override fun getItemCount(): Int {
        return list.size
    }

    //Ánh xạ dữ liệu thành các viewtype tương ứng
    override fun getItemViewType(position: Int): Int {
        return  list[position].viewType
    }

    private var contactList: List<Contacts> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    suspend fun updateData(newList: List<Contacts>) {
        contactList = newList
        App.database.contactDao().updateContact(contactList)
        notifyDataSetChanged()
    }

}





