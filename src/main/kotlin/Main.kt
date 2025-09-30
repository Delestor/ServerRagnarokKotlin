package org.example

import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")

    while (true) {
        val client = server.accept()
        println("Cliente conectado: ${client.remoteSocketAddress}")

        Thread {
            ClientHandler(client).run()
        }.start()
    }

    //testFormatJSON(server)

}

private fun testFormatJSON(server: ServerSocket) {
    val client = server.accept()
    val writer: PrintWriter = PrintWriter(client.getOutputStream(), true)

    val currentClient = 33
    val newClientId = ClientIdComponent(currentClient)

    //writer.println(newClientId)

    val json = JsonConfig.instance.encodeToString(newClientId)

    println(json)

    writer.println(json)
    writer.flush()
}

object JsonConfig {
    val instance = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
}