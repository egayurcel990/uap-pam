package com.example.pamuap

import adapter.TanamanAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import model.Tanaman
import model.TanamanResponse
import network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tanamanAdapter: TanamanAdapter
    private lateinit var btnTambah: Button
    private var listTanaman: MutableList<Tanaman> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerTanaman)
        btnTambah = findViewById(R.id.btnTambah)

        setupRecyclerView()
        setupButtonClickListener()
        fetchTanamanFromApi()
    }

    private fun setupRecyclerView() {
        tanamanAdapter = TanamanAdapter(
            listTanaman,
            onDetail = { tanaman ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("PLANT_NAME", tanaman.plant_name)
                startActivity(intent)
            },
            onHapus = { tanaman ->
                hapusTanaman(tanaman.plant_name)
            }
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = tanamanAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupButtonClickListener() {
        btnTambah.setOnClickListener {
            val intent = Intent(this, TambahItemActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchTanamanFromApi() {
        RetrofitInstance.api.getAllTanaman().enqueue(object : Callback<TanamanResponse> {
            override fun onResponse(call: Call<TanamanResponse>, response: Response<TanamanResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    listTanaman.clear()
                    listTanaman.addAll(response.body()!!.data)
                    tanamanAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal memuat data! (${response.code()})", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TanamanResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun hapusTanaman(nama: String) {
        RetrofitInstance.api.deleteTanaman(nama).enqueue(object : Callback<model.DefaultResponse> {
            override fun onResponse(call: Call<model.DefaultResponse>, response: Response<model.DefaultResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@HomeActivity, "Tanaman dihapus", Toast.LENGTH_SHORT).show()
                    fetchTanamanFromApi()
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<model.DefaultResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchTanamanFromApi()
    }
}
