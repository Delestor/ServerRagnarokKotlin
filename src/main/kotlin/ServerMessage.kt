package org.example

import kotlinx.serialization.Serializable

@Serializable
sealed class ServerMessage {
    abstract val clientId: Int
    abstract val action: String
}