package nonablii.followserviceserver.client

import nonablii.followserviceserver.client.dto.response.UserIdxResponse
import nonablii.followserviceserver.dto.response.UserInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "\${gateway.server.adress}/user")
interface UserClient {
    @GetMapping("/NameToIdx/{userName}")
    fun getUserIdxByName(@PathVariable("userName") userName: String): UserIdxResponse
    @GetMapping("/IdToIdx/{userId}")
    fun getUserIdxById(@PathVariable("userId") userId: String): UserIdxResponse

    @GetMapping("/IdxToUserInfo/{userIdx}")
    fun getUserInfoByIdx(@PathVariable("userIdx") userIdx: String): UserInfoResponse
}

