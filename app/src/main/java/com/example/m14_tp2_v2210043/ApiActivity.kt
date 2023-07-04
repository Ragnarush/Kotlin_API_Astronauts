package com.example.m14_tp2_v2210043

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m14_tp2_v2210043.databinding.ActivityApiBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class ApiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // BUTTON BACK POUR REVENIR AU MAIN ACTIVITY
        binding.bAback.setOnClickListener {
            val myIntent =  Intent(this, MainActivity::class.java).apply {
            }
            startActivity(myIntent)
        }

        val endpoint = getString(R.string.API_String)
        val targetType = object: TypeToken<Resultats>(){}.type

        lifecycleScope.launch(Dispatchers.Default) {
            val strResultats =  URL(endpoint).readText()
            val resultats = Gson().fromJson<Resultats>(strResultats, targetType)

            this@ApiActivity.runOnUiThread {
                binding.rv.adapter = RvAdapter(resultats)
                binding.rv.layoutManager = LinearLayoutManager(this@ApiActivity)
            }
        }
    }
}