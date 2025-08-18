package com.vipul.springSecurity.service

import com.vipul.springSecurity.mapper.GroupMapper
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupResponse
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepo: GroupRepo,
    private val groupMapper : GroupMapper
) {
    fun createGroup(request: GroupRequest,
                     userId : String ): GroupResponse {
        try {
            val group = groupMapper.mapToGroup(userId, request)
            val saved = groupRepo.save(group)
            return groupMapper.mapToDto(saved)
        }catch(e: Exception){
            e.printStackTrace()
            throw e
        }
    }
}

