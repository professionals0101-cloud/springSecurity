package com.vipul.springSecurity.controller

import com.vipul.springSecurity.dto.GroupInfo
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.request.MemberDetails
import com.vipul.springSecurity.response.GroupResponse
import com.vipul.springSecurity.service.GroupService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService : GroupService
) {

    // Create new group
    @PostMapping
    fun createGroup(
        @RequestBody groupRequest: GroupRequest,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {
        val userId = principal.subject.toLong()
        val group = groupService.createGroup(groupRequest, userId);
        return ResponseEntity.ok(group)
    }

    //  Get single group by id
    @GetMapping("/{groupId}")
    fun getGroup(@PathVariable groupId: Long,
                 @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupInfo> {
        val userId = principal.subject.toLong()
        val group = groupService.getGroupForUserId(groupId, userId)
        return ResponseEntity.ok(group)
    }

    // List groups for a user
    @GetMapping
    fun listGroups(@AuthenticationPrincipal principal : Jwt): ResponseEntity<List<GroupInfo>> {
        val userId = principal.subject.toLong()
        val groups = groupService.getAllGroupsForUserId(userId)
        return ResponseEntity.ok(groups)
    }

    // Add member group details
    @PostMapping("/{groupId}/member")
    fun addMember(
        @PathVariable groupId: Long,
        @RequestBody member: MemberDetails,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {
        val userId: String = principal.getClaim("sub")
        val groupResponse = null;
        return ResponseEntity.ok(groupResponse)
    }

    //delete member group details
    @DeleteMapping("/{groupId}/member/{memberId}")
    fun deleteMember(
        @PathVariable groupId: Long,
        @PathVariable memberId: Long,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {
        val userId: String = principal.getClaim("sub")
        val groupResponse = null;
        return ResponseEntity.ok(groupResponse)
    }

    //  Delete group
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: Long,
                    @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {
        return ResponseEntity.ok(GroupResponse(message = "SUCCESS", groupId = groupId))
    }
}
