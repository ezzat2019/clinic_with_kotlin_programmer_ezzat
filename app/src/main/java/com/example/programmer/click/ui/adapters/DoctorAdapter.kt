package com.example.programmer.click.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.DoctorModel
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.doctor_item.view.*

class DoctorAdapter(val listD: MutableList<DoctorModel>, val listen: OnItemClickListener) :
    RecyclerView.Adapter<DoctorAdapter.VH>() {
    private lateinit var listener: OnItemClickListener
    private lateinit var listDA: MutableList<DoctorModel>

    init {
        listener = listen
        listDA = listD
    }

    fun addItem(listD2: MutableList<DoctorModel>) {
        listDA = listD2
        notifyDataSetChanged()

    }

    class VH(itemView: View, val listenVH: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener({
                listenVH.onClcik(adapterPosition)
            })
        }

        fun bindData(doctorModel: DoctorModel) {
            Glide.with(itemView.context)
                .load(doctorModel.img)
                .placeholder(R.drawable.profile)
                .into(itemView.img_doctor)

            itemView.txt_doctor_book.text = doctorModel.books
            itemView.txt_doctor_exp.text = doctorModel.experience
            itemView.txt_doctor_name.text = doctorModel.name
            itemView.rate_doctor.rating = doctorModel.rate.toFloat()
            itemView.txt_doctor_rate.text = doctorModel.rate.toString()
            itemView.txt_doctor_spec.text = doctorModel.spec
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_item, parent, false)

        return VH(v, listener)
    }

    override fun getItemCount(): Int {
        return listDA.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bindData(listDA.get(position))
    }


}