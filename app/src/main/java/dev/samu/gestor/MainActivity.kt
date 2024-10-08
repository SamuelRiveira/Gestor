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
        modifier: Modifier,
        userPass: String = "Samu:contra1234",
        nombreArchivo: String = "userPass"
    ) {
//        val context = LocalContext.current
//        Column {
//            Button(
//                onClick = {
//                    var outs = WriteReadUserPass.guardarUserPassArchivo(context, userPass, nombreArchivo)
//                    Log.i("prueba", outs.toString())
//                }
//            ) {
//                Text(
//                    text = "Guardar Archivo"
//                )
//            }
//
//            Button(
//                onClick = {
//                    var outs = WriteReadUserPass.leerUserPassArchivo(context, nombreArchivo)
//                    Log.i("prueba", outs.toString())
//                }
//            ) {
//                Text(
//                    text = "Leer"
//                )
//            }
//        }
        Column(
            modifier = Modifier,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .background(Color(0xFF87CEFA))
            ) {
                Text(
                    text = "Gestor de Contraseñas",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray )
                    .padding(5.dp)
            ) {
                Row(verticalAlignment = CenterVertically){
                    Image(
                        painter = painterResource(R.drawable.perfil),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(10.dp)
                    )
                    Column(
                        modifier = Modifier
                    ) {
                        Text(text = "Amazon", fontSize = 25.sp)
                        Text(text = "Usuario", fontSize = 15.sp)
                        Text(text = "Contraseña", fontSize = 15.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Image(
                            painter = painterResource(R.drawable.papelera),
                            contentDescription = "",
                            modifier = Modifier
                                .size(33.dp)
                        )
                    }
                }
            }
        }
    }
}