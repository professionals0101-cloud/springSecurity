package com.vipul.springSecurity.repo

import com.vipul.springSecurity.model.TransactionShare
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface TransactionShareRepo : JpaRepository<TransactionShare, Long> {

    @Query(nativeQuery = true, value = "select * from transaction_shares  where member_id = :memberId and transaction_id in (:transactionIds)")
    fun findByMemberIdAndTransactionIds(@Param("memberId") memberId : Long,
                                        @Param("transactionIds") transactionIds : List<Long>):List<TransactionShare>
}