package net.koreate.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.koreate.rest.vo.SampleVO;

@Controller
public class SampleController {

	@GetMapping("testJSON")
	/**
	 *  mapping method가 반환하는 결과가
	 *  사용자가 요청한 데이터임을 명시
	 *  요청한 client에 데이터를 반환할 때
	 *  기본적으로 JSON 형식으로 변환하여 반환
	 */
	@ResponseBody
	public String testJSON() {
		return "Hello World!";
	}
	
	@GetMapping("sampleList")
	@ResponseBody
	public List<SampleVO> sampleList(){
		List<SampleVO> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			SampleVO vo = new SampleVO();
			vo.setName("CGG-"+i);
			vo.setPrice(i);
			list.add(vo);
		}
		return list;
	}
	
	@PostMapping("sample")
	@ResponseBody
	public SampleVO sample(SampleVO vo) {
		System.out.println(vo);
		return vo;
	}
	
	
	
}




















