package com.vipul.springSecurity.service.aws

import com.vipul.springSecurity.response.OperationResponse
import com.vipul.springSecurity.util.getUniqueFileName
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody.fromBytes
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration

@Service
class ImageIoService(
    private val s3Presigner: S3Presigner,
    private val s3Client: S3Client
) {

    private val bucketName = "expenseupload"

    fun generatePreSignedUrl(fileName : String, groupId :Long) : Map<String, String>{
        val path = "groups/"+groupId+"/"+ getUniqueFileName(fileName)
        val url : URL = getPresignedUrl(path)

        return mapOf(
            "url" to url.toString(),
            "key" to path
        )
    }

    fun getPresignedUrl(objectKey : String, ): URL {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest (putObjectRequest)
            .build()

        val presignedRequest = s3Presigner.presignPutObject ( presignRequest )
        return presignedRequest.url()
    }

    fun uploadIntoS3(groupId : Long, file: MultipartFile) : OperationResponse{
        val key = "groups/"+groupId+"/"+ getUniqueFileName(file.originalFilename)
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(file.contentType).build()

        s3Client.putObject(putObjectRequest, fromBytes(file.bytes))

        return OperationResponse(true, key)
    }

}