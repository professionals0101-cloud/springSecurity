package com.vipul.springSecurity.service

import com.vipul.springSecurity.dto.BillInfo
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.textract.TextractClient
import software.amazon.awssdk.services.textract.model.AnalyzeExpenseRequest
import software.amazon.awssdk.services.textract.model.AnalyzeExpenseResponse
import software.amazon.awssdk.services.textract.model.Document
import software.amazon.awssdk.services.textract.model.S3Object
import java.math.BigDecimal
import java.time.LocalDateTime


@Service
//@Profile("dev")
class BillDetector(
    private val textractClient: TextractClient
) {

    fun extractBillInfo(imagePath: String) : BillInfo? {

        var vendor : String =""
        var totalAmount : BigDecimal = BigDecimal.ZERO
        var date : LocalDateTime = LocalDateTime.now()

        //val client: TextractClient = TextractClient.create()

        val request: AnalyzeExpenseRequest? = AnalyzeExpenseRequest.builder()
            .document (
                Document.builder()
                    .s3Object(
                        S3Object.builder()
                            .bucket("expenseupload")
                            .name(imagePath)
                            .build()
                    )
                    .build()
            ).build()

        val response: AnalyzeExpenseResponse = textractClient.analyzeExpense(request)

        response.expenseDocuments().forEach({ doc ->
            if(doc.hasSummaryFields() && doc.summaryFields().isNotEmpty()) {
                doc.summaryFields().forEach({ field ->
                    val type = field.type().text()
                    val value = if (field.valueDetection() != null) field.valueDetection().text() else ""
                    if ("VENDOR_NAME".equals(type)) {
                        vendor = value
                    } else if ("TOTAL".equals(type)) {
                        val (_, amount) = splitCurrencyAndAmount(field.valueDetection()?.text()!!)
                        totalAmount = amount.toBigDecimal()
                    } else if ("INVOICE_RECEIPT_DATE".equals(type)) {
                        //date = LocalDateTime.parse(value)
                    }
                })
            }
            else{
                throw RuntimeException("Invalid bill")
            }
        })

        return BillInfo(vendor= vendor, invoice = "", total = totalAmount, date = date)

    }

    fun splitCurrencyAndAmount(text: String): Pair<String, String> {
        // Regex to match currency symbols and amount
        val regex = Regex("([\\p{Sc}])\\s?([0-9,.]+)")
        val match = regex.find(text)

        return if (match != null) {
            val currency = match.groupValues[1]  // e.g. "$" or "₹"
            val amount = match.groupValues[2].replace(Regex("[^\\d.]"), "") // e.g. "2,177.00"
            Pair(currency, amount)
        } else {
            Pair("", text) // fallback if regex doesn't match
        }
    }
}