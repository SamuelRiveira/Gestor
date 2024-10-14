package dev.samu.gestor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.samu.gestor.ui.theme.GestorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SaveTextToFile(modifier = Modifier)
                }
            }
        }
    }

    @Composable
    fun SaveTextToFile(
        modifier: Modifier = Modifier,
        nombreArchivo: String = "userPass"
    ) {
        val context = LocalContext.current
        var outs by remember { mutableStateOf(WriteReadUserPass.leerUserPassArchivo(context, nombreArchivo).toMutableList()) }
        var showDialog by remember { mutableStateOf(false) }
        var isEditing by remember { mutableStateOf(false) }
        var currentIndex by remember { mutableStateOf(-1) }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val secretKey = CryptoHelper.getOrCreateKey(context)

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().heightIn(70.dp).background(Color(0xFF2A9AFB))
                ) {
                    Text(text = "Gestor de Contraseñas", fontSize = 27.sp, color = Color.Black)
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(bottom = 80.dp)
                ) {
                    items(outs.size) { index ->
                        val partes = outs[index].split(":")
                        if (partes.size == 2) {
                            val usuario = partes[0]
                            val contrasenaEncriptada = partes[1]

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(R.drawable.perfil),
                                    contentDescription = "Perfil",
                                    modifier = Modifier.size(80.dp).padding(10.dp)
                                )
                                Column(modifier = Modifier) {
                                    Text(text = "Cuenta $index", fontSize = 25.sp, color = Color.Black)
                                    Text(text = usuario, fontSize = 15.sp, color = Color.Black)
                                    Text(text = "********", fontSize = 15.sp, color = Color.Black)
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth().heightIn(70.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.editar),
                                        contentDescription = "Editar",
                                        modifier = Modifier.size(40.dp).padding(end = 10.dp).clickable {
                                            username = usuario
                                            password = CryptoHelper.decrypt(contrasenaEncriptada, secretKey)
                                            currentIndex = index
                                            isEditing = true
                                            showDialog = true
                                        }
                                    )

                                    Image(
                                        painter = painterResource(R.drawable.papelera),
                                        contentDescription = "Eliminar",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(end = 10.dp)
                                            .clickable {
                                                outs = outs.toMutableList().apply { removeAt(index) }
                                                outs.filter { it.isNotEmpty() }
                                                WriteReadUserPass.guardarUserPassArchivo(context, outs.joinToString("\n"), nombreArchivo)
                                            }
                                    )
                                }
                            }
                            HorizontalDivider(thickness = 1.dp)
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = {
                    username = ""
                    password = ""
                    isEditing = false
                    showDialog = true
                },
                containerColor = Color(0xFF2A9AFB),
                contentColor = Color.Black,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Crear")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = if (isEditing) "Editar cuenta" else "Crear cuenta") },
                    text = {
                        Column {
                            TextField(
                                value = username,
                                onValueChange = { username = it },
                                placeholder = { Text(text = "Usuario") }
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(8.dp)
                            )
                            TextField(
                                value = password,
                                onValueChange = { password = it },
                                placeholder = { Text(text = "Contraseña") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                val contrasenaEncriptada = CryptoHelper.encrypt(password, secretKey)

                                if (isEditing) {
                                    outs[currentIndex] = "$username:$contrasenaEncriptada"
                                } else {
                                    outs.add("$username:$contrasenaEncriptada"
                                    )
                                }
                                WriteReadUserPass.guardarUserPassArchivo(context, outs.joinToString("\n"), nombreArchivo)
                            }
                        ) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
