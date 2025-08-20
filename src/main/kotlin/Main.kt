package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")

    clientConnection(server)

    server.close()
}

fun clientConnection(server: ServerSocket){
    while(true) {
        Thread {
        val client = server.accept()

            println("===Iniciamos conexión con el cliente ${client.remoteSocketAddress}===")
            client.use {
                val input = BufferedReader(InputStreamReader(it.getInputStream()))
                val output = PrintWriter(it.getOutputStream(), true)
                var mensaje: String?
                while (input.readLine().also { mensaje = it } != null) {
                    println("Mensaje recibido: $mensaje")
                    output.println("Echo: $mensaje")
                }
            }
            println("===Cerramos conexión con el cliente ${client.remoteSocketAddress}===")
        }.start()
    }
}