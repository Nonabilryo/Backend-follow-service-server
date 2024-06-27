package nonablii.followserviceserver.service

import nonablii.followserviceserver.client.UserClient
import nonablii.followserviceserver.dto.response.FollowInfoResponse
import nonablii.followserviceserver.dto.response.UserInfoResponse
import nonablii.followserviceserver.entity.Follow
import nonablii.followserviceserver.repository.FollowRepository
import nonablii.followserviceserver.util.error.CustomError
import nonablii.followserviceserver.util.error.ErrorState
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.log

@Service
class FollowService(val followRepository: FollowRepository, val userClient: UserClient) {
    val log = LoggerFactory.getLogger(javaClass)
    fun postFollowUser(userIdx: String, followingIdx: String) {
//        val followingIdx = userClient.getUserIdxByName(userName).idx ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)

        followRepository.findFollowByFollowerAndFollowing(UUID.fromString(userIdx), UUID.fromString(followingIdx))
            ?.let { throw CustomError(ErrorState.AREADY_FOLLOWED) }

        followRepository.save(Follow(
            follower = UUID.fromString(userIdx),
            following = UUID.fromString(followingIdx)
        ))
    }
    fun deleteFollowUser(userIdx: String, followingIdx: String) {
//        val followerIdx = userClient.getUserIdxById(userId).idx ?: throw CustomError(ErrorState.NOT_FOUND_ID)
//        val followingIdx = userClient.getUserIdxByName(userName).idx ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)

        val follow = followRepository.findFollowByFollowerAndFollowing(UUID.fromString(userIdx), UUID.fromString(followingIdx))
            ?: throw CustomError(ErrorState.NOT_FOUND_FOLLOW)
        followRepository.delete(follow)
    }

    fun getFollowInfo(userIdx: String): FollowInfoResponse {
//        val userIdx = userClient.getUserIdxByName(userName).idx ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        return FollowInfoResponse(
            follower = followRepository.countFollowByFollowing(UUID.fromString(userIdx)),
            following = followRepository.countFollowByFollower(UUID.fromString(userIdx))
        )
    }

    fun getFollowingUserInfo(userIdx: String, page: Int): Page<UserInfoResponse> {
//        val userIdx = userClient.getUserIdxByName(userName).idx ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        val follows = followRepository.findFollowsByFollowerOrderByDateDesc(UUID.fromString(userIdx), PageRequest.of(page, 100))

        log.info("total elements: " + follows.totalElements.toString())
        log.info("total pages: " + follows.totalPages.toString())
        val userInfos = PageImpl(follows.content.map { userClient.getUserInfoByIdx(it.following.toString()) }, follows.pageable, follows.totalElements)
        return userInfos
    }

    fun getFollowerUserInfo(userIdx: String, page: Int): Page<UserInfoResponse> {
//        val userIdx = userClient.getUserIdxByName(userName).idx ?: throw CustomError(ErrorState.NOT_FOUND_USERNAME)
        val follows = followRepository.findFollowsByFollowingOrderByDateDesc(UUID.fromString(userIdx), PageRequest.of(page, 100))

        log.info("total elements: " + follows.totalElements.toString())
        log.info("total pages: " + follows.totalPages.toString())
        val userInfos = PageImpl(follows.content.map { userClient.getUserInfoByIdx(it.follower.toString()) }, follows.pageable, follows.totalElements)
        return userInfos
    }
}