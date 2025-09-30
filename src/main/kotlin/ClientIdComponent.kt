package org.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("NewClientId")
data class ClientIdComponent (
    override val clientId: Int,
    override val action: String = "NewClientId"
): ServerMessage()