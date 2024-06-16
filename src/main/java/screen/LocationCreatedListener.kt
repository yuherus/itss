package views.admin

import model.Location

interface LocationCreatedListener {
    fun onLocationCreated(location: Location?)
}