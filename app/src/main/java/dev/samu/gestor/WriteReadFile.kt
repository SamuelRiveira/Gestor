package dev.samu.gestor

import android.content.Context
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

class WriteReadFile {
    companion object {

        // Método para guardar texto en un archivo
        fun guardarTextoEnArchivo(context: Context, texto: String, nombreArchivo: String): String {
            val estadoAlmacenamiento = Environment.getExternalStorageState()

            if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED || estadoAlmacenamiento == Environment.MEDIA_MOUNTED_READ_ONLY) {
                val directorio = context.filesDir  // Uso del context para obtener filesDir()
                val archivo = File(directorio, nombreArchivo)

                try {
                    val flujoSalida = FileOutputStream(archivo, true)
                    val writer = OutputStreamWriter(flujoSalida)
                    writer.append(texto + "\n")
                    writer.close()

                    return "Texto añadido en ${archivo.absolutePath}"
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "Error al guardar el archivo"
                }
            } else {
                return "No se pudo acceder al almacenamiento externo"
            }
        }

        // Método para leer el contenido de un archivo
        fun leerArchivo(context: Context, nombreArchivo: String): String {
            val estadoAlmacenamiento = Environment.getExternalStorageState()

            if (estadoAlmacenamiento == Environment.MEDIA_MOUNTED || estadoAlmacenamiento == Environment.MEDIA_MOUNTED_READ_ONLY) {
                val directorio = context.filesDir
                val archivo = File(directorio, nombreArchivo)

                try {
                    val flujoEntrada = FileInputStream(archivo)
                    val contenido = flujoEntrada.bufferedReader().use { it.readText() }
                    flujoEntrada.close()

                    return contenido
                } catch (e: Exception) {
                    e.printStackTrace()
                    return "Error al leer el archivo"
                }
            } else {
                return "No se pudo acceder al almacenamiento externo"
            }
        }
    }
}