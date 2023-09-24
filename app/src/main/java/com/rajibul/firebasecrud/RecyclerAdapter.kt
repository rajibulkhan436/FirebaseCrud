package com.rajibul.firebasecrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var notes:ArrayList<Notes>,var listInterface: ListInterface):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view: View):RecyclerView.ViewHolder(view) {
        var tvTitle =view.findViewById<TextView>(R.id.tvTitle)
        var tvDescription =view.findViewById<TextView>(R.id.tvDescription)
        var tvTime =view.findViewById<TextView>(R.id.tvTime)
        var tvId =view.findViewById<TextView>(R.id.tvId)
        var btnUpdate =view.findViewById<Button>(R.id.btnUpdate)
        var btnDelete =view.findViewById<Button>(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()=notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setText(notes[position].title)
        holder.tvDescription.setText(notes[position].description)
        holder.tvTime.setText(notes[position].time)
        holder.tvId.setText("" + notes[position].id)
        holder.btnUpdate.setOnClickListener {
            listInterface.onUpdateClick(notes[position], position)
        }
        holder.btnDelete.setOnClickListener {
            listInterface.onDeleteClick(notes[position], position)
        }
    }
}