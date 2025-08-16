package com.vipul.springSecurity.response

import java.util.UUID

data class GroupResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val createdBy: UUID,
    val members: List<UUID>
)