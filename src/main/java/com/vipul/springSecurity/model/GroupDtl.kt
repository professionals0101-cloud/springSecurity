package com.vipul.springSecurity.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "group_dtl")
data class GroupDtl(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    val groupId: Long = 0,

    @Column(name = "group_name", nullable = false)
    var groupName: String,

    @Column(name = "group_amount")
    var groupAmount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "group_spent")
    var groupSpent: BigDecimal = BigDecimal.ZERO,

    @Column(name = "group_balance")
    var groupBalance: BigDecimal = BigDecimal.ZERO,

    @Column(name = "group_currency")
    var groupCurrency: String = "INR",

    var country: String = "India",
    var purpose: String? = null,

    @Column(name = "usage_type")
    var usageType: String? = null, // Payments | Planning | Saving

    var category: String? = null, // Family | Friends | Colleagues | Others

    @Column(name = "group_max_limit")
    var groupMaxLimit: BigDecimal? = null,

    @Column(name = "daily_usage_limit")
    var dailyUsageLimit: BigDecimal? = null,

    @Column(name = "per_person_limit")
    var perPersonLimit: BigDecimal? = null,

    @Column(name = "per_transaction_limit")
    var perTransactionLimit: BigDecimal? = null,

    @Column(name = "created_by")
    var createdBy: Long,

    @Column(name = "created_timestamp")
    var createdTimestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_updated_by")
    var lastUpdatedBy: Long? = null,

    @Column(name = "last_updated_timestamp")
    var lastUpdatedTimestamp: LocalDateTime = LocalDateTime.now()
)
