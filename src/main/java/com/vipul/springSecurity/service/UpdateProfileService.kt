package com.vipul.springSecurity.service

import com.vipul.springSecurity.model.MemberProfile
import com.vipul.springSecurity.repo.GroupMemberRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.request.UserUpdateProfileRequest
import com.vipul.springSecurity.response.UserUpdateProfileResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service
import org.springframework.security.oauth2.jwt.Jwt
import software.amazon.awssdk.services.textract.model.UpdateAdapterRequest


@Service
class UpdateProfileService(val memberRepo: MemberRepo) {


    fun updateProfile(
        userId: Long,
        userUpdateProfileRequest: UserUpdateProfileRequest
    ): ResponseEntity<UserUpdateProfileResponse> {

        val existingMember = memberRepo.findById(userId).orElseThrow {
            IllegalArgumentException("User with id $userId not found")
        }
        val entity= MemberProfile(
            memberName = userUpdateProfileRequest.memberName,
            email = userUpdateProfileRequest.memberEmail,
            avatarUrl = userUpdateProfileRequest.memberAvatar,
            memberId = userId,
            showOnlyAdminGroups = true,
            mobile =userUpdateProfileRequest.mobile
        )
        memberRepo.save(entity)
        // build response with message
        var response = UserUpdateProfileResponse(
            status = "success",
            message = "Profile updated successfully",
            memberId = userId,
            memberName = userUpdateProfileRequest.memberName,
            email = userUpdateProfileRequest.memberEmail,
            avatarUrl = userUpdateProfileRequest.memberAvatar
        )

        return ResponseEntity.ok(
            UserUpdateProfileResponse(
                status = "success",
                message = "Profile updated successfully",
                memberId = userId,
                memberName = userUpdateProfileRequest.memberName,
                email = userUpdateProfileRequest.memberEmail,
                avatarUrl = userUpdateProfileRequest.memberAvatar
            )
        )


    }
}