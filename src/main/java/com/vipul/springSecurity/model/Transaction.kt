package com.vipul.springSecurity.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transaction")
data class Transaction @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    val transactionId: Long = 0,

    @Column(name = "group_id")
    val group: Long,

    @Column(name = "payer_id")
    val payer: Long,

    val receiverId: Long? = null,
    val receiverType: String? = null,
    val receiverName: String? = null,

    @Column(columnDefinition = "jsonb")
    val includedMembers: String? = null, // store JSON string

    val amount: BigDecimal,

    @Column(name = "created_timestamp")
    val createdTimestamp: LocalDateTime? = LocalDateTime.now(),

    @Column(name = "last_updated_by")
    val lastUpdatedBy: String? = null,

    @Column(name = "is_cash_transaction")
    val isCashTransaction: Boolean = false,

    val category: String? = null,
    val status: String? = null, // Pending | Completed | Failed | Cancelled
    val description: String? = null,
    val transactionType: String? = null, // Receive | Pay

    @Column(columnDefinition = "jsonb")
    val metadata: String? = null,

    @Column(name = "last_updated_timestamp")
    val lastUpdatedTimestamp: LocalDateTime = LocalDateTime.now()
)
