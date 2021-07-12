package com.example.blog

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {
    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `블로그 페이지 제목, 콘텐츠, 상태 코드 검증`() {
        println(">> Assert blog page title, content and status code")
        val entity = restTemplate.getForEntity<String>("/")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1>blog</h1>", "Reactor")
    }

    @Test
    fun `기사 페이지 제목, 콘텐츠 및 상태 코드 검증`() {
        println(">> Assert article page title, content and status code")
        val title = "Reactor Aluminum has landed"
        val entity = restTemplate.getForEntity<String>("/article/${title.toSlug()}")

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(title, "lorem ipsum", "dolor sit amet")
    }
}