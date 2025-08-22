package com.vipul.springSecurity.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.vipul.springSecurity.model.MemberProfile
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
class GroupInfo @JvmOverloads constructor(
    val groupId: Long,
    var groupName: String,
    var groupSpent: BigDecimal = BigDecimal.ZERO,
    var groupCurrency: String = "INR",
    var country: String = "India",
    var purpose: String? = null,
    var usageType: String? = null ,// Payments | Planning | Saving
    var category: String? = null ,// Family | Friends | Colleagues | Others
    var createdBy: MemberProfile? = null,
    var createdTimestamp: LocalDateTime? = null,
    var lastUpdatedBy: Long? = null,
    var lastUpdatedTimestamp: LocalDateTime? = null
)
