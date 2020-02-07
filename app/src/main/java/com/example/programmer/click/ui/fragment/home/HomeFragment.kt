package com.example.programmer.click.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.programmer.click.R
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.ui.adapters.HomeAdapter
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    companion object {
        val TAG_CLINIC = "poss"
         var list: List<ClinicModel>? = null
    }

    // ui
    private var recyclerViewClinic: RecyclerView? = null
    private lateinit var recyclerViewClinicTop: RecyclerView
    private var viewModel: HomeViewModel? = null

    // var

    private var listTop: List<ClinicModel>? = null
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeAdapterTop: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        list = mutableListOf()
        listTop = mutableListOf()
        homeAdapter = HomeAdapter(list as MutableList<ClinicModel>, object : OnItemClickListener {
            override fun onClcik(pos: Int) {
                val bundle: Bundle = Bundle()
                bundle.putSerializable(TAG_CLINIC, list?.get(pos))

                findNavController().navigate(
                    R.id.action_navigation_home_to_clinicDetielFragment,
                    bundle
                )
            }

        })
        homeAdapterTop =
            HomeAdapter(listTop as MutableList<ClinicModel>, object : OnItemClickListener {
                override fun onClcik(pos: Int) {
                    val bundle: Bundle = Bundle()
                    bundle.putSerializable(TAG_CLINIC, listTop?.get(pos))

                    findNavController().navigate(
                        R.id.action_navigation_home_to_clinicDetielFragment,
                        bundle
                    )
                }

            })


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgess(true)
        buildRec(view)

    }

    private fun buildRec(view: View) {


        recyclerViewClinic = view.findViewById(R.id.home_rec)

        recyclerViewClinic!!.layoutManager = LinearLayoutManager(
            view.context
            , LinearLayoutManager.HORIZONTAL, false
        )
        recyclerViewClinicTop = view.findViewById(R.id.home_rec_top)
        recyclerViewClinicTop.layoutManager = LinearLayoutManager(
            view.context
            , LinearLayoutManager.HORIZONTAL, false
        )
        recyclerViewClinic!!.setHasFixedSize(true)
        recyclerViewClinicTop.setHasFixedSize(true)

        recyclerViewClinic!!.setItemViewCacheSize(20)
        recyclerViewClinic!!.isDrawingCacheEnabled = true
        recyclerViewClinic!!.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerViewClinic!!.adapter = homeAdapter

        recyclerViewClinicTop.setItemViewCacheSize(20)
        recyclerViewClinicTop.isDrawingCacheEnabled = true
        recyclerViewClinicTop.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel!!.getData().observe(viewLifecycleOwner, Observer {

            list = it
            homeAdapter.addItem(list as MutableList<ClinicModel>)

            showProgess(false)


        }
        )
        recyclerViewClinicTop.adapter = homeAdapterTop
        viewModel!!.getDataTop().observe(viewLifecycleOwner, Observer {

            listTop = it
            homeAdapterTop.addItem(listTop as MutableList<ClinicModel>)

            showProgess(false)


        }
        )


    }

    private fun showProgess(s: Boolean) {
        if (s) {
            home_relative.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

        } else {
            home_relative.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }


}