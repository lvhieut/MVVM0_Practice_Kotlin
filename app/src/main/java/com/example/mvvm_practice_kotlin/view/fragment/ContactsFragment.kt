package com.example.mvvm_practice_kotlin.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_practice_kotlin.App
import com.example.mvvm_practice_kotlin.databinding.FragmentContactsBinding
import com.example.mvvm_practice_kotlin.model.NameAlphabet
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.example.mvvm_practice_kotlin.view.adapter.ContactsAdapter
import com.example.mvvm_practice_kotlin.view.adapter.NameAlphabetAdapter
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ContactsFragment : Fragment() {
    private val binding by lazy { FragmentContactsBinding.inflate(layoutInflater) }

    lateinit var mcontext: Context
    var listContacts: ArrayList<Contacts> = arrayListOf()
    private lateinit var adapterr: ContactsAdapter

    private var coroutineStarted = false


    val classes = listOf(NameAlphabet(" A ", listContacts))


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterr = ContactsAdapter(listContacts, mcontext)
        binding.recycleContacts.adapter = adapterr


        showAllContacts()
        addControl()



        binding.edtSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.edtSearchView.queryHint = "Search here..."
                binding.edtSearchView.setActivated(true)
                binding.edtSearchView.onActionViewExpanded()
                binding.edtSearchView.setIconified(false)
                binding.edtSearchView.clearFocus()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(newText: String?) {
        val adapter = ContactsAdapter(listContacts, mcontext)
        binding.recycleContacts.adapter = adapter
        if (newText != null) {
            val filteredList = ArrayList<Contacts>()
            for (i in listContacts) {
                if (i.name?.toLowerCase(Locale.ROOT)!!.contains(newText)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }




    private fun firstChar(newText: String?) {

    }


    private fun showAllContacts() {
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                )
            } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                999
            )
        } else {
            readContact()
        }
    }


    @SuppressLint("Range")
    private fun readContact() {


        var contacts = Contacts()

        val cursor: Cursor? = context?.contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )
        if (cursor!!.count > 0) {
            while (cursor!!.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contacts = Contacts(id.toInt(), name, phoneNumber)
                listContacts.add(contacts)

//                if (!coroutineStarted){
//                    coroutineStarted = true
//                    CoroutineScope(Dispatchers.IO).launch {
//                        App.database.contactDao().insertContact(contacts)
//                            Log.d("Database","${App.database.contactDao().getAllContacts()} ")
//                    }
//                }
                Log.d("name>> ", name + "  " + phoneNumber)
            }
        }
        cursor.close()
    }

    fun addControl() {
        val adapter = NameAlphabetAdapter(listContacts)
        binding.recycleContacts.layoutManager = LinearLayoutManager(context)
        binding.recycleContacts.hasFixedSize()
        binding.recycleContacts.adapter = adapter
    }

}