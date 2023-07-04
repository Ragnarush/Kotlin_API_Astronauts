package com.example.m14_tp2_v2210043

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.m14_tp2_v2210043.databinding.ActivityPwBinding
import com.google.android.material.snackbar.Snackbar

class PwActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val localStorage = getSharedPreferences("logins", MODE_PRIVATE)

        binding.bMod.setOnClickListener {
            hideKeyboard(it)
            // validation
            if (binding.etNpw.text.isEmpty() || binding.etOpw.text.isEmpty() || binding.etMun.text.isEmpty()) {
                Snackbar.make(
                    it,
                    getString(R.string.Pw_IsEmpty),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.etOpw.text.toString() == binding.etNpw.text.toString()) {
                Snackbar.make(
                    it,
                    getString(R.string.Pw_IsSame),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (localStorage.contains(binding.etMun.text.toString())) {
                //modifier le mot de passe
                pwModifier(binding, localStorage)
            } else {
                Snackbar.make(it, getString(R.string.Pw_IsNotExist), Toast.LENGTH_SHORT).show()
            }
        }

        // BUTTON POUR REVENIR AU MAIN ACTIVITY
        binding.bPback.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java).apply {
            }
            startActivity(myIntent)
        }

        // BUTTON POUR CLEARER LES EDIT-TEXT
        binding.bPclear.setOnClickListener {
            binding.etMun.text.clear()
            binding.etOpw.text.clear()
            binding.etNpw.text.clear()
        }
    }


    // fonction de modifitaion de password
    private fun pwModifier(binding: ActivityPwBinding, localStorage: SharedPreferences) {
        if (binding.etOpw.text.toString() == localStorage.getString("${binding.etMun.text}", "")) {
            localStorage.edit().apply {
                putString("${binding.etMun.text}", "${binding.etNpw.text}")
            }.apply()
            Toast.makeText(this, getString(R.string.Pw_Success), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(this, getString(R.string.Pw_Wrong), Toast.LENGTH_SHORT).show()
        }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}