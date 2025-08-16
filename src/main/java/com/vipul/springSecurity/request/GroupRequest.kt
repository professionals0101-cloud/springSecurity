package com.vipul.springSecurity.request

import java.util.UUID

data class GroupRequest(
    val name: String,
    val description: String?,
    val members: List<UUID> // list of userIds
)