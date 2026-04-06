package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tilNama: TextInputLayout
    private lateinit var tilNim: TextInputLayout
    private lateinit var etNama: TextInputEditText
    private lateinit var etNim: TextInputEditText
    private lateinit var spinnerProdi: Spinner
    private lateinit var rgJenisKelamin: RadioGroup
    private lateinit var cbMembaca: CheckBox
    private lateinit var cbOlahraga: CheckBox
    private lateinit var cbMusik: CheckBox
    private lateinit var cbGaming: CheckBox
    private lateinit var cbMasak: CheckBox
    private lateinit var btnTampilkan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSpinner()
        setupButton()
    }

    private fun initViews() {
        tilNama        = findViewById(R.id.tilNama)
        tilNim         = findViewById(R.id.tilNim)
        etNama         = findViewById(R.id.etNama)
        etNim          = findViewById(R.id.etNim)
        spinnerProdi   = findViewById(R.id.spinnerProdi)
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin)
        cbMembaca      = findViewById(R.id.cbMembaca)
        cbOlahraga     = findViewById(R.id.cbOlahraga)
        cbMusik        = findViewById(R.id.cbMusik)
        cbGaming       = findViewById(R.id.cbGaming)
        cbMasak        = findViewById(R.id.cbMasak)
        btnTampilkan   = findViewById(R.id.btnTampilkan)
    }

    private fun setupSpinner() {
        val prodiList = listOf(
            "-- Pilih Program Studi --",
            "Teknik Informatika",
            "Sistem Informasi",
            "Teknik Elektro",
            "Teknik Sipil",
            "Teknik Mesin",
            "Matematika",
            "Fisika",
            "Kimia"
        )
        val adapter = ArrayAdapter(this, R.layout.spinner_item, prodiList)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerProdi.adapter = adapter
    }

    private fun setupButton() {
        btnTampilkan.setOnClickListener {
            if (validateInput()) {
                val userData = collectData()
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("USER_DATA", userData)
                startActivity(intent)
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        // Validasi Nama
        val nama = etNama.text.toString().trim()
        if (nama.isEmpty()) {
            tilNama.error = "Nama lengkap tidak boleh kosong"
            isValid = false
        } else {
            tilNama.error = null
        }

        // Validasi NIM
        val nim = etNim.text.toString().trim()
        if (nim.isEmpty()) {
            tilNim.error = "NIM tidak boleh kosong"
            isValid = false
        } else if (nim.length < 3) {
            tilNim.error = "NIM minimal 3 digit"
            isValid = false
        } else {
            tilNim.error = null
        }

        // Validasi Program Studi
        if (spinnerProdi.selectedItemPosition == 0) {
            Toast.makeText(this, "Pilih program studi terlebih dahulu", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validasi Jenis Kelamin
        if (rgJenisKelamin.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin terlebih dahulu", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validasi Hobi (minimal 1)
        val hobiDipilih = listOf(cbMembaca, cbOlahraga, cbMusik, cbGaming, cbMasak)
            .any { it.isChecked }
        if (!hobiDipilih) {
            Toast.makeText(this, "Pilih minimal satu hobi", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun collectData(): UserData {
        val nama = etNama.text.toString().trim()
        val nim  = etNim.text.toString().trim()
        val prodi = spinnerProdi.selectedItem.toString()

        val jenisKelamin = when (rgJenisKelamin.checkedRadioButtonId) {
            R.id.rbLakiLaki -> "Laki-laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> ""
        }

        val hobiList = mutableListOf<String>()
        if (cbMembaca.isChecked)  hobiList.add("Membaca")
        if (cbOlahraga.isChecked) hobiList.add("Olahraga")
        if (cbMusik.isChecked)    hobiList.add("Musik")
        if (cbGaming.isChecked)   hobiList.add("Gaming")
        if (cbMasak.isChecked)    hobiList.add("Memasak")

        return UserData(nama, nim, prodi, jenisKelamin, hobiList)
    }
}
