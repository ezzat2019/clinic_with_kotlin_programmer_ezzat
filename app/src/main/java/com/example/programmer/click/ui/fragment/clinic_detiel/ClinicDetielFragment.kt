package com.example.programmer.click.ui.fragment.clinic_detiel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.pojo.DoctorModel
import com.example.programmer.click.ui.adapters.DoctorAdapter
import com.example.programmer.click.ui.fragment.home.HomeFragment
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.clinic_detiel_fragment.*


class ClinicDetielFragment : Fragment() {

    companion object {
        fun newInstance() =
            ClinicDetielFragment()

        var key: String? = null
    }

    // ui
    private lateinit var recDoctors: RecyclerView


    // var
    private var clinicModel: ClinicModel? = null
    private lateinit var viewModel: ClinicDetielViewModel
    private lateinit var listDoc: MutableList<DoctorModel>
    private lateinit var adapeter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.clinic_detiel_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!(arguments!!.isEmpty)) {
            clinicModel = arguments!!.getSerializable(HomeFragment.TAG_CLINIC) as ClinicModel

            key = clinicModel!!.key
            viewModel = ViewModelProviders.of(this).get(ClinicDetielViewModel::class.java)

            listDoc = mutableListOf()
            adapeter = DoctorAdapter(listDoc, object : OnItemClickListener {
                override fun onClcik(pos: Int) {

                    val bundle = Bundle()
                    bundle.putSerializable("doctor", listDoc.get(pos))
                    findNavController().navigate(
                        R.id.action_clinicDetielFragment_to_doctorFragment,
                        bundle
                    )

                }

            })

            bindView(clinicModel!!, view)

            buildRec(view)
        } else {
            Toast.makeText(view.context, "Error no data", Toast.LENGTH_SHORT).show()
        }


    }

    private fun buildRec(view: View) {
        recDoctors = view.findViewById(R.id.rec_doc)
        recDoctors.setHasFixedSize(true)
        recDoctors.setItemViewCacheSize(10)
        recDoctors.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recDoctors.adapter = adapeter
        viewModel.getDataODoc().observe(viewLifecycleOwner, Observer {

            listDoc = it as MutableList<DoctorModel>

            adapeter.addItem(listDoc)


        })

    }

    private fun bindView(clinicModel: ClinicModel, view: View) {
        Glide.with(view.context)
            .load(clinicModel.img)
            .placeholder(R.drawable.profile)
            .into(img_clinic)
        txt_add_clinic.text = clinicModel.adress
        rate_clinic.rating = clinicModel.rate.toFloat()

    }

}
