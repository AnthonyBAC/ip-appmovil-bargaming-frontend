package com.example.bargaming_grupo4.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.LoginViewModel
import com.example.bargaming_grupo4.viewmodel.LoginViewModelFactory
import com.example.bargaming_grupo4.data.local.storage.UserPreferences

@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val factory = remember { LoginViewModelFactory(userPrefs) }
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val scope = rememberCoroutineScope()

    val username by userPrefs.userName.collectAsState(initial = "Usuario")
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showImageOptions by remember { mutableStateOf(false) }
    var isLoggingOut by remember { mutableStateOf(false) }


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it
            profileBitmap = null
        }
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            profileBitmap = it
            profileImageUri = null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
            .padding(16.dp)
    ) {
        if (isLoggingOut) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = username ?: "Usuario",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable {
                                showImageOptions = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            profileBitmap != null -> Image(
                                bitmap = profileBitmap!!.asImageBitmap(),
                                contentDescription = "Foto tomada",
                                modifier = Modifier.fillMaxSize().clip(CircleShape)
                            )
                            profileImageUri != null -> Image(
                                painter = rememberAsyncImagePainter(profileImageUri),
                                contentDescription = "Foto seleccionada",
                                modifier = Modifier.fillMaxSize().clip(CircleShape)
                            )
                            else -> Text("📷", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }

                Spacer(Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aquí irá el menú del perfil (ej. productos listados, configuración...)")
                }

                Spacer(Modifier.height(40.dp))

                if (isLoggedIn) {
                    Button(
                        onClick = {
                            viewModel.ejecutarLogout(
                                setLoggingOut = { isLoggingOut = it },
                                showSnackbar = { message ->
                                    snackbarHostState.showSnackbar(message)
                                },
                                navigateToAccount = {
                                    navController.navigate("account_entry") {
                                        popUpTo("profile") { inclusive = true }
                                    }
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text("Cerrar sesión")
                    }
                }
            }
        }

        if (showImageOptions) {
            AlertDialog(
                onDismissRequest = { showImageOptions = false },
                confirmButton = {},
                title = { Text("Seleccionar imagen de perfil") },
                text = {
                    Column {
                        Button(
                            onClick = {
                                showImageOptions = false
                                takePhotoLauncher.launch(null)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Tomar foto con cámara")
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                showImageOptions = false
                                pickImageLauncher.launch("image/*")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Elegir desde galería")
                        }
                    }
                }
            )
        }
    }
}