package net.koreate.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.koreate.rest.vo.SampleVO;

/**
 * REST 방식으로 데이터를 전달하기 위한 mapping 메서드를 저장하는 Controller class임을 명시
 * 모든 mapping 메서드에 @ResponseBody 어노테이션을 부여한 것과 동일하게 동작 
 */
@RestController
public class SampleRestController {

	@GetMapping("sample2")
	public SampleVO sample2(SampleVO sample) {
		System.out.println("sample2 : " + sample);
		return sample;
	}
	
	
	@PostMapping("sampleList")
	public List<SampleVO> sampleListPost(SampleVO vo){
		System.out.println("param sample : " + vo);
		List<SampleVO> list = new ArrayList<>();
		list.add(vo);
		for(int i = 0; i < 5; i++) {
			SampleVO sample = new SampleVO();
			sample.setName("Test Sample-"+i);
			sample.setPrice(i*1000);
			list.add(sample);
		}
		return list;
	}
	
	@PutMapping("sampleList")
	public List<SampleVO> sampleListPut(
			// SampleVO로 받아들일 데이터가 Request 본문에 json 형식으로 전달됨을 명시
			@RequestBody SampleVO vo
		){
		System.out.println("param put sample : " + vo);
		List<SampleVO> list = new ArrayList<>();
		list.add(vo);
		for(int i = 0; i < 5; i++) {
			SampleVO sample = new SampleVO();
			sample.setName("Test Sample-"+i);
			sample.setPrice(i*1000);
			list.add(sample);
		}
		return list;
	} 
	
}
