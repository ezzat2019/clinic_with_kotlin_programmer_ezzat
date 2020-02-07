package com.example.programmer.click.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.programmer.click.R
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.ui.adapters.HomeAdapter
import com.example.programmer.click.ui.fragment.home.HomeFragment
import com.example.programmer.click.utilies.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, View.OnClickListener,
    SearchView.OnCloseListener {
    private var listClinic: MutableList<ClinicModel>? = null
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var dashboardViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (HomeFragment.list != null) {
            listClinic = HomeFragment.list as MutableList<ClinicModel>
            buildRec(view)

        } else {
            Toast.makeText(view.context, "Error to load data", Toast.LENGTH_LONG).show()
        }
    }

    private fun buildRec(view: View) {
        rec_sreach.setHasFixedSize(true)
        rec_sreach.setItemViewCacheSize(10)
        rec_sreach.layoutManager = GridLayoutManager(view.context, 2)
        homeAdapter = HomeAdapter(listClinic!!, object : OnItemClickListener {
            override fun onClcik(pos: Int) {
                val bundle: Bundle = Bundle()
                bundle.putSerializable(HomeFragment.TAG_CLINIC, HomeAdapter.listClinc!!.get(pos))

                findNavController().navigate(
                    R.id.action_navigation_search_to_clinicDetielFragment,
                    bundle
                )

            }

        })
        rec_sreach.adapter = homeAdapter
        search.setOnQueryTextListener(this)
        search.setOnSearchClickListener(this)
        search.setOnCloseListener(this)


    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        homeAdapter.filter.filter(newText)


        return true
    }

    override fun onClick(p0: View?) {
        homeAdapter.filter.filter("#")
    }

    override fun onClose(): Boolean {
        homeAdapter.filter.filter("#")
        return false
    }
}