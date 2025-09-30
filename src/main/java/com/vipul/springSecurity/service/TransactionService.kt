package com.vipul.springSecurity.service

import com.vipul.springSecurity.enum.Role
import com.vipul.springSecurity.enum.SplitType
import com.vipul.springSecurity.mapper.TransactionMapper
import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.model.TransactionShare
import com.vipul.springSecurity.repo.GroupMemberRepo
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.repo.TransactionRepo
import com.vipul.springSecurity.repo.TransactionShareRepo
import com.vipul.springSecurity.request.ExpenseRequest
import com.vipul.springSecurity.request.MemberShare
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TransactionService (
    val groupMemberRepo: GroupMemberRepo,
    val transactionRepo: TransactionRepo,
    val transactionShareRepo: TransactionShareRepo,
    val transactionMapper : TransactionMapper
) {

    @Transactional
    fun createExpense(userId : Long, expenseRequest: ExpenseRequest){
        val groupMemberRelation = groupMemberRepo.findByGroupId(expenseRequest.groupId)
        validateExpenseRequest(userId, expenseRequest, groupMemberRelation)
        validateAndCalculateShares(expenseRequest.amount, expenseRequest.memberShares, expenseRequest.splitType)
        val transaction = transactionRepo.save(transactionMapper.mapToTransaction(userId, expenseRequest))
        transactionShareRepo.saveAll(
            transactionMapper.mapToTransactionShare(userId, transaction.transactionId, expenseRequest)
        )
    }

    private fun validateExpenseRequest(
        userId: Long,
        expenseRequest: ExpenseRequest,
        groupMemberRelation: List<GroupMemberRelation>
    ) {
        if(groupMemberRelation.isEmpty()){
            throw RuntimeException("Invalid group")
        }
        val memberMap = groupMemberRelation.associate { groupMemberRelation -> groupMemberRelation.memberId to groupMemberRelation}
        expenseRequest.memberShares.forEach { involvedMember ->
            run {
                if (memberMap.contains(involvedMember.memberId)) {
                    if (involvedMember.memberId == userId) {
                        val member = memberMap.get(userId)
                        //* 1. check if user id exists and has admin or member role on the given group.
                        if (!(member!!.role == Role.ADMIN || member.role == Role.MEMBER)) {
                            throw RuntimeException("Not enough rights for expense creation")
                        }
                    }
                } else {
                    throw RuntimeException("Invalid member ids are passed")
                }
            }
        }
    }


    fun validateAndCalculateShares(totalAmount: Double, shares: List<MemberShare>, type: SplitType){
        when (type) {
            SplitType.PERCENTAGE -> {
                val totalPercentage = shares.sumOf { it.percentage }
                if ("%.2f".format(totalPercentage).toDouble() != 100.0) {
                    throw IllegalArgumentException("Total percentage must equal 100%. Found: $totalPercentage%")
                }

                shares.forEach { share ->
                    share.amount = "%.2f".format(totalAmount * share.percentage / 100).toDouble()
                }

                val roundedTotal = shares.sumOf { it.amount }
                val difference = "%.2f".format(totalAmount - roundedTotal).toDouble()
                if (difference != 0.0) {
                    shares.maxByOrNull { it.amount }?.let { it.amount += difference }
                }
            }

            SplitType.EXACT -> {
                val totalExact = shares.sumOf { it.amount }
                if ("%.2f".format(totalExact).toDouble() != "%.2f".format(totalAmount).toDouble()) {
                    throw IllegalArgumentException("Sum of exact amounts must equal total. Found: ₹$totalExact")
                }

                shares.forEach { share ->
                    share.amount = "%.2f".format(share.amount).toDouble()
                }
            }
        }
    }
}

