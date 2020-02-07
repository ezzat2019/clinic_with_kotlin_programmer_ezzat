package com.example.programmer.click.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.home_item.view.*


class HomeAdapter(val list: List<ClinicModel>, var listener1: OnItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.VH>(), Filterable {
    companion object {
        var listClinc: List<ClinicModel>? = null
    }

    private var listClincT: List<ClinicModel>? = null
    private var listener: OnItemClickListener
    private var listClincFilter: List<ClinicModel>? = null


    init {
        listClinc = list
        listClincT = list
        listener = listener1
        listClincFilter = mutableListOf()

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

    override fun getFilter(): Filter {


        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence): FilterResults {

                var charString = charSequence.toString()
                Log.d("ezzzz2", charString)

                if (charString.equals(" ") || charString.equals("#")) {
                    Log.d("ezzzz6", charString)
                    listClincFilter = listClinc
                    charString = ""
                } else {

                    val filteredList: MutableList<ClinicModel> = mutableListOf()
                    for (androidVersion in listClincT!!) {

                        if (androidVersion.adress.toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion)
                        }
                    }
                    listClincFilter = filteredList
                }
                val filterResults = FilterResults()

                Log.d("ezzz", listClincFilter!!.size.toString())
                filterResults.values = listClincFilter
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                listClinc = filterResults.values as MutableList<ClinicModel>
                notifyDataSetChanged()
            }
        }
    }


}