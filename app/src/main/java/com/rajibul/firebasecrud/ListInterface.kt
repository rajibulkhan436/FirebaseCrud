package com.rajibul.firebasecrud

interface ListInterface {
    fun onDeleteClick(notes: Notes ,position :Int)
    fun onUpdateClick(notes: Notes, position :Int)
}