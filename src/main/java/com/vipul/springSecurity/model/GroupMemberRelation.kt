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
import java.math.BigDecimal

@Entity
@Table(name = "group_member_relation")
data class GroupMemberRelation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,


    @Column(name = "group_id")
    val group: Long,

    @Column(name = "member_id")
    val member: Long,

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: GroupDtl,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: MemberProfile,*/

    @Column(name = "is_admin")
    val isAdmin: Boolean = false,

    val role: String? = null, // Admin | Member | Viewer

    @Column(name = "amount_added")
    val amountAdded: BigDecimal = BigDecimal.ZERO,

    @Column(name = "amount_spent")
    val amountSpent: BigDecimal = BigDecimal.ZERO,

    @Column(name = "joined_by")
    val joinedBy: String? = null, // Link | QR | Code

    @Column(name = "nick_name")
    val nickName: String? = null,

    @Column(name = "relation_color")
    val relationColor: String? = null // Green | Orange | Red
)
