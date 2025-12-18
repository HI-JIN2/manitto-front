package party.manitto.api

import kotlinx.serialization.Serializable

@Serializable
data class CreatePartyRequest(
    val name: String,
    val password: String
)

@Serializable
data class PartyResponse(
    val id: Long,
    val name: String? = null
)

@Serializable
data class JoinPartyRequest(
    val email: String
)

@Serializable
data class Participant(
    val id: Long,
    val email: String
)

@Serializable
data class PartyStatus(
    val matched: Boolean
)

@Serializable
data class MatchResult(
    val receiver: String
)

@Serializable
data class GoogleAuthRequest(
    val credential: String
)

@Serializable
data class AuthResponse(
    val token: String
)

