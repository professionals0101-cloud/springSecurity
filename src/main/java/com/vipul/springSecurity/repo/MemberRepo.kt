package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.MemberProfile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberRepo : JpaRepository<MemberProfile, Long> {

    @Query("SELECT * from member_profile mp where mp.mobile =:mobileNumber", nativeQuery = true)
    fun findByMobileNumber(@Param("mobileNumber") mobileNumber:Long) : Optional<MemberProfile>

    @Query("SELECT * from member_profile mp where mp.mobile in (:mobileNumbers)", nativeQuery = true)
    fun findByMobileNumbers(@Param("mobileNumbers") mobileNumbers: List<Long>) :List<MemberProfile>

}