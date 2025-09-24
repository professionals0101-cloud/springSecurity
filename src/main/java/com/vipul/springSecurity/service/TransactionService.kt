package com.vipul.springSecurity.service

import com.vipul.springSecurity.model.GroupMemberRelation
import com.vipul.springSecurity.repo.GroupMemberRepo
import com.vipul.springSecurity.repo.GroupRepo
import com.vipul.springSecurity.repo.MemberRepo
import com.vipul.springSecurity.request.ExpenseRequest
import org.springframework.stereotype.Service

@Service
class TransactionService (
    val groupRepo : GroupRepo,
    val groupMemberRepo: GroupMemberRepo,
    val memberRepo: MemberRepo
) {

    fun createExpense(userId : Long, expenseRequest: ExpenseRequest){
        val groupMemberRelation = groupMemberRepo.findByGroupId(expenseRequest.groupId)
        validateExpenseRequest(userId, expenseRequest, groupMemberRelation)

        /**
         * 1. check if user id exists and has admin or member role on the given group.
         * 2. check if all involved members are part of that group.
         * 3. check if share for all members sum to 100%
         * 4. set create by paid by and their timestamps
         */
    }

    private fun validateExpenseRequest(
        userId: Long,
        expenseRequest: ExpenseRequest,
        groupMemberRelation: List<GroupMemberRelation>
    ) {

    }
}
