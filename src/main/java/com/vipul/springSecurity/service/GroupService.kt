package com.vipul.springSecurity.service

import com.vipul.springSecurity.dto.GroupInfo
import com.vipul.springSecurity.mapper.Mapper
import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.repo.GroupMemberRepo
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupCreateResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Service
class GroupService(
    private val groupRepo: GroupRepo,
    private val groupMemberRepo : GroupMemberRepo,
    private val memberRepo: MemberRepo,
    private val mapper : Mapper
) {
    @Transactional
    fun createGroup(request: GroupRequest,
                     userId : Long ): GroupCreateResponse {
        val user = memberRepo.findById(userId).orElseThrow { throw UsernameNotFoundException("Invalid user") }

        if (groupRepo.isGroupNameExists(request.name, userId)) {
            throw RuntimeException("Group name should be unique")
        }

        val group = mapper.mapToGroup(userId, request)
        val saved = groupRepo.save(group)

        val membersExistingAsUsers = memberRepo.findByMobileNumbers(request.members.map { it.mobile })
        val allMembers = mapper.mapToMembers(request.members, membersExistingAsUsers)
        memberRepo.saveAll(allMembers)
        val groupMembers = mapper.mapToGroupMember(saved, allMembers, user)
        groupMemberRepo.saveAll(groupMembers)
        return GroupCreateResponse(groupId = saved.groupId, message = "SUCCESS")
    }

    fun getAllGroupsForUserId(userId: Long) : List<GroupInfo> {
        val member = memberRepo.findById(userId).orElseThrow { throw UsernameNotFoundException("Invalid user") }

        var groupRequired = mutableListOf<Boolean>(true)
        if(!member.showOnlyAdminGroups){
            groupRequired.add(false)
        }
        val groups =  groupMemberRepo.findByUserId(userId, groupRequired)
        return  mapper.mapToGroupInfoList(groups)
    }

    fun getGroupForUserId(groupId: Long, userId: Long): GroupInfo {
        val member = memberRepo.findById(userId).orElseThrow { throw UsernameNotFoundException("Invalid user") }
        var groupRequired = mutableListOf<Boolean>(true)
        if(!member.showOnlyAdminGroups){
            groupRequired.add(false)
        }
        val groupRelation =  groupMemberRepo.findByGroupId(groupId)
        val idToMemberMap = memberRepo.findAllById(groupRelation.map{it.memberId}).associate { it.memberId to it}
        return  mapper.mapToGroupInfo(groupId, groupRelation, idToMemberMap)
    }

}

