package com.example.mvvm_practice_kotlin.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.comon.Common
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.google.android.material.internal.TextDrawableHelper

class NameAlphabetAdapter(private var list: List<Contacts>) : RecyclerView.Adapter<NameAlphabetAdapter.MyViewHolder>() {

    var alphabetList: List<String>

    init {
        alphabetList = Common.genAlphabetList()
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        lateinit var alphabet_img: ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.activity_alphabet,parent,false)
        return MyViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }

}