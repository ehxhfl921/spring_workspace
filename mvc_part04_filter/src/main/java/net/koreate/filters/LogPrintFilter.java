package net.koreate.filters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogPrintFilter implements Filter {

	// 로그 파일을 저장할 디렉토리 경로
	private String printDir;
	// 로그 정보를 저장할 파일 이름
	private String printFile;
	
	// 로그 파일에 로그 정보를 저장할 출력 스트림
	private PrintWriter pw;
	
    public LogPrintFilter() {
    	System.out.println("LogPrintFilter 객체 생성");
    }

    public void init(FilterConfig fConfig) throws ServletException {
    	System.out.println("LogPrintFilter init() 호출");
    	
    	printDir = fConfig.getInitParameter("printDir");
    	if(printDir == null) printDir = "/log";
    	
    	printFile = fConfig.getInitParameter("printFile");
    	if(printFile == null) printDir = "filter.log";
    	
    	ServletContext application = fConfig.getServletContext();
    	
    	// application - 프로젝트 webapp 경로 기준 매개 변수로 전달된 위치 경로를 
    	//				 절대 경로로 반환하는 메서드 : realPath
    	String path = application.getRealPath(printDir);
    	System.out.println("real path : " + path);
    	
    	File file = new File(path);
    	if(!file.exists()) {
    		System.out.println("real path 폴더 생성");
    		file.mkdirs();
    	}
    	
    	if(pw == null) {
    		try {
				file = new File(path, printFile);
				//									 	*이어쓰기 (false : 덮어쓰기) 
				FileWriter writer = new FileWriter(file, true);
				//							*AutoFlush
				pw = new PrintWriter(writer, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	System.out.println("LogPrintFilter init() 종료");
    }

    /**
     *  요청이 들어온 시간, 요청 IP, 요청 경로, 전송 방식, 응답 결과 등의 정보를 기록으로 남김
     */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	System.out.println("LogPrintFilter doFilter() 호출");
    	
    	HttpServletRequest req = (HttpServletRequest)request;
    	HttpServletResponse res = (HttpServletResponse)response;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E요일 HH:mm:ss");
    	String now = sdf.format(System.currentTimeMillis());
    	pw.println("===============================================");
    	pw.printf("로그 시간 : %s %n", now);
    	pw.printf("요청 IP : %s %n", req.getRemoteAddr());
    	pw.printf("요청 경로 : %s %n", req.getRequestURI());
    	pw.printf("전송 방식 : %s %n", req.getMethod());
		
    	chain.doFilter(request, response);
		
    	pw.printf("응답 코드 : %d %n", res.getStatus());
    	pw.printf("content type : %s %n", res.getContentType());
    	
		pw.println("===============================================");
		System.out.println("LogPrintFilter doFilter() 종료");
	}

	public void destroy() {
		System.out.println("LogPrintFilter destroy() 호출");
	}

}
