package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GroupRepo : JpaRepository<GroupDtl, Long>

@Repository
interface GroupMemberRepository : JpaRepository<GroupMemberRelation, Long> {

    @Query("SELECT group_name from group_dtl gd INNER JOIN group_member_relation gmr on gd.group_id = gmr.group_id" +
            " where gmr.member_id = :userId", nativeQuery = true)
    fun findByUserId(@Param("userId") userId: String): List<String>
}


@Repository
interface MemberRepository : JpaRepository<MemberProfile, Long>


