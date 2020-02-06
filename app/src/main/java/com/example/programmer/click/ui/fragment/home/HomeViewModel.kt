package com.example.programmer.click.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.programmer.click.pojo.ClinicModel
import com.example.programmer.click.ui.activity.HomeActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    private val listClinics: MutableLiveData<List<ClinicModel>>
    private var list = mutableListOf<ClinicModel>()
    private var is_sort = false
    private val listClinicsTop: MutableLiveData<List<ClinicModel>>
    private var listTop = mutableListOf<ClinicModel>()
    private val data = FirebaseDatabase.getInstance()
    private val ref = data.getReference("clinic")
    private var uid: String = ""

    init {
        listClinicsTop = MutableLiveData()
        listTop = mutableListOf()

        listClinics = MutableLiveData()
        if (HomeActivity.uid != null) {
            uid = HomeActivity.uid.toString()

        }

    }

    fun getData(): LiveData<List<ClinicModel>> {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                for (i in p0.children) {


                    var clinicModel = i.getValue(ClinicModel::class.java) as ClinicModel
                    list.add(clinicModel)
                }

                listClinics.value = list

            }
        })
        return listClinics
    }

    fun getDataTop(): LiveData<List<ClinicModel>> {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                listTop.clear()
                for (i in p0.children) {


                    var clinicModel = i.getValue(ClinicModel::class.java) as ClinicModel
                    listTop.add(clinicModel)
                }

                listTop.sortByDescending { it.rate }
                listClinicsTop.value = listTop

            }

        })
        return listClinicsTop
    }


}