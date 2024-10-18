package cn.lsmya.smart.ktx

import android.graphics.drawable.Drawable
import android.util.Base64
import android.widget.ImageView
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

fun ImageView?.loadUrl(
    imageUrl: String?,
    baseUrl: String? = null,
    placeholder: Drawable? = null,
    error: Drawable? = null,
    isCircle: Boolean? = null,
    radius: Float? = null,
    topLeft: Float? = null,
    topRight: Float? = null,
    bottomLeft: Float? = null,
    bottomRight: Float? = null,
    defaultImage: Drawable? = null,
) {
    if (this == null) {
        return
    }
    if (defaultImage != null) {
        load(defaultImage) {
            onImageRequestBuilder(
                builder = this,
                placeholder = placeholder,
                error = error,
                isCircle = isCircle,
                radius = radius,
                topLeft = topLeft,
                topRight = topRight,
                bottomLeft = bottomLeft,
                bottomRight = bottomRight
            )
        }
        return
    }
    val regex = Regex(".*data:image/.*;base64,")
    if (imageUrl != null && imageUrl.contains(regex)) {
        val base64Str = imageUrl.replace(regex, "")
        val decodedString = Base64.decode(base64Str, Base64.DEFAULT)
        load(decodedString) {
            if (imageUrl.startsWith("data:image/svg+xml;base64")) {
                decoderFactory { result, options, _ -> SvgDecoder(result.source, options) }
            }
            onImageRequestBuilder(
                builder = this,
                placeholder = placeholder,
                error = error,
                isCircle = isCircle,
                radius = radius,
                topLeft = topLeft,
                topRight = topRight,
                bottomLeft = bottomLeft,
                bottomRight = bottomRight
            )
        }
    } else {
        if (baseUrl.isNotNullOrEmpty() && imageUrl.isNullOrEmpty()) {
            load(error) {
                onImageRequestBuilder(
                    builder = this,
                    placeholder = placeholder,
                    error = error,
                    isCircle = isCircle,
                    radius = radius,
                    topLeft = topLeft,
                    topRight = topRight,
                    bottomLeft = bottomLeft,
                    bottomRight = bottomRight
                )
            }
        } else {
            load(madeImageUrl(baseUrl, imageUrl)) {
                onImageRequestBuilder(
                    builder = this,
                    placeholder = placeholder,
                    error = error,
                    isCircle = isCircle,
                    radius = radius,
                    topLeft = topLeft,
                    topRight = topRight,
                    bottomLeft = bottomLeft,
                    bottomRight = bottomRight
                )
            }
        }
    }
}

private fun onImageRequestBuilder(
    builder: ImageRequest.Builder,
    placeholder: Drawable?,
    error: Drawable?,
    isCircle: Boolean?,
    radius: Float?,
    topLeft: Float?,
    topRight: Float?,
    bottomLeft: Float?,
    bottomRight: Float?,
) {
    builder.apply {
        crossfade(true)
        placeholder?.let {
            placeholder(it)
        }
        error?.let {
            error(it)
        }
        if (isCircle == true) {
            transformations(CircleCropTransformation())
        } else {
            if (topLeft != null || topRight != null || bottomLeft != null || bottomRight != null) {
                transformations(
                    RoundedCornersTransformation(
                        topLeft = topLeft ?: 0f,
                        topRight = topRight ?: 0f,
                        bottomLeft = bottomLeft ?: 0f,
                        bottomRight = bottomRight ?: 0f,
                    )
                )
            } else {
                radius?.let {
                    transformations(
                        RoundedCornersTransformation(it)
                    )
                }
            }
        }
    }
}

fun madeImageUrl(baseUrl: String?, imageUrl: String?): String? {
    return if ((imageUrl?.startsWith("http://") == true) || imageUrl?.startsWith("https://") == true) {
        imageUrl
    } else {
        if (baseUrl != null) {
            if (imageUrl == null) {
                baseUrl
            } else {
                if (baseUrl.endsWith("/") && imageUrl.startsWith("/")) {
                    baseUrl + imageUrl.substring(1)
                } else if (!baseUrl.endsWith("/") && !imageUrl.startsWith("/")) {
                    "$baseUrl/$imageUrl"
                } else {
                    baseUrl + imageUrl
                }
            }
        } else {
            imageUrl
        }
    }
}