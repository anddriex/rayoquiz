package com.eapp.rayoquiz

object DataManager {
    val pictures = ArrayList<PictureInfo>()

    init {
        initializePictures()
    }

    private fun initializePictures() {
        var picture = PictureInfo(arrayListOf("cat", "Kitten"),"cat.jpg")
        pictures.add(picture)

        picture = PictureInfo(arrayListOf("wolf", "artic wolf"), "wolf.jpg")
        pictures.add(picture)

        picture = PictureInfo(arrayListOf("fox", "zorro"), "fox.jpg")
        pictures.add(picture)
    }
}