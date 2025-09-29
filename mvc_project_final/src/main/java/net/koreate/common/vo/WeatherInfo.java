package net.koreate.common.vo;

import lombok.Data;

@Data
public class WeatherInfo {

	private String location;		// 지역
	private String temperature;		// 현재 온도
	private String status;			// 날씨 상태
	private String src;				// 이미지 아이콘 링크
}
