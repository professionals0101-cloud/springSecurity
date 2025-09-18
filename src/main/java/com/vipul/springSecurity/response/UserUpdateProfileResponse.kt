package com.vipul.springSecurity.response

data class UserUpdateProfileResponse(
    val status : String,
    val message : String,
    val memberId : Long,
    val memberName : String,
    val email  : String,
    val avatarUrl  : String,
)
