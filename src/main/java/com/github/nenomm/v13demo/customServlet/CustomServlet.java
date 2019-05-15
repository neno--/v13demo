package com.github.nenomm.v13demo.customServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomServlet extends HttpServlet {

    private String messageFormat;
    private DateTimeFormatter dtf;

    public void init() throws ServletException {
        // Do required initialization
        messageFormat = "Hello World %s";
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final LocalDateTime now = LocalDateTime.now();

        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<h1>" + String.format(messageFormat, dtf.format(now)) + "</h1>");
        listServlets(request.getServletContext().getServletRegistrations(), out);
    }

    private void listServlets(final Map<String, ? extends ServletRegistration> servletRegistrations, final PrintWriter out) {
        out.println("<ul>");
        servletRegistrations.forEach((key, registration) -> out.println(String.format(" <li> %s -> %s", key, registration.getMappings())));
        out.println("</ul>");
    }

    public void destroy() {
        // do nothing.
    }
}
