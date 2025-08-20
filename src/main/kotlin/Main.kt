package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")

    while(true) {
        val client = server.accept()
        println("===Cliente conectado: ${client.remoteSocketAddress}===")

        while(true) {
            val output = PrintWriter(client.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(client.inputStream))

            val mensajeCliente = input.readLine() ?: break

            println("Recibido mensaje de ${client.remoteSocketAddress}: ${mensajeCliente}")

            output.println("${mensajeCliente} responde el servidor.")

            println("Mensaje enviado al ${client.remoteSocketAddress}")

            Thread.sleep(1000)
        }

        println("===Cliente desconectado: ${client.remoteSocketAddress}===")
        client.close()
    }
    server.close()
}