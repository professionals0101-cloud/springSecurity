package com.vipul.springSecurity.service

import com.vipul.springSecurity.mapper.Mapper
import com.vipul.springSecurity.repo.GroupMemberRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.response.OperationResponse
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepo: MemberRepo,
    private val groupMemberRepo: GroupMemberRepo,
    private val mapper : Mapper
) {
    @Transactional
    fun updateMember(memberAttributes: Map<String, Any>,
                     userId : Long ): OperationResponse {
        val member = memberRepo.findById(userId).orElseThrow { throw UsernameNotFoundException("Invalid user") }

        memberAttributes.keys.forEach { attribute ->
            when (attribute) {
                "showOnlyAdminGroups" -> {
                    val value = memberAttributes.get(attribute) as Boolean
                    if(!value) {
                        val groupRelations = groupMemberRepo.findByMobileNumber(member.mobile)
                        if(groupRelations.isNotEmpty()) {
                            val filteredRelations = groupRelations.filter { it.memberId == null }
                            if(filteredRelations.isNotEmpty()) {
                                filteredRelations.forEach { relation -> relation.memberId = member.memberId }
                                groupMemberRepo.saveAll(groupRelations)
                            }
                        }
                    }
                    member.showOnlyAdminGroups = value
                }
            }
        }
        memberRepo.save(member)
        return OperationResponse(status = true, "SUCCESS")
    }

}

