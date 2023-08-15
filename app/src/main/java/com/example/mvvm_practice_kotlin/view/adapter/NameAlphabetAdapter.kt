package com.example.mvvm_practice_kotlin.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.model.entities.Contacts

class NameAlphabetAdapter(private val classes: List<Contacts>) : RecyclerView.Adapter<NameAlphabetAdapter.ClassViewHolder>() {
    class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contacts: Contacts){
            tvNameAlphabet.text = (contacts.name!!.first().toString())
            Log.d("Test", "-------------- ${contacts.name.first()}")
            recyclerPerson.layoutManager = LinearLayoutManager(itemView.context)
            recyclerPerson.adapter = ContactsAdapter(listOf(contacts),itemView.context)
        }
        val tvNameAlphabet: TextView = itemView.findViewById(R.id.tvNameAlphabet)
        val recyclerPerson: RecyclerView = itemView.findViewById(R.id.recycleName)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.name_alphabet_item, parent, false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val contactClasses = classes[position]
        holder.bind(contactClasses)

    }

    override fun getItemCount(): Int {
        return classes.size
    }
    fun getFirstCharacter(inputString: String): String {
        return if (inputString.isNotEmpty()) {
            inputString[0].toString()
        } else {
            "not success"
        }
    }
}