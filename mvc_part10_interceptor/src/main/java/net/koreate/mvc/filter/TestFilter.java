package net.koreate.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info(" ====== START ====== ");
		request.setCharacterEncoding("UTF-8");
		log.info("request encoding : {}", request.getCharacterEncoding());
		chain.doFilter(request, response);
		log.info(" ======= END ======= ");
	} // end doFilter

}
