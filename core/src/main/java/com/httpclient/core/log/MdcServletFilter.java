package com.httpclient.core.log;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MdcServletFilter extends MDCInsertingServletFilter {
    private static final String SERVLET_MSG_ID = "servlet-message-id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest))
            return;
        /**如果前端传递了唯一标识，拿出来直接用*/
        String traceId = ((HttpServletRequest) request).getHeader(SERVLET_MSG_ID);

        if (StringUtils.isEmpty(traceId))
            traceId = UUIDShort.generate();

        MDC.put(SERVLET_MSG_ID, traceId);
        try {
            super.doFilter(request, response, chain);
        } finally {
            MDC.remove(SERVLET_MSG_ID);
        }

    }
}
