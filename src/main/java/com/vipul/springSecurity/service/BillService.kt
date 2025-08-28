package com.vipul.springSecurity.service

import com.vipul.springSecurity.mapper.Mapper
import com.vipul.springSecurity.response.OperationResponse
import com.vipul.springSecurity.service.aws.ImageIoService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class BillService(
    private val billDetector: BillDetector,
    private val imageIoService: ImageIoService,
    private val mapper: Mapper
) {
    fun processBill(userId: Long, groupId: Long, multipartFile: MultipartFile): OperationResponse {
        val response = imageIoService.uploadIntoS3(groupId, multipartFile)
        val billInfo = billDetector.extractBillInfo(response.info as String)
        if(null!= billInfo){
            val transaction = mapper.mapToTransaction(billInfo, groupId, userId )
            return OperationResponse(true, "SUCCESS")
        }else{
            throw RuntimeException("Invalid bill")
        }
    }
}