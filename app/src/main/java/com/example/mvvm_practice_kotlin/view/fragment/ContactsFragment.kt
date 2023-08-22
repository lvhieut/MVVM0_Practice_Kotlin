package com.example.mvvm_practice_kotlin.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_practice_kotlin.App
import com.example.mvvm_practice_kotlin.NavVisibilityListener
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.comon.Common
import com.example.mvvm_practice_kotlin.databinding.FragmentContactsBinding
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import com.example.mvvm_practice_kotlin.utils.OnContactAddedListener
import com.example.mvvm_practice_kotlin.view.adapter.ContactsAdapter
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ContactsFragment : Fragment(),OnContactAddedListener {
    private val binding by lazy { FragmentContactsBinding.inflate(layoutInflater) }

    private var navigationVisibilityListener: NavVisibilityListener? = null


    var isAtTop = true
    lateinit var mcontext: Context
    var listContacts: ArrayList<Contacts> = arrayListOf()

    val listConstackAndHearder = arrayListOf<Contacts>()

    private lateinit var adapterr: ContactsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext = context
        if (context is NavVisibilityListener) {
            navigationVisibilityListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigationVisibilityListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycleContacts.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0){
                    binding.fabAdd.hide()
                }else {
                    binding.fabAdd.show()
                }
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (isAtTop) {
                        // Hiển thị lại thanh điều hướng khi người dùng ngừng cuộn và ở đầu trang
                        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE }
                }else {
                }
            }
        })
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterr = ContactsAdapter(listContacts, mcontext)
        binding.recycleContacts.adapter = adapterr


        val layoutManager = LinearLayoutManager(requireContext())
        binding.recycleContacts.layoutManager = layoutManager
        val adapter = ContactsAdapter(listConstackAndHearder, requireContext())
        binding.recycleContacts.adapter = adapter

        showAllContacts()
        addControl()

        binding.fabAdd.setOnClickListener{
            val dialogFragment = PopUpFragment()
            dialogFragment.contactAddedListener = this
            dialogFragment.show(requireFragmentManager(), "dialog")
        }

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

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                adapterr.notifyDataSetChanged()
                return true
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("NotifyDataSetChanged")
    private fun filterList(newText: String?) {
        val adapter = ContactsAdapter(listContacts, mcontext)
        binding.recycleContacts.adapter = adapter
        if (newText?.trim() != null) {
            val filteredList = ArrayList<Contacts>()
            for (i in listContacts) {
                if (i.name?.toLowerCase(Locale.ROOT)!!.contains(newText) || i.phone?.contains(newText) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
            } else {
                adapter.setFilteredList(filteredList)
            }

        }
    }

    private fun showAllContacts() {
        if (context?.let {
                ContextCompat.checkSelfPermission(it, Manifest.permission.READ_CONTACTS)
            } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf(Manifest.permission.READ_CONTACTS), 999
            )
        } else {
            readContact()
        }
    }

    @SuppressLint("Range", "NotifyDataSetChanged")
    private fun readContact() {
        var contacts = Contacts()
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = context?.contentResolver?.query(
            uri,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        if (cursor!!.count > 0) {
            cursor.moveToFirst()

            while (cursor!!.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                contacts = Contacts(id.toInt(), name, phoneNumber, -1)
                listContacts.add(contacts)


                Log.d("name>> ", name + "  " + phoneNumber)
            }
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    App.database.contactDao().insertContact(contacts)
                    Log.d("Database", "${App.database.contactDao().getAllContacts()} ")
                }
            } catch (e: Exception) {
                Log.e("InsertError", "Error inserting contacts: ${e.message}")
            }
            cursor.close()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addControl() {
        //intialize a variable named is listGroup. This variable implement grouping first element of name.
        //EX : "HIEU" -> first element at index [0] = H
        val listGroup = listContacts.groupBy { it.name.first() } //khởi tạo 1 biến
        val listConstackAndHearder = arrayListOf<Contacts>() //chứa các đối tượng kiểu contacts theo kiểu arraylist
        //foreach is passing all elements
        listGroup.forEach { key, listItem ->
            //header
            listConstackAndHearder.add(// adding object Contact to listContactAndHeader
                Contacts(
                    name = key.toString(),
                    viewType = Common.VIEWTYPE_GROUP
                )
            )
            //list Sđt
            listConstackAndHearder.addAll(listItem)
        }
        val adapter = ContactsAdapter(listConstackAndHearder, mcontext)
        binding.recycleContacts.layoutManager = LinearLayoutManager(context)
        binding.recycleContacts.hasFixedSize()
        binding.recycleContacts.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onContactAdded(contacts: Contacts) {
        listContacts.add(contacts)
        adapterr.notifyItemInserted(listContacts.size -1)
    }
}