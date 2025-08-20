package com.vipul.springSecurity.service

import com.vipul.springSecurity.mapper.Mapper
import com.vipul.springSecurity.repo.GroupMemberRepository
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.repo.MemberRepository
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupCreateResponse
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepo: GroupRepo,
    private val groupMemberRepo : GroupMemberRepository,
    private val memberRepo: MemberRepository,
    private val mapper : Mapper
) {

    //@Transactional
    fun createGroup(request: GroupRequest,
                     userId : Long ): GroupCreateResponse {
        try {
            val group = mapper.mapToGroup(userId, request)
            val members = mapper.mapToMember(request.members)
            val groupMembers = mapper.mapToGroupMember( group, members)
            val saved = groupRepo.save(group)
            val savedMembers = memberRepo.saveAll(members)
            val savedGroupMembers = groupMemberRepo.saveAll(groupMembers)
            return GroupCreateResponse(groupId = saved.groupId, message = "SUCCESS")
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    fun getAllGroupsForUserId(userId: String) : List<String> {
      return groupMemberRepo.findByUserId(userId)
    }
}

