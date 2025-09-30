package com.vipul.springSecurity.request

import com.vipul.springSecurity.enum.SplitType

data class MemberShare (
    val memberId : Long,
    val percentage: Double,
    var amount: Double = 0.0
)
