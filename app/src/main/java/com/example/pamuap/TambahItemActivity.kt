package com.example.pamuap

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import model.TanamanRequest
import model.DefaultResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahItemActivity : AppCompatActivity() {

    private lateinit var imgTanaman: ImageView
    private lateinit var etNama: EditText
    private lateinit var etHarga: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnTambah: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_item)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        imgTanaman = findViewById(R.id.imgTanaman)
        etNama = findViewById(R.id.etNama)
        etHarga = findViewById(R.id.etHarga)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnTambah = findViewById(R.id.btnTambah)
    }

    private fun setupListeners() {
        imgTanaman.setOnClickListener {
            Toast.makeText(this, "Upload gambar belum tersedia", Toast.LENGTH_SHORT).show()
        }

        btnTambah.setOnClickListener {
            validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val nama = etNama.text.toString().trim()
        val harga = etHarga.text.toString().trim()
        val deskripsi = etDeskripsi.text.toString().trim()

        if (nama.isEmpty()) {
            etNama.error = "Nama harus diisi"
            etNama.requestFocus()
            return
        }

        if (harga.isEmpty()) {
            etHarga.error = "Harga harus diisi"
            etHarga.requestFocus()
            return
        }

        if (deskripsi.isEmpty()) {
            etDeskripsi.error = "Deskripsi harus diisi"
            etDeskripsi.requestFocus()
            return
        }

        val request = TanamanRequest(nama, harga, deskripsi)

        RetrofitInstance.api.addTanaman(request).enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@TambahItemActivity, "Berhasil menambahkan!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@TambahItemActivity, "Gagal: ${response.body()?.message ?: "Terjadi kesalahan"}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(this@TambahItemActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
