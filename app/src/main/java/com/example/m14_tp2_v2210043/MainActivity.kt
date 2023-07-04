package com.example.m14_tp2_v2210043

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.m14_tp2_v2210043.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Variables
        val localStorage = getSharedPreferences("logins", MODE_PRIVATE)

        //Ici on va définir les actions on click de notre bouton.
        binding.bLogin.setOnClickListener{
            hideKeyboard(it)
            if(binding.etUn.text.toString().isEmpty() || binding.etPw.text.toString().isEmpty()){
                Snackbar.make(it,getString(R.string.Log_IsEmpty), Snackbar.LENGTH_SHORT).show()
            }else if(!localStorage.contains(binding.etUn.text.toString())) {
                Snackbar.make(it, getString(R.string.Log_UserNoExist), Snackbar.LENGTH_SHORT).show()
            }else{
                myLogin(binding.etUn, binding.etPw, localStorage)
                //pas de message de succes, on passe direct a l'api , ca me semble evident que cest un succes
            }
        }

        // BUTTON POUR ALLER SUR L'ACTIVITY DE CHANGEMENT DE MOT DE PASSE
        binding.bChangePw.setOnClickListener{
            val myPwIntent = Intent(this, PwActivity::class.java)
            startActivity(myPwIntent)
        }

        // BUTTON POUR ALLER SUR L'ACTIVITY D'ENREGISTREMENT DE NOUVEL UTILISATEUR
        binding.bSignUp.setOnClickListener{
            val myRegIntent = Intent(this, RegActivity::class.java)
            startActivity(myRegIntent)
        }
    }

        // FONCTION DE LOGIN
    private fun myLogin(etUn: EditText, etPw: EditText, localStorage: SharedPreferences) {
        if(etPw.text.toString() == localStorage.getString("${etUn.text}", "")){
            startActivity(Intent(this,ApiActivity::class.java))
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.Log_Error))
                .setMessage(getString(R.string.Log_InvalidPw))
                .show()
        }
    }

        // FONCTION POUR CACHER LE KEYBOARD
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}