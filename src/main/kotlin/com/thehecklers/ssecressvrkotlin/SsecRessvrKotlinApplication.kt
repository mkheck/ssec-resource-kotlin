package com.thehecklers.ssecressvrkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SsecRessvrKotlinApplication

fun main(args: Array<String>) {
	runApplication<SsecRessvrKotlinApplication>(*args)
}

@EnableWebSecurity
class SecurityConfig: WebSecurityConfigurerAdapter() {
	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.mvcMatchers("/claims/**").hasAuthority("SCOPE_claims")
			.mvcMatchers("/email/**").hasAuthority("SCOPE_email")
			.anyRequest().authenticated()
			.and()
			.oauth2ResourceServer().jwt()
	}
}

@RestController
@RequestMapping("/resources")
class ResSvrController {
	@GetMapping("/something")
	fun getSomething() = "This is really something!"

	@GetMapping("/claims")
	fun getClaims(@AuthenticationPrincipal jwt: Jwt) = jwt.claims

	@GetMapping("/email")
	fun getSubject(@AuthenticationPrincipal jwt: Jwt) = jwt.subject
}