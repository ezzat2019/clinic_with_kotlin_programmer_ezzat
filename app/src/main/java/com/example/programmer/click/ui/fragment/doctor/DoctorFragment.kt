package com.example.programmer.click.ui.fragment.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.programmer.click.R
import com.example.programmer.click.pojo.DoctorModel
import com.example.programmer.click.ui.activity.BookActivity
import com.example.programmer.click.ui.activity.HomeActivity
import com.example.programmer.click.ui.fragment.clinic_detiel.ClinicDetielFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.doctor_fragment.*
import java.util.*


class DoctorFragment : Fragment() {

    private val startDate: Calendar = Calendar.getInstance()
    private lateinit var horizontalCalendar: HorizontalCalendar
    private lateinit var doctorModel: DoctorModel


    /* ends after 1 month from now */
    private val endDate: Calendar = Calendar.getInstance()


    companion object {
        fun newInstance() = DoctorFragment()
    }

    private lateinit var viewModel: DoctorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        endDate.add(Calendar.MONTH, 1)
        startDate.add(Calendar.MONTH, -1)
        return inflater.inflate(R.layout.doctor_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DoctorViewModel::class.java)

        buildDateView(view)

        doctorModel = arguments!!.getSerializable("doctor") as DoctorModel

        fillView(doctorModel, view)
        Log.d("wezz", doctorModel.name)

    }

    private fun fillView(doctorModel: DoctorModel, v: View) {
        txt_doctor_name2.text = doctorModel.name.toString()
        txt_doctor_book2.text = doctorModel.books.toString()
        txt_doctor_exp2.text = doctorModel.experience.toString()
        txt_doctor_rate2.text = doctorModel.rate.toString()
        rate_doctor2.rating = doctorModel.rate.toFloat()
        txt_doctor_spec2.text = doctorModel.spec.toString()
        txt_doctor_fees.text = doctorModel.fee
        Glide.with(v.context)
            .load(doctorModel.img)
            .placeholder(R.drawable.profile)
            .into(img_doctor2)


    }

    private fun buildDateView(view: View) {
        horizontalCalendar = HorizontalCalendar.Builder(
            view,
            R.id.calendarView
        )
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .defaultSelectedDate(Calendar.getInstance())
            .build()


        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) { //do something

            }
        }

        val bundle = Bundle()

        btn_book.setOnClickListener({
            bundle.putString("date", horizontalCalendar.selectedDate.time.toString())
            bundle.putString("uid", HomeActivity.uid)
            bundle.putString("clinic_key", ClinicDetielFragment.key)
            bundle.putString("doc_key", doctorModel.key)
            bundle.putString("fee", doctorModel.fee)

            val intent = Intent(
                view.context,
                BookActivity::class.java
            )
            intent.putExtras(bundle)
            startActivity(intent)

        })


    }

}




