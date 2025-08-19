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
        println("Cliente conectado: ${client.remoteSocketAddress}")

        while(true) {
            val output = PrintWriter(client.getOutputStream(), true)
            val input = BufferedReader(InputStreamReader(client.inputStream))

            println("Recibido mensaje del cliente: ${input.readLine()}")

            output.println("${input.readLine()} enviado al cliente.")

            Thread.sleep(1000)
        }

        client.close()
    }
    server.close()
}