package com.vipul.springSecurity.response


data class GroupResponse(
    val groupId: Long,
    val groupName: String,
    val description: String?,
    val createdBy: String,
    val members: List<String>
)