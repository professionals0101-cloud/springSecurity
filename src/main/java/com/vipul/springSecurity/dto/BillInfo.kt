package com.vipul.springSecurity.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class BillInfo(val vendor: String?,
               val invoice: String?,
               val total: BigDecimal,
               val date: LocalDateTime?)
