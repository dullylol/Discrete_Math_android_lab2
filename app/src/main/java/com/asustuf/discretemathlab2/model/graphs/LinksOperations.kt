package com.asustuf.discretemathlab2.model.graphs

import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.asustuf.discretemathlab2.model.dataclasses.Link

class LinksOperations(private val vertexes: Array<Char>, private val links: Array<Link>) {

    fun vertexesDegrees(): Array<Int> {
        val vertexesDegrees = Array(vertexes.size) { 0 }
        for ((vertexCount, vertex) in vertexes.withIndex()) {
            for (link in links) {
                if (vertex == link.vertex1 || vertex == link.vertex2) {
                    vertexesDegrees[vertexCount]++
                }
            }
        }
        return vertexesDegrees
    }

    fun vertexesDegreesString(): String {
        var degreesStr = ""
        val degrees = vertexesDegrees()
        for (i in degrees.indices) {
            degreesStr += "[" + vertexes[i] + ": " + degrees[i] + "]  "
        }
        return degreesStr
    }

    fun mapping(): String {
        var mappingStr = ""
        vertexes.forEach { vertex ->
            mappingStr += "G($vertex) = {"
            var count = 0
            links.forEach { link ->
                if (link.vertex1 == vertex) {
                    mappingStr += "${link.vertex2}, "
                    count++
                }
            }
            if (count == 0) {
                mappingStr += "∅"
            } else {
                mappingStr = mappingStr.dropLast(2)
            }
            mappingStr += "}\n"
        }
        return mappingStr
    }

    fun preimages(): String {
        var mappingStr = ""
        vertexes.forEach { vertex ->
            mappingStr += "G^-1($vertex) = {"
            var count = 0
            links.forEach { link ->
                if (link.vertex2 == vertex) {
                    mappingStr += "${link.vertex1}, "
                    count++
                }
            }
            if (count == 0) {
                mappingStr += "∅"
            } else {
                mappingStr = mappingStr.dropLast(2)
            }
            mappingStr += "}\n"
        }
        return mappingStr
    }

    fun adjacencyMatrix(): Array<Array<Int>> {
        val matrix = Array(vertexes.size) { Array(vertexes.size) { 0 } }
        links.forEach {
            matrix[vertexes.indexOf(it.vertex1)][vertexes.indexOf(it.vertex2)] = 1
        }
        return matrix
    }

    fun adjacencyMatrixCreateTable(table: TableLayout) {
        val matrix = adjacencyMatrix()
        val context = table.context
        val firstTableRow = TableRow(context)

        firstTableRow.addView(View(context))
        vertexes.forEach {
            firstTableRow.addView(TextView(context).apply {
                this.text = it.toString()
                setViewStyle(this)
            })
        }
        table.addView(firstTableRow)

        for (i in matrix.indices) {
            val tableRow = TableRow(context)
            for (j in matrix[i].indices) {
                if (j == 0) {
                    tableRow.addView(TextView(context).apply {
                        this.text = vertexes[i].toString()
                        setViewStyle(this)
                    })
                }
                tableRow.addView(TextView(context).apply {
                    this.text = matrix[i][j].toString()
                    setViewStyle(this)
                })
            }
            table.addView(tableRow)
        }
    }

    fun identityMatrix(): Array<Array<String>> {
        val matrix = Array(vertexes.size) { Array(links.size) { "0" } }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (vertexes[i] == links[j].vertex1 && vertexes[i] == links[j].vertex2) {
                    matrix[i][j] = "±1"
                } else if (vertexes[i] == links[j].vertex1) {
                    matrix[i][j] = "+1"
                } else if (vertexes[i] == links[j].vertex2) {
                    matrix[i][j] = "-1"
                }
            }
        }
        return matrix
    }

    fun identityMatrixCreateTable(table: TableLayout) {
        val matrix = identityMatrix()
        val context = table.context
        val firstTableRow = TableRow(context)

        firstTableRow.addView(View(context))
        links.forEach {
            firstTableRow.addView(TextView(context).apply {
                this.text = "${it.vertex1}->${it.vertex2}"
                setViewStyle(this)
            })
        }
        table.addView(firstTableRow)

        for (i in matrix.indices) {
            val tableRow = TableRow(context)
            for (j in matrix[i].indices) {
                if (j == 0) {
                    tableRow.addView(TextView(context).apply {
                        this.text = vertexes[i].toString()
                        setViewStyle(this)
                    })
                }
                tableRow.addView(TextView(context).apply {
                    this.text = matrix[i][j]
                    setViewStyle(this)
                })
            }
            table.addView(tableRow)
        }
    }

    private fun setViewStyle(view: TextView) {
        view.gravity = Gravity.CENTER
        view.textSize = 17F
        view.setPadding(11, 0, 11, 0)
    }

}