package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

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
interface GroupMemberRepo : JpaRepository<GroupMemberRelation, Long> {

    @Query("SELECT gd.* from group_dtl gd INNER JOIN group_member_relation gmr on gd.group_id = gmr.group_id" +
            " where gmr.member_id = :userId and gmr.is_admin in ( :showOnlyAdminGroups )", nativeQuery = true)
    fun findByUserId(@Param("userId") userId: Long,@Param("showOnlyAdminGroups") showOnlyAdminGroups: List<Boolean>): List<GroupDtl>

    @Query("SELECT * from group_member_relation gmr where gmr.mobile =:mobileNumber", nativeQuery = true)
    fun findByMobileNumber(@Param("mobileNumber") mobileNumber:Long): List<GroupMemberRelation>

    @Query("SELECT gmr.* from group_member_relation gmr " +
            " where gmr.group_id = :groupId", nativeQuery = true)
    fun findByGroupId(@Param("groupId") groupId : Long) : List<GroupMemberRelation>

}


