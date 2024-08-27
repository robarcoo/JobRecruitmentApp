package com.example.hhrutest.ui.dashboard

import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Response
import com.example.hhrutest.R

class OfferAdapter(private val data : Response,
                   private val onItemClicked: (String) -> Unit) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    inner class OfferViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        return OfferViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.square_card,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.offers.size
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = data.offers[position]
        val icon = when(offer.id) {
            "near_vacancies" -> R.drawable.ic_location_on
            "level_up_resume" -> R.drawable.ic_star
            "temporary_job" -> R.drawable.ic_list
            else -> android.R.color.transparent
        }
        holder.itemView.apply {
            val offerIcon = findViewById<ImageView>(R.id.offer_icon)
            val offerTitle = findViewById<TextView>(R.id.offer_title)
            val offerButton = findViewById<TextView>(R.id.offer_button)
            if (icon == android.R.color.transparent) {
                offerIcon.visibility = View.GONE
            } else {
                offerIcon.setImageResource(icon)
            }
            offerTitle.text = offer.title
            offerButton.visibility = if (offer.button != null) View.VISIBLE else View.GONE
            offerButton.text = offerButton.text
            }

        holder.itemView.setOnClickListener {
            onItemClicked(offer.link)
        }
    }
}
