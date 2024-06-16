package views.admin

import model.SampleTour


interface SampleTourCreatedListener {
    fun onSampleTourCreated(sampleTour: SampleTour?)
}