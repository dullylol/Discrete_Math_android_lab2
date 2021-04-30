package com.asustuf.discretemathlab2.model.dataclasses

class Link(var vertex1: Char = ' ', var vertex2: Char = ' ') {
    fun isOneVertexEmpty() = vertex1 == ' ' || vertex2 == ' '
}