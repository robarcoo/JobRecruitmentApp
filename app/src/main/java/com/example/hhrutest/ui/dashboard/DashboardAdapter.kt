package com.example.hhrutest.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Response
import com.example.hhrutest.R

class DashboardAdapter(private val data : Response) : RecyclerView.Adapter<DashboardAdapter.VacancyViewHolder>() {

    inner class VacancyViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {

        return VacancyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.vacancies.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val post = data.vacancies[position]
        holder.itemView.apply {
            val nowLookingTextView = findViewById<TextView>(R.id.now_looking)
            val vacancyNameTextView = findViewById<TextView>(R.id.vacancy_name)
            val vacancyLocationTextView = findViewById<TextView>(R.id.vacancy_location)
            val companyNameTextView = findViewById<TextView>(R.id.company_name)
            val vacancyExperienceTextView = findViewById<TextView>(R.id.vacancy_experience)
            val publishedDataTextView = findViewById<TextView>(R.id.published_data)
            nowLookingTextView.text = "Сейчас просматривает ${post.lookingNumber} человек"
            vacancyNameTextView.text = post.title
            vacancyLocationTextView.text = post.address.town
            companyNameTextView.text = post.company
            vacancyExperienceTextView.text = post.experience.previewText
            publishedDataTextView.text = post.publishedDate

    }
    }
}

