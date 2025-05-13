package cn.lsmya.smart.ktx

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.random.Random

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
            if (id.isAllNum()) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    id.toLong()
                )
                return getDataColumn(context, contentUri, null, null)
            } else {
                return uriToFileQ(context, imageUri)?.path
            }
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

private fun uriToFileQ(context: Context, uri: Uri): File? =
    if (uri.scheme == ContentResolver.SCHEME_FILE)
        File(requireNotNull(uri.path))
    else if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        //把文件保存到沙盒
        val contentResolver = context.contentResolver
        val displayName = "${System.currentTimeMillis()}${Random.nextInt(0, 9999)}.${
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(contentResolver.getType(uri))
        }"
        val ios = contentResolver.openInputStream(uri)
        if (ios != null) {
            File("${context.cacheDir.absolutePath}/$displayName")
                .apply {
                    val fos = FileOutputStream(this)
                    copyFieUriToInnerStorage(context, uri, this)
                    fos.close()
                    ios.close()
                }
        } else null
    } else null

/**
 * 通过uri拷贝外部存储的文件到自己包名的目录下
 *
 * @param uri
 * @param destFile
 */
private fun copyFieUriToInnerStorage(context: Context, uri: Uri, destFile: File) {
    var inputStream: InputStream? = null
    var fileOutputStream: FileOutputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        if (destFile.exists()) {
            destFile.delete()
        }
        fileOutputStream = FileOutputStream(destFile)
        val buffer = ByteArray(4096)
        var redCount: Int
        while ((inputStream!!.read(buffer).also { redCount = it }) >= 0) {
            fileOutputStream.write(buffer, 0, redCount)
        }
    } catch (e: java.lang.Exception) {
    } finally {
        try {
            if (fileOutputStream != null) {
                fileOutputStream.flush()
                fileOutputStream.fd.sync()
                fileOutputStream.close()
            }
            inputStream?.close()
        } catch (e: java.lang.Exception) {
        }
    }
}