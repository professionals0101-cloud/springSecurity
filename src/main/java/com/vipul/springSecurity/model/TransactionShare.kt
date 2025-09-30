package com.vipul.springSecurity.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table(name = "transaction_shares")
@IdClass(TransactionShareId::class)
class TransactionShare(

    @Id
    @Column(name ="transaction_id")
    val transactionId : Long,

    @Id
    @Column(name ="member_id")
    val memberId    :   Long,

    @Column(name ="share_amount")
    val shareAmount : Double
)

data class TransactionShareId(
    val transactionId: Long = 0,
    val memberId: Long = 0
) : Serializable
