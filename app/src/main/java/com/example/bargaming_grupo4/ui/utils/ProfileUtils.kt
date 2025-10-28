package com.example.bargaming_grupo4.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("profile_", ".jpg", context.cacheDir)
    inputStream?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }
    val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)
    return MultipartBody.Part.createFormData("file", tempFile.name, requestBody)
}

fun saveBitmapToTempUri(context: Context, bitmap: Bitmap): Uri {
    val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
    FileOutputStream(tempFile).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return Uri.fromFile(tempFile)
}

fun CoroutineScope.uploadProfileImage(
    context: Context,
    uri: Uri,
    snackbarHostState: SnackbarHostState
) {
    launch {
        try {
            val part = uriToMultipart(context, uri)
            val response = RetrofitClient.authService.uploadProfileImage(part)

            if (response.isSuccessful) {
                snackbarHostState.showSnackbar("Imagen subida correctamente ")
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                snackbarHostState.showSnackbar("Error al subir imagen: $errorMsg")
            }

        } catch (e: Exception) {
            snackbarHostState.showSnackbar("Excepción: ${e.message}")
        }
    }
}
