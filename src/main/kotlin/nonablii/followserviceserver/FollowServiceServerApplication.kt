package nonablii.followserviceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients(basePackages = ["nonablii.followserviceserver.client"])
@SpringBootApplication
class FollowServiceServerApplication

fun main(args: Array<String>) {
	runApplication<FollowServiceServerApplication>(*args)
}
