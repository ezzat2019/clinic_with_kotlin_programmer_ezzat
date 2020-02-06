package com.example.programmer.click.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.home_item.view.*

class HomeAdapter(val list: List<ClinicModel>, var listener1: OnItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.VH>() {
    private var listClinc: List<ClinicModel>? = null
    private lateinit var listener: OnItemClickListener

    init {
        listClinc = list
        listener = listener1

    }

    fun addItem(list: MutableList<ClinicModel>) {
        listClinc = list
        notifyDataSetChanged()
    }

    class VH(itemView: View, var listener1: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener({
                listener1.onClcik(adapterPosition)

            })
        }

        fun bindData(clinicModel: ClinicModel) {

            itemView.txt_home_time.text = clinicModel.time
            itemView.txt_homename.text = clinicModel.adress
            itemView.rate_home.rating = clinicModel.rate.toFloat()
            Glide.with(itemView.context)
                .load(clinicModel.img)
                .placeholder(R.drawable.profile)
                .into(itemView.img_home)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)

        return VH(v, listener)
    }

    override fun getItemCount(): Int {
        return listClinc!!.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.bindData(listClinc!!.get(position))

    }
}