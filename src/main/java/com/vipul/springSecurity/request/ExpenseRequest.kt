package com.vipul.springSecurity.request

class ExpenseRequest (
    val groupId : Long,
    val involvedMembersId : List<InvolvedMember>,
    val amount : Double,
    val paidBy : Long,
    val createdById : Long,
    val lastUpdatedById : Long,
    val receiverDetails : ReceiverDetail,
    val category: String? = null,
    val status: String? = null, // Pending | Completed | Failed | Cancelled
    val description: String? = null,
    val transactionType: String? = null, // Add | Pay
)
