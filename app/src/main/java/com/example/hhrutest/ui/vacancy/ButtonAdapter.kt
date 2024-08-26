package com.example.hhrutest.ui.vacancy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Response
import com.example.hhrutest.R

class ButtonAdapter(private val data: List<String>) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    inner class ButtonViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        return ButtonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.question_button,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val question = data[position]
        holder.itemView.apply {
            val button = findViewById<Button>(R.id.question_button)
            button.text = question
        }
    }
}
