package net.koreate.lombok;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

/**
 *  lombok
 *  java 코드에서 반복적으로 작성해야 하는 코드를 줄여주는 라이브러리
 *  annotation 기본으로 컴파일 되는 class 정보를 보완해주는 라이브러리
 *  
 *  생성자, getter, setter equals hashcode toString 같은 메서드
 *  개발자가 직접 작성할 필요 없이 컴파일 단계에서 완성되므로 코드가 훨씬 간결해짐
 *
 */
// @Data
@Getter
@Setter
// @ToString
/**
 *  exclude   : 제외할 필드
 *  of		  : 포함할 필드
 *  callsuper : 부모 class method 호출 여부 
 */
// @ToString(exclude="upw")
//@ToString(of = {"uno", "uname", "friends"})
@ToString
@AllArgsConstructor
// @NoArgsConstructor - final field 존재 시 생성 불가 (필드 초기화 삽입 불가)
// @EqualsAndHashCode - 전체 필드 데이터가 같으면 동등한 객체로 재정의
@EqualsAndHashCode(of = {"uid", "upw"}) // 아이디와 비밀번호가 같으면 동등한 객체
@RequiredArgsConstructor // 필수 인자 값을 매개 변수로 받는 생성자 추가 

@Builder
public class UserVO {

	@Getter @Setter
	private int uno;
	@Setter @NonNull // null 값이면 안 되는 필드 - 필수 데이터
	private String uid;
	@NonNull
	private String upw;
	private final String uname;
	private Date regdate;
	
	@Singular("list")
	private List<String> friends;
	
}
