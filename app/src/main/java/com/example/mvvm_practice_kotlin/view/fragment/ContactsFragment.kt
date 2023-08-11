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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.databinding.FragmentContactsBinding
import com.example.mvvm_practice_kotlin.model.Contacts
import com.example.mvvm_practice_kotlin.view.adapter.ContactsAdapter

class ContactsFragment : Fragment() {
    private val binding by lazy { FragmentContactsBinding.inflate(layoutInflater) }

      lateinit var mcontext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext = context
    }

    private var listContacts: ArrayList<Contacts> = arrayListOf()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAllContacts()
        addControl()


    }


    private fun showAllContacts() {
        if(context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS) } != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(Manifest.permission.READ_CONTACTS), 999)
        } else {
            readContact()
        }

    }
    @SuppressLint("Range")
    private fun readContact(){
        val cursor: Cursor? = context?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        if(cursor!!.count > 0){
            while (cursor!!.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contacts = Contacts(id.toInt(), name, phoneNumber)
//            val contacts = Contacts()
                listContacts.add(contacts)
                Log.d("name>>", name + "  " + phoneNumber)
            }
        }



        cursor.close()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addControl(){
        val adapter = ContactsAdapter(listContacts,mcontext)
        binding.recycleContacts.layoutManager = LinearLayoutManager(context)
        binding.recycleContacts.hasFixedSize()
        binding.recycleContacts.adapter = adapter

    }

}