package com.vipul.springSecurity.mapper

import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupResponse
import org.springframework.stereotype.Component

@Component
class GroupMapper {

    fun mapToGroup(userId: String, request: GroupRequest) : GroupDtl {
        return GroupDtl(
            groupName= request.name,
            purpose = request.description!!,
            createdBy = userId
        )
    }

    fun mapToDto(saved: GroupDtl): GroupResponse {
        return GroupResponse(
            groupId = saved.groupId,
            groupName = saved.groupName,
            description = saved.purpose,
            createdBy = saved.createdBy,
            members = emptyList()
        )
    }
    fun mapToDtoList(groups: List<GroupDtl>): List<GroupResponse> {
        return groups.map { group ->
            GroupResponse(
                groupId = group.groupId,
                groupName = group.groupName,
                description = group.purpose,
                createdBy = group.createdBy,
                members = emptyList() // later you can map members if needed
            )
        }
    }
}