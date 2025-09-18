package com.vipul.springSecurity.controller

import com.vipul.springSecurity.request.UserUpdateProfileRequest
import com.vipul.springSecurity.response.UserUpdateProfileResponse
import com.vipul.springSecurity.service.UpdateProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.oauth2.jwt.Jwt


@RestController
@RequestMapping("/updateProfile")
class UpdateProfileController(private val updateProfileService: UpdateProfileService) {
@PostMapping("/{userId}")
fun updateProfile(@AuthenticationPrincipal principal : Jwt,@RequestBody updateProfileRequest: UserUpdateProfileRequest): ResponseEntity<UserUpdateProfileResponse>{
   //
    val userId = principal.subject.toLong()
    return updateProfileService.updateProfile(userId, updateProfileRequest)

}


}