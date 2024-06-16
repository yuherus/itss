package views.admin

import model.Style


interface StyleCreatedListener {
    fun onStyleCreated(style: Style?)
}