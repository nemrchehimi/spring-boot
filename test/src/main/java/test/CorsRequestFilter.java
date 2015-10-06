package test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
@Component
public class CorsRequestFilter implements Filter  {

	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		boolean isOptionsRequest = "OPTIONS".equals(request.getMethod());
		response.addHeader("Access-Control-Allow-Origin", "*");

		if ( isOptionsRequest && request.getHeader("Access-Control-Request-Method") != null ) {
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			// response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, x-requested-with");
			response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
			response.addHeader("Access-Control-Max-Age", "1800");
		}

		if ( isOptionsRequest ) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().flush();
			return;
		}

		chain.doFilter(request, response);
		
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}
}
