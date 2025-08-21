package com.vipul.springSecurity.service

import com.vipul.springSecurity.mapper.Mapper
import com.vipul.springSecurity.repo.GroupMemberRepository
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupCreateResponse
import org.apache.coyote.BadRequestException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepo: GroupRepo,
    private val groupMemberRepo : GroupMemberRepository,
    private val memberRepo: MemberRepo,
    private val mapper : Mapper
) {

    //@Transactional
    fun createGroup(request: GroupRequest,
                     userId : Long ): GroupCreateResponse {
        try {
            val user = memberRepo.findById(userId)
            if(user.isPresent) {
                if(!groupRepo.isGroupNameExists(request.name, userId)) {
                    val group = mapper.mapToGroup(userId, request)
                    val saved = groupRepo.save(group)
                    val existingMembers = memberRepo.findByMobileNumbers(request.members.map { it.mobile })
                    val groupMembers = mapper.mapToGroupMember(saved, request.members, existingMembers, user.get())
                    val savedGroupMembers = groupMemberRepo.saveAll(groupMembers)
                    return GroupCreateResponse(groupId = saved.groupId, message = "SUCCESS")
                }
                throw RuntimeException("Group name should be unique")
            }

            throw UsernameNotFoundException("Invalid user")
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    fun getAllGroupsForUserId(userId: Long) : List<String> {
      return groupMemberRepo.findByUserId(userId)
    }
}

