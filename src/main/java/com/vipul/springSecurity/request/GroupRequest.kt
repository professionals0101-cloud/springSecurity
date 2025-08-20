package com.vipul.springSecurity.request

data class GroupRequest(
    val name: String,
    val description: String?,
    val members: List<MemberDetails> // list of userIds
)