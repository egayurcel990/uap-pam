package com.example.pamuap

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import model.TanamanSingleResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var imgTanaman: ImageView
    private lateinit var tvNama: TextView
    private lateinit var tvHarga: TextView
    private lateinit var tvDeskripsi: TextView
    private lateinit var btnUpdate: CardView
    private var namaTanaman: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Inisialisasi view
        imgTanaman = findViewById(R.id.imgDetail)
        tvNama = findViewById(R.id.tvNamaTanaman)
        tvHarga = findViewById(R.id.tvHargaTanaman)
        tvDeskripsi = findViewById(R.id.tvDeskripsi)
        btnUpdate = findViewById(R.id.btnUpdate)

        // Ambil nama tanaman dari intent
        namaTanaman = intent.getStringExtra("PLANT_NAME") ?: ""

        if (namaTanaman.isEmpty()) {
            Toast.makeText(this, "Data tanaman tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Ambil detail tanaman
        fetchPlantDetails(namaTanaman)

        btnUpdate.setOnClickListener {
            val intent = Intent(this, UpdateItemActivity::class.java)
            intent.putExtra("PLANT_NAME", tvNama.text.toString())
            intent.putExtra("PLANT_PRICE", tvHarga.text.toString().replace("Rp ", ""))
            intent.putExtra("PLANT_DESC", tvDeskripsi.text.toString())
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val updatedName = data?.getStringExtra("PLANT_NAME") ?: return
            fetchPlantDetails(updatedName)
        }
    }

    private fun fetchPlantDetails(nama: String) {
        RetrofitInstance.api.getTanamanByName(nama).enqueue(object : Callback<TanamanSingleResponse> {
            override fun onResponse(call: Call<TanamanSingleResponse>, response: Response<TanamanSingleResponse>) {
                if (response.isSuccessful && response.body()!= null) {
                    val tanaman = response.body()?.data
                    if (tanaman != null) {
                        tvNama.text = tanaman.plant_name
                        tvHarga.text = "Rp ${tanaman.price}"
                        tvDeskripsi.text = tanaman.description
                        imgTanaman.setImageResource(R.drawable.ic_tanaman) // default icon
                    }
                } else {
                    Toast.makeText(this@DetailActivity, "Gagal memuat detail tanaman", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TanamanSingleResponse>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
