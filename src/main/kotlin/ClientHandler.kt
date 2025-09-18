package org.example

import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

class ClientHandler(client: Socket) {


    private val client: Socket = client
    private var isRunning: Boolean = false
    private val scanner: Scanner = Scanner(client.getInputStream())
    private val writer: PrintWriter = PrintWriter(client.getOutputStream(), true)
    private val firstClient: Boolean = true
    private var clientId: Int = -1

    fun run(){
        isRunning = true
        if(clientId == -1)
            addNewClient()

        //write("Welcome to the server.")
        while(isRunning){
            when (clientId){
                0 -> checkClientPosition(1)
                1 -> checkClientPosition(0)
                else ->{
                    println("Número de cliente no registrado: $clientId")
                }
            }

            //if(scanner.hasNext()){
                var clientPosition = GlobalData.listClientPosition.get(clientId)
                val input = scanner.nextLine()
                val coordinates = input.split(" ").filter { it.isNotBlank() }
                val x = coordinates[0].toFloat()
                val y = coordinates[1].toFloat()
                clientPosition.posX = x
                clientPosition.posY = y

                println("Coordenadas recibidas: X = $x, Y = $y, para el cliente: $clientId")

                GlobalData.listClientPosition.set(clientId, clientPosition)
            //}
        }
    }

    private fun checkClientPosition(pos: Int) {
        if(GlobalData.listClientPosition.size >= pos+1){
            println("Check client $pos position")
            var clientPosition = GlobalData.listClientPosition[pos]
            sendClientPosition(clientPosition)
        }else{
            println("Client $pos not initialized")
        }
    }

    fun sendClientPosition(clientPosition : ClientPositionComponent){
        write("SendingPosition")
        write(clientPosition.posX.toString())
        write(clientPosition.posY.toString())
    }

    fun write(message: String){
        writer.println(message)
    }

    fun addNewClient(){
        var clientPosition : ClientPositionComponent = ClientPositionComponent(GlobalData.clientCount, 0f, 0f)
        clientId = GlobalData.clientCount
        GlobalData.listClientPosition.add(clientPosition)
        GlobalData.clientCount++
    }

}