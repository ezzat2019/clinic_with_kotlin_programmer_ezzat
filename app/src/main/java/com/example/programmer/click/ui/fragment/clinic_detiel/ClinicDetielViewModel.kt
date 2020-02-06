package com.example.programmer.click.ui.fragment.clinic_detiel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.programmer.click.pojo.DoctorModel
import com.example.programmer.click.ui.activity.HomeActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClinicDetielViewModel : ViewModel() {
    private val listDoctorModel: MutableList<DoctorModel>
    private val listDocLive: MutableLiveData<List<DoctorModel>>
    private val data = FirebaseDatabase.getInstance()
    private val ref = data.getReference("clinic").child(ClinicDetielFragment.key!!)
        .child("doctors")
    private var uid: String = ""

    init {
        listDoctorModel = mutableListOf()


        listDocLive = MutableLiveData()
        if (HomeActivity.uid != null) {
            uid = HomeActivity.uid.toString()
            Log.d("ezzatuid", uid)

        }

    }

    fun getDataODoc(): LiveData<List<DoctorModel>> {

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                listDoctorModel.clear()
                for (i in p0.children) {


                    var docModel = i.getValue(DoctorModel::class.java) as DoctorModel

                    listDoctorModel.add(docModel)
                }

                listDocLive.value = listDoctorModel

            }
        })
        return listDocLive
    }

}
