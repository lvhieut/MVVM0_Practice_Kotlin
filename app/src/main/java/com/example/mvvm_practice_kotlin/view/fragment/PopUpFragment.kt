package com.example.mvvm_practice_kotlin.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.mvvm_practice_kotlin.App
import com.example.mvvm_practice_kotlin.databinding.FragmentPopUpBinding
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.example.mvvm_practice_kotlin.view.adapter.ContactsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PopUpFragment : DialogFragment() {

    private val binding by lazy { FragmentPopUpBinding.inflate(layoutInflater) }

    private lateinit var contactName: String
    private lateinit var contactNumber: String


    var listContacts: ArrayList<Contacts> = arrayListOf()

    val adapter = context?.let { ContactsAdapter(listContacts, it) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnOk.setOnClickListener {
            contactName = binding.edtAddName.text.toString()
            contactNumber = binding.edtAddNumber.text.toString()

            val newContact = Contacts(name = contactName, phone = contactNumber)
            try {
                addContactToRoom(newContact)
                dismiss()
                udateRecycleView()
            } catch (e: Exception){
                Log.e("Add contact", "Error: ${e.message}")
            }

        }

    }

    private fun addContactToRoom(contact: Contacts) {
        CoroutineScope(Dispatchers.IO).launch {
            App.database.contactDao().insertContact(contact)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun udateRecycleView() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val contactList = App.database.contactDao().getAllContacts()
                listContacts.clear()
                listContacts.addAll(contactList)
                adapter?.updateData(listContacts)
                adapter?.notifyDataSetChanged()
                Log.d("TAG", "${App.database.contactDao().getAllContacts()} ")
            }
        } catch (e: Exception) {
            Log.d("new data", "${e.message}")
        }
    }
}