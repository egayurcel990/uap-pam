# 🌿 PAM UAP — Aplikasi Manajemen Tanaman

Aplikasi Android untuk manajemen data tanaman (CRUD) yang dilengkapi autentikasi pengguna menggunakan Firebase Authentication dan komunikasi REST API berbasis Retrofit.

---

## 📱 Fitur Utama

- **Autentikasi** — Register dan login pengguna via Firebase Authentication
- **Lihat Daftar Tanaman** — Menampilkan seluruh data tanaman dari API
- **Tambah Tanaman** — Input nama, harga, dan deskripsi tanaman baru
- **Detail Tanaman** — Melihat informasi lengkap satu tanaman
- **Update Tanaman** — Mengubah data tanaman yang sudah ada
- **Hapus Tanaman** — Menghapus tanaman dari daftar

---

## 🏗️ Arsitektur & Teknologi

| Komponen | Teknologi |
|---|---|
| Bahasa | Kotlin |
| Min SDK | 29 (Android 10) |
| Target SDK | 35 (Android 15) |
| Autentikasi | Firebase Authentication |
| Networking | Retrofit 2 + OkHttp |
| JSON Parsing | Gson |
| Layout | XML + ViewBinding |
| Build System | Gradle (Kotlin DSL) |

---

## 📂 Struktur Project

```
app/src/main/java/
├── com/example/pamuap/
│   ├── MainActivity.kt          # Splash/landing screen
│   ├── LoginActivity.kt         # Halaman login (Firebase Auth)
│   ├── RegisterActivity.kt      # Halaman registrasi (Firebase Auth)
│   ├── HomeActivity.kt          # Daftar tanaman (RecyclerView)
│   ├── DetailActivity.kt        # Detail satu tanaman
│   ├── TambahItemActivity.kt    # Form tambah tanaman
│   └── UpdateItemActivity.kt    # Form update tanaman
├── adapter/
│   └── TanamanAdapter.kt        # Adapter RecyclerView untuk list tanaman
├── model/
│   ├── Tanaman.kt               # Data class model tanaman
│   ├── TanamanRequest.kt        # Request body POST/PUT
│   ├── TanamanResponse.kt       # Response list tanaman
│   ├── TanamanSingleResponse.kt # Response satu tanaman
│   └── DefaultResponse.kt       # Response generik (success/message)
└── network/
    ├── TanamanApi.kt            # Interface endpoint Retrofit
    └── RetrofitInstance.kt      # Singleton Retrofit client
```

---

## 🌐 API Endpoint

Base URL: `https://uappam.kuncipintu.my.id/`

| Method | Endpoint | Deskripsi |
|---|---|---|
| `GET` | `/plant/all` | Ambil semua data tanaman |
| `GET` | `/plant/{name}` | Ambil detail tanaman berdasarkan nama |
| `POST` | `/plant/new` | Tambah tanaman baru |
| `PUT` | `/plant/{name}` | Update tanaman berdasarkan nama |
| `DELETE` | `/plant/{name}` | Hapus tanaman berdasarkan nama |

### Contoh Request Body (POST/PUT)
```json
{
  "plant_name": "Monstera",
  "price": "150000",
  "description": "Tanaman hias daun berlubang yang populer"
}
```

---

## 🔄 Alur Aplikasi

```
MainActivity
    ├── → LoginActivity     (Firebase Auth)
    │       └── → HomeActivity (setelah login berhasil)
    └── → RegisterActivity  (Firebase Auth)
            └── → LoginActivity (setelah registrasi berhasil)

HomeActivity
    ├── → TambahItemActivity  (tombol Tambah)
    └── → DetailActivity      (klik item di list)
            └── → UpdateItemActivity (tombol Update)
```

---

## 🚀 Cara Menjalankan

### Prasyarat
- Android Studio Hedgehog (2023.1.1) atau lebih baru
- JDK 11
- Perangkat/emulator dengan Android 10 (API 29) ke atas
- Koneksi internet aktif

### Langkah-langkah

1. **Clone repository**
   ```bash
   git clone https://github.com/<username>/uap-pam.git
   cd uap-pam
   ```

2. **Buka di Android Studio**
   - Pilih *File → Open* lalu arahkan ke folder project

3. **Konfigurasi Firebase**
   - Pastikan file `google-services.json` sudah ada di folder `app/`
   - Jika belum, buat project Firebase baru di [console.firebase.google.com](https://console.firebase.google.com), aktifkan **Email/Password Authentication**, lalu unduh `google-services.json`

4. **Sync & Build**
   ```
   Build → Make Project  (atau Ctrl+F9)
   ```

5. **Jalankan aplikasi**
   ```
   Run → Run 'app'  (atau Shift+F10)
   ```

---

## 📦 Dependencies

```kotlin
// Firebase
implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
implementation("com.google.firebase:firebase-auth-ktx")

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
```

---

## 📝 Catatan

- Upload gambar tanaman belum tersedia (fitur placeholder)
- Autentikasi menggunakan Firebase Email/Password
- Semua operasi CRUD dilakukan melalui REST API eksternal

---

## 📄 Lisensi

Project ini dibuat untuk keperluan **Ujian Akhir Praktikum (UAP)** mata kuliah Pemrograman Aplikasi Mobile (PAM).
