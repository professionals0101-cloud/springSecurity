package com.vipul.springSecurity.util

fun getUniqueFileName(fileName : String): String{
    val identifier = generateUniqueNumber()
    return identifier.toString()+"_"+fileName;
}

fun generateUniqueNumber(): Long {
    // Get current time in milliseconds
    val currentMillis = System.currentTimeMillis()

    // Optional: append a random 3-digit number to reduce chance of collision in very fast calls
    val randomSuffix = (100..999).random()

    // Combine timestamp and random suffix to get a unique number
    return "$currentMillis$randomSuffix".toLong()
}
