package com.vipul.springSecurity.controller

import com.vipul.springSecurity.dto.GroupInfo
import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupCreateResponse
import com.vipul.springSecurity.response.OperationResponse
import com.vipul.springSecurity.service.BillService
import com.vipul.springSecurity.service.GroupService
import com.vipul.springSecurity.service.aws.ImageIoService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService : GroupService,
    private val billService: BillService
) {

    // Create new group
    @PostMapping
    fun createGroup(
        @RequestBody groupRequest: GroupRequest,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupCreateResponse> {
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
/*
    //  Update group details
    @PutMapping("/{groupId}")
    fun updateGroup(
        @PathVariable groupId: UUID,
        @RequestBody request: GroupRequest,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {
        val userId: String = principal.getClaim("sub")
        val updated = GroupResponse(
            id = groupId,
            name = request.name,
            description = request.description,
            createdBy = userId,
            members = request.members
        )
        return ResponseEntity.ok(updated)
    }

    //  Add member to group
    @PostMapping("/{groupId}/members")
    fun addMember(
        @PathVariable groupId: UUID,
        @RequestParam userId: UUID,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<String> {

        return ResponseEntity.ok("User $userId added to group $groupId")
    }

    // Remove member from group
    @DeleteMapping("/{groupId}/members/{userId}")
    fun removeMember(
        @PathVariable groupId: UUID,
        @PathVariable userId: UUID,
        @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<String> {
        return ResponseEntity.ok("User $userId removed from group $groupId")
    }

    //  Delete group
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: UUID,
                    @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Group $groupId deleted successfully")
    }*/


    @PostMapping("/{groupId}/upload")
    fun uploadBil(@AuthenticationPrincipal principal : Jwt,
                  @RequestParam multipartFile: MultipartFile,
                    @PathVariable groupId : Long) : ResponseEntity<OperationResponse>{
        val userId = principal.subject.toLong()
        return ResponseEntity.ok(billService.processBill(userId, groupId, multipartFile))
    }
}
