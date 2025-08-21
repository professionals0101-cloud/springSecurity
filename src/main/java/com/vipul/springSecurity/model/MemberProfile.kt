package com.vipul.springSecurity.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "member_profile")
data class MemberProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val memberId: Long = 0,

    @Column(name = "member_name")
    val memberName: String? = null,

    val relation: String? = null,

    @Column(nullable = false, unique = true)
    val mobile: Long,

    val email: String? = null,
    val avatarUrl: String? = null
){
    companion object {
        /** Java can call: MemberProfile.withMobile(9876543210L) */
        @JvmStatic
        fun withMobile(mobile: Long): MemberProfile =
            MemberProfile(mobile = mobile)

    }
}

