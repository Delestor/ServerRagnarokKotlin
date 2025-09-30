package org.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("SendingPositionClient")
data class ClientPositionComponent (
    override val clientId: Int,
    var posX: Float,
    var posY: Float,
    override val action: String = "SendingPositionClient"
): ServerMessage()