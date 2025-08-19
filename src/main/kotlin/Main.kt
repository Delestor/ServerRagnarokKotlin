package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")

    val client = server.accept()
    println("Cliente conectado: ${client.remoteSocketAddress}")

    val output = PrintWriter(client.getOutputStream(), true)
    val input = BufferedReader(InputStreamReader(client.inputStream))

    println("Recibido mensaje del cliente: ${input.readLine()}")

    output.println("${input.readLine()} to the client.")

    client.close()
    server.close()
}