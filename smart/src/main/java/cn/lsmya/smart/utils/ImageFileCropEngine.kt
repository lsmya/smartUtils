package cn.lsmya.smart.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.luck.picture.lib.engine.CropFileEngine
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine

class ImageFileCropEngine : CropFileEngine {
    override fun onStartCrop(
        fragment: Fragment?,
        srcUri: Uri?,
        destinationUri: Uri?,
        dataSource: ArrayList<String>?,
        requestCode: Int
    ) {
        val options = UCrop.Options()
        val uCrop = UCrop.of(srcUri!!, destinationUri!!, dataSource)
        uCrop.withOptions(options)
        uCrop.setImageEngine(object : UCropImageEngine {
            override fun loadImage(context: Context, url: String, imageView: ImageView) {
                if (!ImageLoaderUtils.assertValidRequest(context)) {
                    return
                }
                imageView.load(url) {
                    size(180, 180)
                }
            }

            override fun loadImage(
                context: Context,
                url: Uri,
                maxWidth: Int,
                maxHeight: Int,
                call: UCropImageEngine.OnCallbackListener<Bitmap>?
            ) {
                val request = ImageRequest.Builder(context)
                    .data(url)
                    .target { drawable ->
                        call?.onCall(drawable.toBitmap())
                    }
                    .build()
                context.imageLoader.enqueue(request)
            }
        })
        uCrop.start(fragment!!.requireActivity(), fragment, requestCode)
    }
}