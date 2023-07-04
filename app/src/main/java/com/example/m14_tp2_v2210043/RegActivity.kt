package com.example.m14_tp2_v2210043

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.m14_tp2_v2210043.databinding.ActivityRegBinding
import com.google.android.material.snackbar.Snackbar

class RegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val localStorage = getSharedPreferences("logins", MODE_PRIVATE)

        binding.bReg.setOnClickListener {
            hideKeyboard(it)
            if (binding.etRun.text.isEmpty() || binding.etRpw.text.isEmpty()){
                Snackbar.make(it, getString(R.string.Reg_IsEmpty), Snackbar.LENGTH_SHORT).show()
            } else if(localStorage.contains(binding.etRun.text.toString())) {
                Snackbar.make(it, getString(R.string.Reg_Exist), Snackbar.LENGTH_SHORT).show()
            }else {
                signUp(binding.etRun, binding.etRpw, localStorage)
            }
        }

        // BUTTON BACK POUR REVENIR AU MAIN ACTIVITY
        binding.bRback.setOnClickListener{
            val myIntent =  Intent(this, MainActivity::class.java).apply {
            }
            startActivity(myIntent)
        }

        // BUTTON CLEAR POUR CLEARER LES CHAMPS
        binding.bRclear.setOnClickListener{
            binding.etRun.text.clear()
            binding.etRpw.text.clear()
        }
    }

    // Fonction de signup
    private fun signUp(etRun: EditText, etRpw: EditText, localStorage: SharedPreferences) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.felicitation))
            .setMessage(getString(R.string.Felicitation_welcome))

        // enregistrer les donner dans le sharedPref
        if(localStorage.getString("${etRun.text}", "") == ""){
            localStorage.edit().apply{
                putString("${etRun.text}", "${etRpw.text}")
            }.apply() //commit async
        }
        builder.show()
    }

    // Fonction pour cacher le keyboard
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
