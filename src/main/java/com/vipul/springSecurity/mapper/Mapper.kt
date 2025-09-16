package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.dto.BillInfo
import com.vipul.springSecurity.dto.GroupInfo
import com.vipul.springSecurity.dto.MemberProfileDto
import com.vipul.springSecurity.enum.Role
import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.model.Transaction
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
        val mobileToMemberPair = existingMembers.associateBy { it.mobile }
        val membersList = members.filter { !it.mobile.equals(admin.mobile) }.map { member ->
            GroupMemberRelation(
                groupId = group.groupId,
                memberId = mobileToMemberPair[member.mobile]?.memberId,
                isAdmin = false,
                role = Role.MEMBER.value,
                mobile = member.mobile,
                nickName = member.name
            )
        } + listOf(GroupMemberRelation(
            groupId = group.groupId,
            memberId = admin.memberId,
            isAdmin = true,
            role = Role.ADMIN.value,
            mobile = admin.mobile,
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

    fun mapToGroupInfo(groupId : Long, groupRelation: List<GroupMemberRelation>, mobileToMembersMap :Map<Long, MemberProfile>): GroupInfo {

        val members = groupRelation.map { relation->
            if(mobileToMembersMap.contains(relation.mobile)){
                val member = mobileToMembersMap.get(relation.mobile)
                MemberProfileDto(
                    memberId = member!!.memberId,
                    mobile = member.mobile,
                    memberName = relation.nickName
                )
            }else{
                MemberProfileDto(mobile = relation.mobile)
            }
        }

        return GroupInfo(
            groupId = groupId,
            members = members
        )
    }

    fun mapToTransaction(billInfo: BillInfo, groupId: Long, userId: Long): Transaction {
        return Transaction(
            amount = billInfo.total,
            receiverName = billInfo.vendor,
            createdTimestamp = billInfo.date,
            isCashTransaction = false,
            group = groupId,
            payer = userId
        )
    }
}