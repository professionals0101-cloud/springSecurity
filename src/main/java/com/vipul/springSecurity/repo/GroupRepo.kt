package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.request.MemberDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GroupRepo : JpaRepository<GroupDtl, Long> {

    @Query("SELECT EXISTS (\n" +
            "    select 1 from group_dtl gd inner join group_member_relation gmr on gd.group_id = gmr.group_id\n" +
            "where member_id = :memberId and is_admin = true and gd.group_name =:groupName\n" +
            ");", nativeQuery = true)
    fun isGroupNameExists(@Param("groupName") groupName: String,
                          @Param("memberId") memberId: Long): Boolean
}

@Repository
interface GroupMemberRepository : JpaRepository<GroupMemberRelation, Long> {

    @Query("SELECT group_name from group_dtl gd INNER JOIN group_member_relation gmr on gd.group_id = gmr.group_id" +
            " where gmr.member_id = :userId", nativeQuery = true)
    fun findByUserId(@Param("userId") userId: Long): List<String>

}


