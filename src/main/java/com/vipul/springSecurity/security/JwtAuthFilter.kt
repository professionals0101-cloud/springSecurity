package com.vipul.springSecurity.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthFilter : OncePerRequestFilter() {
    private val jwtUtil = JwtUtil()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        val userId = jwtUtil.validateToken(token)

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            val authToken =
                UsernamePasswordAuthenticationToken(userId, null, null)

            authToken.setDetails(WebAuthenticationDetailsSource().buildDetails(request))

            SecurityContextHolder.getContext().setAuthentication(authToken)
        }

        filterChain.doFilter(request, response)
    }
}
