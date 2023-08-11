package com.example.mvvm_practice_kotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.databinding.ActivityHomeBinding
import com.example.mvvm_practice_kotlin.view.fragment.ContactsFragment
import com.example.mvvm_practice_kotlin.view.fragment.HomeFragment

class HomeActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.contacts -> replaceFragment(ContactsFragment())
                else -> {

                }
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fmSP = supportFragmentManager.popBackStack()
        val fmTransaction = supportFragmentManager.beginTransaction()
        fmTransaction.replace(R.id.fmLayout, fragment)
        fmTransaction.addToBackStack(null)
        fmTransaction.commit()
    }
}