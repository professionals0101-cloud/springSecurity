package com.vipul.springSecurity.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction_dtl")
data class TransactionDtl(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    val transactionId: Long = 0,

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "group_id")
    val groupId: Long,

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "payer_id")
    val payer_id: Long,

    val receiverId: Long? = null,
    val receiverType: String? = null,
    val receiverName: String? = null,
    val receiverAccount: String? = null,

    //@Column(columnDefinition = "jsonb")
    //val includedMembers: String? = null, // store JSON string

    val amount: Double,

    @Column(name = "created_by")
    val createdBy: Long,


    @Column(name = "created_timestamp")
    val createdTimestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_updated_by")
    val lastUpdatedBy: String? = null,

    @Column(name = "is_cash_transaction")
    val isCashTransaction: Boolean = false,

    val category: String? = null,
    val status: String? = null, // Pending | Completed | Failed | Cancelled
    val description: String? = null,
    val transactionType: String? = null, // Add | Pay

   // @Column(columnDefinition = "jsonb")
    //val metadata: String? = null,

    @Column(name = "last_updated_timestamp")
    val lastUpdatedTimestamp: LocalDateTime = LocalDateTime.now()
)
