package com.eapp.rayoquiz

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RayoQuizViewModel : ViewModel() {
    var picturePositionName = "eapp.notekeeper.noteViewModel.notePosition"
    var picturePosition = POSITION_NOT_SET

    private val pictures = MutableLiveData<List<PictureInfo>>()

    init {
        pictures.value = loadPictures()
    }
    private fun loadPictures() : List<PictureInfo> = DataManager.pictures

    fun getPictures() : LiveData<List<PictureInfo>> {
        return pictures
    }
    fun saveState(outState: Bundle) {
        outState.putInt(picturePositionName, picturePosition)
    }

    fun restoreState(savedInstanceState: Bundle) {
        picturePosition = savedInstanceState.getInt(picturePositionName, POSITION_NOT_SET)
    }
}