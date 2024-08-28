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
import com.example.hhrutest.util.humanUtil
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
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
            val peopleNumber = post.lookingNumber?.toInt()
            favoriteButton.changeFavoriteButton(post.isFavorite)
            if (peopleNumber != null) {
                 nowLookingTextView.text = context.getString(
                     R.string.now_looking_text,
                     peopleNumber.toString(),
                     humanUtil(peopleNumber)
                 )
            } else {
                nowLookingTextView.visibility = View.GONE
            }
            vacancyNameTextView.text = post.title
            vacancyLocationTextView.text = post.address.town
            companyNameTextView.text = post.company
            vacancyExperienceTextView.text = post.experience.previewText

            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = dateFormat.parse(post.publishedDate)
            val dateFormatter = SimpleDateFormat("d MMMM", Locale("ru"))
            val formattedDate = dateFormatter.format(date!!)
            publishedDataTextView.text = context.getString(R.string.publshed_on_text, formattedDate)
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

