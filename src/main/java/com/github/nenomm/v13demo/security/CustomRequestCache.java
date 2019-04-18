package com.github.nenomm.v13demo.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HttpSessionRequestCache that avoids saving internal framework requests.
 */
class CustomRequestCache extends HttpSessionRequestCache {
    /**
     * {@inheritDoc}
     * <p>
     * If the method is considered an internal request from the framework, we skip
     * saving it.
     *
     * @see SecurityUtils#isFrameworkInternalRequest(HttpServletRequest)
     */
    @Override
    public void saveRequest(HttpServletRequest p_request, HttpServletResponse p_response) {
        if (!SecurityUtils.isFrameworkInternalRequest(p_request)) {
            super.saveRequest(p_request, p_response);
        }
    }

}
