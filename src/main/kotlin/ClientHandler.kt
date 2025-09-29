package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.net.Socket
import java.util.Scanner

class ClientHandler(client: Socket) {


    private val client: Socket = client
    private var isRunning: Boolean = false
    private val scanner: Scanner = Scanner(client.getInputStream())
    private val writer: PrintWriter = PrintWriter(client.getOutputStream(), true)
    private var currentClient: Int = -1
    private val scope = CoroutineScope(Dispatchers.IO)
    private var job : Job? = null

    fun run(){

        println("inetAddress: ${client.inetAddress}, port: ${client.port}")
        isRunning = true
        if(currentClient == -1)
            addNewClient()

        savePositionFromClient()
        //write("Welcome to the server.")
        while(isRunning){
            checkAndSendAllClientsPositions()
            Thread.sleep(10)
        }
    }

    private fun savePositionFromClient() {

        scope.launch {
            println("Escuchamos mensaje cliente $currentClient en ${Thread.currentThread().name}")
            while(isRunning) {
                try {
                    if (scanner.hasNext()) {
                        synchronized(GlobalData.listClientPosition) {
                            var clientPosition = GlobalData.listClientPosition.get(currentClient)
                            val input = scanner.nextLine()
                            val coordinates = input.split(" ").filter { it.isNotBlank() }
                            val x = coordinates[0].toFloat()
                            val y = coordinates[1].toFloat()
                            clientPosition.posX = x
                            clientPosition.posY = y

                            println("Coordenadas recibidas: X = $x, Y = $y, para el cliente: $currentClient")

                            GlobalData.listClientPosition.set(currentClient, clientPosition)
                        }
                    }
                }catch (e: Exception){
                    println("Conexión perdida con el cliente $currentClient: ${e.message}")
                    isRunning = false
                }finally {
                    //isRunning = false
                }
            }
        }
    }

    private fun checkAndSendAllClientsPositions() {
        if(GlobalData.listClientPosition.size > 1){
            //Por lo menos tiene que haber 2 clientes para poder empezar a mandar posiciones.
            /*var clientPosition = GlobalData.listClientPosition[pos]
            sendClientPosition(clientPosition)*/
            for (clientPosition in GlobalData.listClientPosition){
                if(clientPosition.clientId != currentClient){
                    sendClientPosition(clientPosition)
                }
            }
        }else{
            //Solo tenemos un cliente conectado al servidor, no hace falta mandar posiciones.
        }
    }

    fun sendClientPosition(clientPosition : ClientPositionComponent){
        //write("SendingPositionClient: ${clientPosition.clientId}")
        write(clientPosition.posX.toString())
        write(clientPosition.posY.toString())
    }

    fun write(message: String){
        writer.println(message)
    }

    fun addNewClient(){
        var clientPosition : ClientPositionComponent = ClientPositionComponent(GlobalData.clientCount, 0f, 0f)
        currentClient = GlobalData.clientCount
        GlobalData.listClientPosition.add(clientPosition)
        GlobalData.clientCount++
    }

}