package dev.samu.gestor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.samu.gestor.ui.theme.GestorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VentanaPrincipal()
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val inputStream: InputStream = assets.open("datos.txt")
                val lineList = mutableListOf<String>()

                inputStream.bufferedReader().forEachLine { lineList.add(it) }

                withContext(Dispatchers.Main) {
                    lineList.forEach { Log.i("Datos", it) }
                }
            } catch (e: Exception) {
                Log.e("AppDebug", "Error reading file")
            }
        }
    }
}


@Composable
fun VentanaPrincipal(){
    Column (
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row {
            Text(
                text = "Usuario: ",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Samu",
                modifier = Modifier.padding(16.dp)
            )
        }
        Row {
            Text(
                text = "Contraseña: ",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "1234",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}