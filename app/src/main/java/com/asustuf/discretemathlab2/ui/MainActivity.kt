package com.asustuf.discretemathlab2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.asustuf.discretemathlab2.databinding.ActivityMainBinding
import com.asustuf.discretemathlab2.model.dataclasses.Link
import com.asustuf.discretemathlab2.model.graphs.LinksOperations
import com.asustuf.discretemathlab2.ui.adapters.LinksAdapter
import java.lang.Exception
import java.lang.NumberFormatException
import java.security.cert.Extension

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val links = mutableListOf<Link>()
        binding.linksRv.layoutManager = GridLayoutManager(this, 4)
        binding.linksRv.adapter = LinksAdapter(links)

        binding.defaultValuesBtn.setOnClickListener {
            binding.vertexesEt.setText("АБВГДЖЗИЕК")
            binding.countOfLinksEt.setText("19")
            setDefaultLinks(links)
            (binding.linksRv.adapter as LinksAdapter).notifyDataSetChanged()
        }

        binding.countOfLinksEt.addTextChangedListener {
            val linksCount = try {
                it.toString().toInt()
            } catch (e: NumberFormatException) {
                0
            }
            binding.linksRv.visibility = if (linksCount == 0) { View.GONE } else { View.VISIBLE }
            setLinksCount(links, linksCount)
            (binding.linksRv.adapter as LinksAdapter).notifyDataSetChanged()
        }

        binding.okBtn.setOnClickListener {
            try {
                val vertexes = binding.vertexesEt.text.toString().toSortedSet().toTypedArray()
                for (link in links) {
                    if (link.isOneVertexEmpty()) {
                        throw Exception("Link empty!")
                    }
                    if (!vertexes.contains(link.vertex1) || !vertexes.contains(link.vertex2)) {
                        throw Exception("Incorrect link!")
                    }
                }

                val linksOperations = LinksOperations(vertexes, links.toTypedArray())

                binding.degreesTv.text = "Degrees: " + linksOperations.vertexesDegreesString()
                binding.mappingTv.text = "Mapping:\n" + linksOperations.mapping()
                binding.preimagesTv.text = "Preimages:\n" + linksOperations.preimages()
                linksOperations.adjacencyMatrixCreateTable(binding.adjacencyMatrixTl)
                linksOperations.identityMatrixCreateTable(binding.identityMatrixTl)
                binding.adjacencyMatrixView.visibility = View.VISIBLE
                binding.identityMatrixView.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLinksCount(links: MutableList<Link>, count: Int) {
        links.clear()
        while (links.size != count) {
            if (links.size < count) {
                links.add(Link())
            } else {
                links.removeLast()
            }
        }
    }

    private fun setDefaultLinks(links: MutableList<Link>) {
        links.clear()
        links.addAll(mutableListOf(
                Link('А', 'Б'),
                Link('А', 'В'),
                Link('А', 'Г'),
                Link('А', 'Д'),
                Link('Г', 'В'),
                Link('Г', 'Д'),
                Link('В', 'Б'),
                Link('Б', 'Е'),
                Link('В', 'Ж'),
                Link('Г', 'Ж'),
                Link('Д', 'З'),
                Link('Д', 'И'),
                Link('Ж', 'Е'),
                Link('Ж', 'З'),
                Link('И', 'З'),
                Link('И', 'К'),
                Link('Ж', 'К'),
                Link('Е', 'К'),
                Link('З', 'К')))
    }

}