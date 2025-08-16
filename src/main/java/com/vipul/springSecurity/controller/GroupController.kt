package com.vipul.springSecurity.controller

import com.vipul.springSecurity.request.GroupRequest
import com.vipul.springSecurity.response.GroupResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/groups")
class GroupController {

    // Create new group
    @PostMapping
    fun createGroup(
        @RequestBody request: GroupRequest,
        @RequestHeader("userId") userId: UUID
    ): ResponseEntity<GroupResponse> {
        val groupId = UUID.randomUUID()
        val group = GroupResponse(
            id = groupId,
            name = request.name,
            description = request.description,
            createdBy = userId,
            members = request.members + userId // creator auto added
        )
        return ResponseEntity.ok(group)
    }

    //  Get single group by id
    @GetMapping("/{groupId}")
    fun getGroup(@PathVariable groupId: UUID): ResponseEntity<GroupResponse> {
        // Dummy response for now
        val group = GroupResponse(
            id = groupId,
            name = "Trip to Goa",
            description = "Expenses for Goa Trip",
            createdBy = UUID.randomUUID(),
            members = listOf(UUID.randomUUID(), UUID.randomUUID())
        )
        return ResponseEntity.ok(group)
    }

    //  List groups for a user
    @GetMapping
    fun listGroups(@RequestHeader("userId") userId: UUID): ResponseEntity<List<GroupResponse>> {
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
        @RequestBody request: GroupRequest
    ): ResponseEntity<GroupResponse> {
        val updated = GroupResponse(
            id = groupId,
            name = request.name,
            description = request.description,
            createdBy = UUID.randomUUID(),
            members = request.members
        )
        return ResponseEntity.ok(updated)
    }

    //  Add member to group
    @PostMapping("/{groupId}/members")
    fun addMember(
        @PathVariable groupId: UUID,
        @RequestParam userId: UUID
    ): ResponseEntity<String> {
        return ResponseEntity.ok("User $userId added to group $groupId")
    }

    // Remove member from group
    @DeleteMapping("/{groupId}/members/{userId}")
    fun removeMember(
        @PathVariable groupId: UUID,
        @PathVariable userId: UUID
    ): ResponseEntity<String> {
        return ResponseEntity.ok("User $userId removed from group $groupId")
    }

    //  Delete group
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: UUID): ResponseEntity<String> {
        return ResponseEntity.ok("Group $groupId deleted successfully")
    }
}
