package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.dto.GroupInfo
import com.vipul.springSecurity.dto.MemberProfileDto
import com.vipul.springSecurity.enum.Role
import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.model.MemberProfile.Companion.withMobileAndName
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
        existingMembers: List<MemberProfile>,
        admin: MemberProfile
    ) : List<GroupMemberRelation> {
        val mobileToMemberPair = existingMembers.associateBy { it.mobile }
        val membersList = existingMembers.filter { !it.mobile.equals(admin.mobile) }.map { member ->
            GroupMemberRelation(
                groupId = group.groupId,
                memberId = mobileToMemberPair[member.mobile]?.memberId!!,
                role = Role.MEMBER,
                nickName = member.memberName
            )
        } + listOf(GroupMemberRelation(
            groupId = group.groupId,
            memberId = admin.memberId,
            role = Role.ADMIN,
            nickName = admin.memberName
        ))

        return membersList
    }


    fun mapToGroupInfoList(groups: List<GroupDtl>): List<GroupInfo> {
          return groups.map { group -> mapToGroupInfo(group) }
    }

     fun mapToGroupInfo(it: GroupDtl): GroupInfo {
        return GroupInfo(
            groupId = it.groupId,
            groupName = it.groupName,
            groupSpent = it.groupSpent,
            groupCurrency = it.groupCurrency,
            country = it.country
        )
    }

    fun mapToGroupInfo(groupId : Long, groupRelation: List<GroupMemberRelation>, idToMembersMap :Map<Long, MemberProfile>): GroupInfo {

        val members = groupRelation.map { relation->
            if(idToMembersMap.contains(relation.memberId)) {
                val member = idToMembersMap.get(relation.memberId)
                MemberProfileDto(
                    memberId = member!!.memberId,
                    mobile = member.mobile,
                    memberName = member.memberName
                )
            }
            else{
                throw RuntimeException("member id must exist")
            }
        }

        return GroupInfo(
            groupId = groupId,
            members = members
        )
    }

    fun mapToMembers(members: List<MemberDetails>, existingMembers: List<MemberProfile>) : List<MemberProfile> {
        val existingMobiles = existingMembers.map { it.mobile }.toSet()
        val missingMembers = members.filter { it.mobile !in existingMobiles }

        val newMembers = missingMembers.map { member-> withMobileAndName(
            mobile = member.mobile,
            memberName = member.name)
        }

        return newMembers +existingMembers
    }
}