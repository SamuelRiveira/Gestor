package dev.samu.gestor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
        userPass: String = "Ainhoa:chumino123",
        nombreArchivo: String = "userPass"
    ) {
        val context = LocalContext.current
        var outs by remember { mutableStateOf(WriteReadUserPass.leerUserPassArchivo(context, nombreArchivo))}

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .background(Color(0xFF87CEFA))
            ) {
                Text(text = "Gestor de Contraseñas")
            }

            if (outs.isNotEmpty()) {
                outs.forEachIndexed { index, elemento ->
                    if (elemento.contains(":")) {
                        val partes = elemento.split(":")
                        if (partes.size == 2) {
                            val usuario = partes[0]
                            val password = partes[1]

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray)
                                    .padding(5.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(R.drawable.perfil),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(80.dp)
                                            .padding(10.dp)
                                    )
                                    Column(modifier = Modifier) {
                                        Text(text = "cuenta ${index}", fontSize = 25.sp)
                                        Text(text = usuario, fontSize = 15.sp)
                                        Text(text = password, fontSize = 15.sp)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.papelera),
                                            contentDescription = "Eliminar",
                                            modifier = Modifier
                                                .size(33.dp)
                                                .clickable {
                                                    outs = outs.toMutableList().apply {
                                                        removeAt(index)
                                                    }.filter { it.isNotBlank() }
                                                    WriteReadUserPass.guardarUserPassArchivo(context, outs.joinToString("\n"), nombreArchivo)
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(text = "No hay contraseñas guardadas", fontSize = 20.sp)
                }
            }

            FloatingActionButton(
                onClick = {
                    outs = outs.toMutableList().apply {
                        add(userPass)
                    }.filter { it.isNotBlank() }
                    WriteReadUserPass.guardarUserPassArchivo(context, outs.joinToString("\n"), nombreArchivo)
                }
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    }
}
