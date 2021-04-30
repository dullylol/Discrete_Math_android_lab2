package com.asustuf.discretemathlab2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.asustuf.discretemathlab2.databinding.HolderLinkBinding
import com.asustuf.discretemathlab2.model.dataclasses.Link
import java.lang.IndexOutOfBoundsException

class LinksAdapter(private val links: MutableList<Link>) :
        RecyclerView.Adapter<LinksAdapter.LinkHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkHolder {
        val binding = HolderLinkBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return LinkHolder(binding)
    }

    override fun onBindViewHolder(holder: LinkHolder, position: Int) {
        holder.bind(links[position])
    }

    override fun getItemCount(): Int {
        return links.size
    }

    class LinkHolder(private val binding: HolderLinkBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(link: Link) {
            binding.vertex1Et.setText(link.vertex1.toString())
            binding.vertex2Et.setText(link.vertex2.toString())

            binding.vertex1Et.addTextChangedListener {
                link.vertex1 = try {
                    it.toString().toCharArray()[0]
                } catch (e: IndexOutOfBoundsException) { ' ' }
            }
            binding.vertex2Et.addTextChangedListener {
                link.vertex2 = try {
                    it.toString().toCharArray()[0]
                } catch (e: IndexOutOfBoundsException) { ' ' }
            }
        }
    }

}