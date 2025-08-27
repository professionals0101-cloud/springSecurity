package com.vipul.springSecurity.service.aws

import com.vipul.springSecurity.util.getUniqueFileName
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration

@Service
class S3Service(
    private val s3Presigner: S3Presigner
) {

    private val bucketName = "expenseupload"

    fun generatePreSignedUrl(fileName : String, groupId :Long) : Map<String, String>{
        val objectKey = "groups/"+groupId+"/"+ getUniqueFileName(fileName)

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey).build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest (putObjectRequest)
            .build()

        val presignedRequest = s3Presigner.presignPutObject ( presignRequest )
        val url : URL = presignedRequest.url()

        return mapOf(
            "url" to url.toString(),
            "key" to objectKey
        )
    }
}