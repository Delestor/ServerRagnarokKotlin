package org.example

import kotlin.collections.mutableListOf

class GlobalData {
    companion object{
        var clientCount: Int = 0
        var listClientPosition = mutableListOf<ClientPositionComponent>()
    }
}