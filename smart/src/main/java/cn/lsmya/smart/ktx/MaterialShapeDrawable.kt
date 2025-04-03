package cn.lsmya.smart.ktx

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.AttrRes
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

@SuppressLint("ResourceType")
fun Context.createMaterialShapeDrawable(
    @AttrRes fillColorRes: Int = com.google.android.material.R.attr.colorSurface,
    elevation: Float = 3.dp2px(), cornerSize: Float = 16.dp2px()
): MaterialShapeDrawable {
    return MaterialShapeDrawable.createWithElevationOverlay(this, elevation).apply {
        shapeAppearanceModel = ShapeAppearanceModel.builder().setAllCornerSizes(cornerSize).build()
        fillColor = getColorStateList(fillColorRes)
    }
}