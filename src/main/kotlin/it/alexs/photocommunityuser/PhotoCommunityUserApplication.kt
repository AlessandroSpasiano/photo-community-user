package it.alexs.photocommunityuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PhotoCommunityUserApplication

fun main(args: Array<String>) {
    runApplication<PhotoCommunityUserApplication>(*args)
}
