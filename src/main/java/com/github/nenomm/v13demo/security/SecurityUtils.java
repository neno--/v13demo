package com.github.nenomm.v13demo.security;

import com.vaadin.flow.server.ServletHelper.RequestType;
import com.vaadin.flow.shared.ApplicationConstants;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * SecurityUtils takes care of all such static operations that have to do with
 * security and querying rights from different beans of the UI.
 */
public final class SecurityUtils {

    private SecurityUtils() {
        // Util methods only
    }

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param p_request {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    static boolean isFrameworkInternalRequest(final HttpServletRequest p_request) {
        final String parameterValue = p_request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

}
