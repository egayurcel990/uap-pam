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

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var plantImage: ImageView
    private lateinit var etNama: EditText
    private lateinit var etHarga: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnSimpan: CardView
    private lateinit var originalNama: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        plantImage = findViewById(R.id.imgTanaman)
        etNama = findViewById(R.id.etNama)
        etHarga = findViewById(R.id.etHarga)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Ambil data dari Intent
        originalNama = intent.getStringExtra("PLANT_NAME") ?: ""
        val harga = intent.getStringExtra("PLANT_PRICE") ?: ""
        val deskripsi = intent.getStringExtra("PLANT_DESC") ?: ""

        etNama.setText(originalNama)
        etHarga.setText(harga)
        etDeskripsi.setText(deskripsi)

        plantImage.setOnClickListener {
            Toast.makeText(this, "Fitur upload gambar belum tersedia", Toast.LENGTH_SHORT).show()
        }

        btnSimpan.setOnClickListener {
            val newNama = etNama.text.toString().trim()
            val newHarga = etHarga.text.toString().trim()
            val newDeskripsi = etDeskripsi.text.toString().trim()

            if (newNama.isEmpty() || newHarga.isEmpty() || newDeskripsi.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = TanamanRequest(
                plant_name = newNama,
                price = newHarga,
                description = newDeskripsi
            )

            RetrofitInstance.api.updateTanaman(originalNama, request)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@UpdateItemActivity, "Berhasil diperbarui", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            val message = response.body()?.message ?: "Gagal memperbarui"
                            Toast.makeText(this@UpdateItemActivity, message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(this@UpdateItemActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}
