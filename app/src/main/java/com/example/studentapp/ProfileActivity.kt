package com.example.studentapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userData = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("USER_DATA", UserData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("USER_DATA")
        }

        userData?.let { displayData(it) }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }
    }

    private fun displayData(data: UserData) {
        val initial = data.namaLengkap
            .split(" ")
            .take(2)
            .joinToString("") { it.first().uppercase() }

        findViewById<TextView>(R.id.tvInitial).text     = initial
        findViewById<TextView>(R.id.tvNama).text        = data.namaLengkap
        findViewById<TextView>(R.id.tvNimValue).text    = data.nim
        findViewById<TextView>(R.id.tvProdiValue).text  = data.programStudi
        findViewById<TextView>(R.id.tvGenderValue).text = data.jenisKelamin
        findViewById<TextView>(R.id.tvHobiValue).text   =
            if (data.hobi.isEmpty()) "-" else data.hobi.joinToString(" • ")
    }
}

