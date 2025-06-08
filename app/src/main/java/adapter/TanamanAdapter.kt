package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuap.R
import model.Tanaman

class TanamanAdapter(
    private val listTanaman: List<Tanaman>,
    private val onDetail: (Tanaman) -> Unit,
    private val onHapus: (Tanaman) -> Unit
) : RecyclerView.Adapter<TanamanAdapter.TanamanViewHolder>() {

    inner class TanamanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNama: TextView = itemView.findViewById(R.id.txtNama)
        val txtHarga: TextView = itemView.findViewById(R.id.txtHarga)
        val btnDetail: Button = itemView.findViewById(R.id.btnDetail)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TanamanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tanaman, parent, false)
        return TanamanViewHolder(view)
    }

    override fun onBindViewHolder(holder: TanamanViewHolder, position: Int) {
        val tanaman = listTanaman[position]

        holder.txtNama.text = tanaman.plant_name
        holder.txtHarga.text = "Rp ${tanaman.price}"

        holder.btnDetail.setOnClickListener {
            onDetail(tanaman)
        }

        holder.btnHapus.setOnClickListener {
            onHapus(tanaman)
        }
    }

    override fun getItemCount(): Int = listTanaman.size
}
