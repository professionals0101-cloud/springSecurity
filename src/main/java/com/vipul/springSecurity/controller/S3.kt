package com.vipul.springSecurity.controller

import com.vipul.springSecurity.util.getUniqueFileName
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.PutObjectRequest

import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.net.URL
import java.time.Duration

@RestController
@RequestMapping("/s3")
class S3 {
    private val bucketName = "expenseupload"


    private val presigner : S3Presigner = S3Presigner.builder()
        .region(Region.AP_SOUTH_1)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()

    @GetMapping("/{groupId}/presigned-url")
    fun generatePreSignedUrl(@RequestParam fileName: String,
                             @PathVariable groupId : Long) : Map<String, String>{

        val objectKey = "groupId/"+ getUniqueFileName(fileName)

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey).build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest (putObjectRequest)
            .build()

        val presignedRequest = presigner.presignPutObject ( presignRequest )
        val url : URL = presignedRequest.url()

        return mapOf(
            "url" to url.toString(),
            "key" to objectKey
        )
    }
}