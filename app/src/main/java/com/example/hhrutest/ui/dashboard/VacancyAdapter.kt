package com.example.hhrutest.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Response
import com.example.domain.model.Vacancy
import com.example.hhrutest.R
import com.example.hhrutest.ui.vacancy.VacancyFragment
import kotlin.math.min

class VacancyAdapter(private val data : List<Vacancy>,
                     private var maxItems: Int = 3,
                     private val onFavoriteButtonClicked: (String) -> Unit,
                     private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

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
        return min(data.size, maxItems)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateMaxItems(max: Int) {
        maxItems = max
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val post = data[position]
        holder.itemView.apply {
            val nowLookingTextView = findViewById<TextView>(R.id.now_looking)
            val vacancyNameTextView = findViewById<TextView>(R.id.vacancy_name)
            val vacancyLocationTextView = findViewById<TextView>(R.id.vacancy_location)
            val companyNameTextView = findViewById<TextView>(R.id.company_name)
            val vacancyExperienceTextView = findViewById<TextView>(R.id.vacancy_experience)
            val publishedDataTextView = findViewById<TextView>(R.id.published_data)
            val favoriteButton = findViewById<ImageButton>(R.id.short_favorite_button)
            favoriteButton.setOnClickListener {
                onFavoriteButtonClicked(post.id)
                favoriteButton.changeFavoriteButton(post.isFavorite)
            }
            favoriteButton.changeFavoriteButton(post.isFavorite)
            nowLookingTextView.text = "Сейчас просматривает ${post.lookingNumber} человек"
            vacancyNameTextView.text = post.title
            vacancyLocationTextView.text = post.address.town
            companyNameTextView.text = post.company
            vacancyExperienceTextView.text = post.experience.previewText
            publishedDataTextView.text = post.publishedDate
        }
        holder.itemView.setOnClickListener {
            onItemClicked(post.id)
        }
    }

    private fun ImageButton.changeFavoriteButton(isFavorite : Boolean) {
        if (isFavorite) {
            this.setBackgroundResource(R.drawable.ic_favorite)
        } else {
            this.setBackgroundResource(R.drawable.ic_unfavorite)
        }
    }
}

