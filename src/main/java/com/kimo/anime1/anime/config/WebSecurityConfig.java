package com.kimo.anime1.anime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kimo.anime1.anime.security.AuthenticateFilter;

import lombok.RequiredArgsConstructor;

import static com.kimo.anime1.anime.model.entity.Permission.*;
import static com.kimo.anime1.anime.model.entity.Role.ADMIN;
import static com.kimo.anime1.anime.model.entity.Role.USER;
import static org.springframework.http.HttpMethod.*;

/**
 * 安全配置过滤器
 * @author  kimo
 *
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/users/register",
            "/users/publishevent",
            "/users/logout",
            "/users/verificationEmail",
            "/users/authentication",
            "/comments/list/**",
            "/video/list/**",
            "/video/getVideoSection/**",
            "/video/getVideo/**",
            "/video/getVideoUrl",
            "/video/getVideoResponse/**",
            "/video/search",
            "/video/getVideoHop",
            "/video/getVideoScore",
            "/video/match",
            "/video/listVideoAnimeType",
            "/video/getAllChase",
            "/video/getVideoScore",
            "/scoring/all/comment/**"
    };
    private static final String[] USER_LIST_URL = {
            "/userinfos/**",
            "/comments/addComments",
            "/comments/deleted/**",
            "/comments/put-comments/**",
            "/comments/like/**",
            "/scoring/addScore",
            "/scoring/put-comments/**",
            "/scoring/deleted/**",
    };
    private final AuthenticationProvider authenticationProvider;

    private final AuthenticateFilter authenticateFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer :: disable)
                        .authorizeHttpRequests(
                            req -> req.requestMatchers(WHITE_LIST_URL)
                                    .permitAll()
                                    .requestMatchers(USER_LIST_URL)
                                    .hasAnyRole(USER.name(),ADMIN.name())
                                    .requestMatchers(POST,"/video/add/video")
                                    .hasAuthority("admin:create")
                                    .requestMatchers(POST,"/video/add/url")
                                    .hasAuthority("admin:create")
                                    .requestMatchers(DELETE,"/video/deleteVideo/**")
                                    .hasAuthority("admin:delete")
                                    .requestMatchers(PUT,"/video/updated/**")
                                    .hasAuthority("admin:update")
                                    .anyRequest()
                                    .authenticated()
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class);
                        
        return http.build();
    }
}
