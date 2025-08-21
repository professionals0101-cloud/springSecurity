package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.enum.Role
import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.request.MemberDetails
import org.springframework.stereotype.Component

@Component
class Mapper {

    fun mapToGroup(userId: Long, request: GroupRequest) : GroupDtl {
        return GroupDtl(
            groupName= request.name,
            purpose = request.description!!,
            createdBy = userId
        )
    }

/*    fun mapToMember(members: List<MemberDetails>, existingMembers: List<MemberProfile>) : List<MemberProfile> {
        return members.map { MemberProfile(mobile = it.mobile, memberName = it.name) }
    }*/

    fun mapToGroupMember(
        group: GroupDtl,
        members: List<MemberDetails>,
        existingMembers: List<MemberProfile>,
        admin: MemberProfile
    ) : List<GroupMemberRelation> {
        val mobileMemberPair = existingMembers.associate { m-> m.mobile to m }
        return members.map { member ->
            if (member.mobile.equals(admin.mobile)) {
                GroupMemberRelation(groupId = group.groupId,
                    memberId = mobileMemberPair[member.mobile]?.memberId,
                    isAdmin = true,
                    role = Role.ADMIN.value,
                    mobile = member.mobile
                )
            } else {
                GroupMemberRelation(groupId = group.groupId,
                    memberId = mobileMemberPair[member.mobile]?.memberId,
                    isAdmin = false,
                    role = Role.MEMBER.value,
                    mobile = member.mobile
                )
            }
        }
    }
}