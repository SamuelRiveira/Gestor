package dev.samu.gestor

import android.content.Context
import android.util.Log
import java.io.FileNotFoundException

class WriteReadUserPass {
    companion object {
        fun guardarUserPassArchivo(context: Context, data: String, nombreArchivo: String) {
            try {
                context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE).use { output ->
                    output.write(data.toByteArray())
                }
                Log.i("Archivo", "Datos guardados exitosamente: $data")
            } catch (e: Exception) {
                Log.e("Archivo", "Error al guardar datos: ${e.message}", e)
            }
        }

        fun leerUserPassArchivo(context: Context, nombreArchivo: String): List<String> {
            return try {
                context.openFileInput(nombreArchivo).bufferedReader().useLines { lines ->
                    lines.toList()
                }
            } catch (e: FileNotFoundException) {
                Log.i("Archivo", "Archivo no encontrado, retornando lista vacía")
                emptyList()
            } catch (e: Exception) {
                Log.e("Archivo", "Error al leer archivo: ${e.message}", e)
                emptyList()
            }
        }
    }
}
