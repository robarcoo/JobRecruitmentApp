package com.example.hhrutest.ui.vacancy

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.domain.model.Response
import com.example.hhrutest.R

class ButtonAdapter(private val data: List<String>,
    val openDialog: (String, String) -> Unit) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

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
            val extraSpace = 1000
            this.post {
                val touchableArea = Rect()
                button.getHitRect(touchableArea)
                touchableArea.top += extraSpace * 100
                touchableArea.bottom += extraSpace * 100
                touchableArea.left += extraSpace
                //touchableArea.right += extraSpace
                this.touchDelegate = TouchDelegate(touchableArea, button)
            }
        }

        holder.itemView.setOnClickListener {
            openDialog("", question)
        }

    }


}
