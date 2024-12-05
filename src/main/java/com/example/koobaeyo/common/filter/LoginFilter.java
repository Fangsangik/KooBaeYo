package com.example.koobaeyo.common.filter;

import com.example.koobaeyo.auth.exception.type.AuthErrorCode;
import com.example.koobaeyo.common.constants.Auth;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/sign-up", "/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        // 다양한 기능을 사용하기 위해 다운 캐스팅
        //해당 요청에 대한 정보를 얻어옴.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("로그인 필터 로직 실행");

        // 로그인을 체크 해야하는 URL인지 검사
        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            // 로그인하지 않은 사용자인 경우
            if (session == null || session.getAttribute(Auth.LOGIN_USER) == null) {
                // JSON 형태의 응답 작성
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized

                String json = String.format("""
                            {
                            "success" : false,
                            "message" : %s
                            }
                            """,AuthErrorCode.SHOULD_HAVE_LOGINED.getMessage());
                response.getWriter().write(json);
                return;
            }

        }

        // 1번경우 : whiteListURL에 등록된 URL 요청이면 바로 chain.doFilter()
        // 2번경우 : 필터 로직 통과 후 다음 필터 호출 chain.doFilter()
        // 다음 필터 없으면 Servlet -> Controller 호출
        chain.doFilter(request, response);
    }

    // 로그인 여부를 확인하는 URL인지 체크하는 메서드
    private boolean isWhiteList(String requestURI) {
        // request URI가 whiteListURL에 포함되는지 확인
        // 포함되면 true 반환
        // 포함되지 않으면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

