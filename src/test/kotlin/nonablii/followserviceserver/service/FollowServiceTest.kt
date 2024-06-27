package nonablii.followserviceserver.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FollowServiceTest {
    var mockMvc: MockMvc? = null
    val log = LoggerFactory.getLogger(javaClass)

    private val objectMapper = ObjectMapper()

    @Autowired
    private val wac: WebApplicationContext? = null

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider?) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac!!)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    @Order(1)
    fun postFollowUser() {
        val userName = "문가인2"
        val userId = "ansrkdls"
        mockMvc?.perform(
            post("/follow/$userName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("postFollow/success"))
    }

    @Test
    @Order(2)
    fun deleteFollowUser() {
        val userName = "문가인2"
        val userId = "ansrkdls"
        mockMvc?.perform(
            delete("/follow/$userName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId    )
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("deleteFollow/success"))
    }

    @Test
    fun getFollowInfo() {
        val userName = "문가인"
        mockMvc?.perform(
            get("/follow/$userName")
                .contentType(MediaType.APPLICATION_JSON)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("getFollowInfo/success"))
    }

    @Test
    fun getFollowingUserInfo() {
        val userName = "문가인"
        val userId = "ansrkdls"
        mockMvc?.perform(
            get("/follow/following/$userName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("getFollowing/success"))
    }

    @Test
    fun getFollowerUserInfo() {
        val userName = "문가인"
        val userId = "ansrkdls"
        mockMvc?.perform(
            get("/follow/follower/$userName")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userId", userId)
        )
            ?.andExpect(MockMvcResultMatchers.status().isOk)
            ?.andDo(document("getFollower/success"))
    }
}