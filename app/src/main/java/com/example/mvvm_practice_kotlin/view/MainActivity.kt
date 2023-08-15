package com.example.mvvm_practice_kotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_practice_kotlin.R
import com.example.mvvm_practice_kotlin.databinding.ActivityMainBinding
import com.example.mvvm_practice_kotlin.utils.OnCallBack
import com.example.mvvm_practice_kotlin.viewmodell.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnCallBack {


    val loginViewModel: LoginViewModel by viewModels<LoginViewModel>()

    val binding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        CoroutineScope(Dispatchers.Main).launch {
            listenObsevable()
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.userName = binding.edtEmail.text.toString()
            loginViewModel.password = binding.edtPassword.text.toString()
            loginViewModel.getToken()

            val intent: Intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }


    }

    private fun listenObsevable() {
        loginViewModel.tokenResponse.observe(this) {
            val newToken = it?.requestToken
            if (!newToken.isNullOrBlank()) {
                println("========Token" + newToken)
                loginViewModel.login(newToken)
            } else {
                println("========Token false")
            }
        }
        loginViewModel.user.observe(this) { name ->
            name?.let { namecheck ->
                binding.edtEmail.setText(namecheck)
            }

            loginViewModel.passw.observe(this) { pass ->
                pass?.let { passcheck ->
                    Toast.makeText(this, passcheck, Toast.LENGTH_LONG).show()
                }
            }
        }
//        loginViewModel.userName3.collect{
//            it.let { namecheck ->
//                binding.edtEmail.setText(namecheck)
//            }
//        }
    }

    override fun loginSuccess() {
        Toast.makeText(this, "Login Sucess", Toast.LENGTH_LONG).show()
    }

    override fun notNullEmail() {
        Toast.makeText(this, "Email field can't be empty", Toast.LENGTH_LONG).show()
    }

    override fun NotNullPassword() {
        Toast.makeText(this, "Password field can't be empty", Toast.LENGTH_LONG).show()
    }

    override fun FormatEmail() {
        Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show()
    }

    override fun FormatPassword() {
        Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG).show()
    }

    override fun InvalidEmail() {
        Toast.makeText(this, "Email or password is incorrect", Toast.LENGTH_LONG).show()
    }



}