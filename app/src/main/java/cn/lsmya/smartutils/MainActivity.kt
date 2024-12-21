package cn.lsmya.smartutils

import android.net.Uri
import android.util.Log
import cn.lsmya.smart.ktx.loge
import cn.lsmya.smart.ktx.registerForGetContentResult
import cn.lsmya.smart.ktx.selectSingleVideo
import cn.lsmya.smart.ktx.singleClick
import cn.lsmya.smart.utils.AppCacheUtils
import cn.lsmya.smart.vb.BaseVBActivity
import cn.lsmya.smartutils.databinding.ActivityMainBinding
import java.io.File


class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    override fun initUI() {
        super.initUI()

        getBinding().btn.singleClick {
            loge(":开始")
//            val bitmap = BitmapUtil.getBitmapByUrl(this, filesDir.path + "/img.png")
//
//            val splitImageByColor = ImageUtils.splitImageByColor(
//                bitmap,
//            )
//            loge(splitImageByColor.size)
//            if (splitImageByColor.isNotEmpty()) {
//                getBinding().image1.setImageBitmap(splitImageByColor[0])
//            }
            selectSingleVideo(enableCompress = true) {
                Log.e("===","压缩后:${AppCacheUtils.getInstance().formatFileSize(File(it).length())}")
            }
        }
    }

    private val imagePickerLauncher =
        registerForGetContentResult { result ->
            val uri: Uri? = result
            uri?.let {
                //拿到图片的uri，处理你的业务代码
//                getBinding().image.load(it)
            }
        }

    private fun openGallery() {
        imagePickerLauncher.launch("image/*")
    }


}