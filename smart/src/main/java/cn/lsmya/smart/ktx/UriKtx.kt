package cn.lsmya.smart.ktx

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

fun Uri?.toPath(context: Context?): String? {
    if (this == null || context == null) {
        return null
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        return getImageAbsolutePath(context, this)
    } else {
        var cursor: Cursor? = null
        var path: String? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(this, proj, null, null, null)
            val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            path = cursor.getString(columnIndex)
        } catch (_: Exception) {
        } finally {
            cursor?.close()
        }
        return path
    }
}

fun Uri.isExternalStorageDocument(): Boolean {
    return "com.android.externalstorage.documents" == this.authority
}

fun Uri.isGooglePhotosUri(): Boolean {
    return "com.google.android.apps.photos.content" == this.authority
}

fun Uri.isDownloadsDocument(): Boolean {
    return "com.android.providers.downloads.documents" == this.authority
}

fun Uri.isMediaDocument(): Boolean {
    return "com.android.providers.media.documents" == this.authority
}

private fun getImageAbsolutePath(context: Context, imageUri: Uri): String? {
    if (DocumentsContract.isDocumentUri(context, imageUri)) {
        if (imageUri.isExternalStorageDocument()) {
            val docId = DocumentsContract.getDocumentId(imageUri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (imageUri.isDownloadsDocument()) {
            val id = DocumentsContract.getDocumentId(imageUri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"),
                id.toLong()
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (imageUri.isMediaDocument()) {
            val docId = DocumentsContract.getDocumentId(imageUri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = MediaStore.Images.Media._ID + "=?"
            val selectionArgs = arrayOf(split[1])
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    } // MediaStore (and general)
    else if ("content".equals(imageUri.scheme, ignoreCase = true)) {
        // Return the remote address
        if (imageUri.isGooglePhotosUri()) return imageUri.lastPathSegment
        return getDataColumn(context, imageUri, null, null)
    } else if ("file".equals(imageUri.scheme, ignoreCase = true)) {
        return imageUri.path
    }
    return null
}

private fun getDataColumn(
    context: Context,
    uri: Uri?,
    selection: String?,
    selectionArgs: Array<String>?
): String? {
    if (uri == null) {
        return null
    }
    var cursor: Cursor? = null
    val column = MediaStore.MediaColumns.DATA
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    } finally {
        cursor?.close()
    }
    return null
}
