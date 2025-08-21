package com.vipul.springSecurity.controller

import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupResponse
import com.vipul.springSecurity.service.GroupService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.util.*

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
        val userId = principal.subject
        val group = groupService.createGroup(groupRequest, userId);
        return ResponseEntity.ok(group)
    }


    /*//  Get single group by id
    @GetMapping("/{groupId}")
    fun getGroup(@PathVariable groupId: UUID,
                 @AuthenticationPrincipal principal : Jwt
    ): ResponseEntity<GroupResponse> {

        val userId: String = principal.getClaim("sub")
        // Dummy response for now
        val group = GroupResponse(
            id = groupId,
            name = "Trip to Goa",
            description = "Expenses for Goa Trip",
            createdBy = "",
            members = emptyList()
        )
        return ResponseEntity.ok(group)
    }

    //  List groups for a user
    @GetMapping
    fun listGroups(@AuthenticationPrincipal principal : Jwt): ResponseEntity<List<GroupResponse>> {
        val userId: String = principal.getClaim("sub")
        val groups = listOf(
            GroupResponse(UUID.randomUUID(), "Goa Trip", "Friends trip", userId, listOf(userId)),
            GroupResponse(UUID.randomUUID(), "Flat Rent", "Monthly rent split", userId, listOf(userId))
        )
        return ResponseEntity.ok(groups)
    }

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
}
