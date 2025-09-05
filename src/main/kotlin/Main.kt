package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.util.Scanner

fun main() {
    println("Hello World!")
    val server = ServerSocket(9999)
    println("Servidor iniciado en puerto 9999")

    //clientConnection(server)
    leerDeTeclado(server)

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
            client.close()
        }.start()
    }
}

fun sendPositionToClient(x: Float, y: Float, server: ServerSocket){
    val client = server.accept()
    client.use {
        val output = PrintWriter(it.getOutputStream(), true)
        output.println("$x")
        output.println("$y")
        client.close()
    }
}

fun leerDeTeclado(server: ServerSocket){

    val scanner = Scanner(System.`in`)
    println("Programa de lectura de coordenadas (escribe 'salir' para terminar)")

    while (true) {
        print("Ingresa las coordenadas x,y (separadas por espacio): ")
        val input = scanner.nextLine()

        if (input.lowercase() == "salir") {
            println("Saliendo del programa...")
            break
        }

        try {
            val coordinates = input.split(" ").filter { it.isNotBlank() }

            if (coordinates.size == 2) {
                val x = coordinates[0].toFloat()
                val y = coordinates[1].toFloat()

                println("Coordenadas recibidas: X = $x, Y = $y")
                sendPositionToClient(x, y, server)
            } else {
                println("Error: Debes ingresar exactamente 2 coordenadas separadas por espacio")
            }

        } catch (e: NumberFormatException) {
            println("Error: Los valores deben ser números válidos")
        }
    }

    scanner.close()

}