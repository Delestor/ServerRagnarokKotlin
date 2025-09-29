package org.example

import java.net.ServerSocket

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")


    //thread { ClientHandler(server.accept()).run() }
    //thread { ClientHandler(server.accept()).run() }

    while (true) {
        val client = server.accept()
        println("Cliente conectado: ${client.remoteSocketAddress}")

        Thread {
            ClientHandler(client).run()
        }.start()
    }

}