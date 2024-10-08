package dev.samu.gestor

import android.content.Context

class WriteReadUserPass {
    companion object {
        public fun guardarUserPassArchivo(context: Context, userPass: String, nombreArchivo: String): String {
            var outs = WriteReadFile.guardarTextoEnArchivo(context, userPass,  nombreArchivo)
            return outs
        }
        public fun leerUserPassArchivo(context: Context, nombreArchivo: String): List<String> {
            var outs = WriteReadFile.leerArchivo(context, nombreArchivo)
            var lista: List<String> = outs.split("\n")
            return lista
        }
    }
}