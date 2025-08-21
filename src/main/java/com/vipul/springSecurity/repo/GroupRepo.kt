package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.GroupDtl
import com.vipul.springSecurity.model.GroupMemberRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepo : JpaRepository<GroupDtl, Long>

@Repository
interface GroupMemberRepository : JpaRepository<GroupMemberRelation, Long>

