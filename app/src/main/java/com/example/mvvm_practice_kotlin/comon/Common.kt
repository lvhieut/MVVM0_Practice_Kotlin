package com.example.mvvm_practice_kotlin.comon

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.mvvm_practice_kotlin.model.entities.Contacts
import java.util.*
import kotlin.collections.ArrayList

object Common {
    val VIEWTYPE_GROUP = 0
    val VIEWTYPE_CONTACT = 1
    val RESULT_CODE = 1000

    var listContacts: ArrayList<Contacts> = arrayListOf()
    var alphabet_availiable: MutableList<String> = ArrayList()


    //sap xep list nhu bang chu cai
    fun sortList(list: ArrayList<Contacts>): ArrayList<Contacts>{
        list.sortWith(Comparator {
                contact1,contact2 -> contact1!!.name!!.compareTo(contact2!!.name!!)
        })
        return list
    }

    fun addAlphabets(list: ArrayList<Contacts>):ArrayList<Contacts>{
        var i = 0
        val customList = ArrayList<Contacts>()
        val firstMember = Contacts()
        firstMember.name = list[0].name!![0].toString()
        firstMember.viewType = VIEWTYPE_GROUP // set viewtype  la header
        alphabet_availiable.add(list[0].name!![0].toString())

        customList.add(firstMember)
        i=0
        while (i<list.size-1){
            val contacts = Contacts()
            val name1 = list[i].name!![0]
            val name2 = list[i + 1].name!![0]
            if (name1 == name2){
                list[i].viewType = VIEWTYPE_CONTACT
                customList.add(list[i])
            }else {
                list[i].viewType = VIEWTYPE_CONTACT
                customList.add(list[i])
                contacts.name = name2.toString()
                contacts.viewType = VIEWTYPE_GROUP
                alphabet_availiable.add(name2.toString())
                customList.add(contacts)
            }
            i++
        }
        list[i].viewType = VIEWTYPE_CONTACT
        customList.add(list[i])
        return customList
    }
     //return index of name in list
    fun findPositionWithName(name: String, list: ArrayList<Contacts>):Int{
        for (i in list.indices)
            if (list[i].name == name)
                return i
        return -1

    }

    //generate list from A-Z
    fun genAlphabetList():ArrayList<String>{
        val result = ArrayList<String>()
        for (i in 65..90) // 65 in ASCII CODE = A, 90 = Z
            result.add((i.toChar()).toString())
        return result
    }

    @SuppressLint("Range")
    fun genPersonGroup(context: Context): ArrayList<Contacts> {
        var contacts = ArrayList<Contacts>()

        val contentResolver = context.contentResolver

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        if (cursor!!.count > 0) {
            while (cursor!!.moveToNext()) {

                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                listContacts.add(Contacts(id.toInt(), name, phoneNumber, -1))
                Log.d("name>> ", "$name  $phoneNumber")
            }
        }
        return contacts
    }



}