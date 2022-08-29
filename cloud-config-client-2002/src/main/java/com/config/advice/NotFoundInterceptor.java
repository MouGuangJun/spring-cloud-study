package com.config.advice;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data
@RefreshScope
public class NotFoundInterceptor implements HandlerInterceptor {
    private PathPatternParser pathPatternParser = new PathPatternParser();

    @Value("${web.gateway.blacklist}")
    private String blackList;

    private Set<String> blackSet;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        for (String black : blackSet) {
            PathPattern pattern = pathPatternParser.parse(black);
            if (pattern.matches(PathContainer.parsePath(request.getRequestURI()))) {
                // 黑名单的请求，设置响应码为404
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                // 进行拦截的操作
                return false;
            }
        }

        // 不在黑名单中，直接放行
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // 注册黑名单集合
    @PostConstruct
    private void register() {
        if (blackList != null) {
            blackSet = Arrays.stream(blackList.split(",")).map(String::trim).collect(Collectors.toSet());
        }
    }
}
