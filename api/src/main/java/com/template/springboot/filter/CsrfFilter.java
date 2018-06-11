package com.template.springboot.filter;

import com.google.common.collect.Lists;
import com.template.springboot.util.AjaxResponseWriter;
import com.template.springboot.util.ServiceStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CSRF跨域请求伪造拦截
 * 除登录以外的post方法，都需要携带token，如果token为空或token错误，则返回异常提示
 * 注意在filter初始化参数内配置排除的url
 */
@Slf4j
public class CsrfFilter implements Filter {
    public List<String> excludes = Lists.newArrayList();

    private boolean isOpen = false;//是否开启该filter

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!isOpen) {
            filterChain.doFilter(request, response);
            return;
        }
        log.debug("csrf filter is running");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        Object token = session.getAttribute("token");
        if (!"post".equalsIgnoreCase(req.getMethod()) || handleExcludeURL(req, resp) || token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestToken = req.getParameter("token");
        if (StringUtils.isBlank(requestToken) || !requestToken.equals(token)) {
            AjaxResponseWriter.write(req, resp, ServiceStatusEnum.ILLEGAL_TOKEN, "非法的token");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("csrf filter init~~~~~~~~~~~~");

        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }

        temp = filterConfig.getInitParameter("isOpen");
        if (StringUtils.isNotBlank(temp) && "true".equals(isOpen)) {
            isOpen = true;
        }
    }

    @Override
    public void destroy() {
    }
}
