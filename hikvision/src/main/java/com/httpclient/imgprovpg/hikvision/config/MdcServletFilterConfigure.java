package com.httpclient.imgprovpg.hikvision.config;

import com.module.servlettracer.MdcServletFilter;
import com.module.servlettracer.MdcTraceIdInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MdcServletFilterConfigure {

    @Value("${log.traceId.pointcutExpression:non-expression}")
    private String POINTCUT_EXPRESSION;

    @Bean
    @ConditionalOnMissingBean(MdcServletFilter.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    public MdcServletFilter mdcFilter(){
        return new MdcServletFilter();
    }

    @Bean("MdcTraceIdInterceptor")
    @ConditionalOnProperty(name = "log.traceId.pointcutExpression")
    public MdcTraceIdInterceptor mdcTraceIdInterceptor() {
        return new MdcTraceIdInterceptor();
    }

    @Bean("MdcTraceIdAdvisor")
    @ConditionalOnProperty(name = "log.traceId.pointcutExpression")
    public Advisor MdcTraceIdAdvisor(MdcTraceIdInterceptor mdcTraceIdMethodInterceptor) {
        AspectJExpressionPointcut cut = new AspectJExpressionPointcut();
        cut.setExpression(POINTCUT_EXPRESSION);
        Advisor advisor = new DefaultPointcutAdvisor(cut, mdcTraceIdMethodInterceptor);
        return advisor;
    }
}
