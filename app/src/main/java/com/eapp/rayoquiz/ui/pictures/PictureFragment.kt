package com.eapp.rayoquiz.ui.pictures

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.eapp.rayoquiz.*
import kotlinx.android.synthetic.main.fragment_picture.*
import org.pytorch.IValue
import org.pytorch.Module
import org.pytorch.torchvision.TensorImageUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PictureFragment : Fragment() {

    private val picturaFragmentTag = this::class.simpleName

    private val args: PictureFragmentArgs by navArgs()
    private var module : Module? = null
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_picture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel : RayoQuizViewModel by activityViewModels()
        viewModel.picturePosition = args.picPosition

        val picture = DataManager.pictures[viewModel.picturePosition]

        try {
            bitmap = BitmapFactory.decodeStream(context?.assets?.open(picture.imageName))
            module = Module.load(assetFilePath(requireContext(), "model.pt"))
        } catch (e: IOException) {
            Log.e(picturaFragmentTag, "Error reading assets", e)
            activity?.finish()
        }

        pictureImageView.setImageBitmap(bitmap)

        val inputTensor =
            TensorImageUtils.bitmapToFloat32Tensor(bitmap, TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
                TensorImageUtils.TORCHVISION_NORM_STD_RGB)
        val outputTensor = module?.forward(IValue.from(inputTensor))?.toTensor()

        val scores = outputTensor?.dataAsFloatArray!!
        var maxScore = -Float.MAX_VALUE
        var maxScoreIdx = POSITION_NOT_SET

        for(i in scores.indices) {
            if (scores[i] > maxScore) {
                maxScore = scores[i]
                maxScoreIdx = i
            }
        }

        val className = ImageNetClasses.IMAGENET_CLASSES[maxScoreIdx]
        answerText.text = className
    }
}

/**
 * Copies specified asset to the file in /files app directory and returns this file absolute path.
 *
 * @return absolute file path
 */
@Throws(IOException::class)
fun assetFilePath(context: Context, assetName: String?): String? {
    val file = File(context.filesDir, assetName!!)
    if (file.exists() && file.length() > 0) {
        return file.absolutePath
    }
    context.assets.open(assetName).use { `is` ->
        FileOutputStream(file).use { os ->
            val buffer = ByteArray(4 * 1024)
            var read: Int
            while (`is`.read(buffer).also { read = it } != -1) {
                os.write(buffer, 0, read)
            }
            os.flush()
        }
        return file.absolutePath
    }
}