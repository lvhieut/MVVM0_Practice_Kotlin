package com.example.mvvm_practice_kotlin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.model.Contacts


class ContactsAdapter(private val list: List<Contacts>,private val context: Context) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {


    class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhoneNum: TextView = itemView.findViewById(R.id.tvNumberPhone)

        fun bind(contacts: Contacts) {
            tvName.text = contacts.name
            tvPhoneNum.text = contacts.phone
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
        //lay 1 layout tu moi truong giao dien sang moi truong code
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(list[position])
        holder.itemView.requestLayout()
    }


    override fun getItemCount(): Int {
        return list.size
    }

}