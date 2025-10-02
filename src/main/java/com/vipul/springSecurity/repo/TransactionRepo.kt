package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.TransactionDtl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepo : JpaRepository<TransactionDtl, Long> {

    fun findByGroupId(groupId : Long) : List<TransactionDtl>

}