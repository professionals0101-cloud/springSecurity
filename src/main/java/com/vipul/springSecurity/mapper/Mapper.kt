package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.enum.Role
import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.request.MemberDetails
import com.vipul.springSecurity.response.GroupCreateResponse
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

    fun mapToMember(members : List<MemberDetails>) : List<MemberProfile> {
        return members.map { MemberProfile(mobile = it.mobile, memberName = it.name) }
    }

    fun mapToGroupMember(group: GroupDtl, members: List<MemberProfile>) : List<GroupMemberRelation> {
        return members.map { member ->
            if (member.memberName.equals(group.createdBy)) {
                GroupMemberRelation(group = group.groupId,
                    member = member.memberId,
                    isAdmin = true,
                    role = Role.ADMIN.value
                )
            } else {
                GroupMemberRelation(group = group.groupId,
                    member = member.memberId,
                    isAdmin = true,
                    role = Role.MEMBER.value
                )
            }
        }
    }
}