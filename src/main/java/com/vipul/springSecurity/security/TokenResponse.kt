package com.vipul.springSecurity.security

class TokenResponse (
    val message : String
){
    var accessToken : String =""
    var refreshToken : String= ""

    constructor(accessToken: String, refreshToken: String, message: String) : this(message) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}