package net.koreate.msg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.koreate.msg.dao.MessageMapper;
import net.koreate.msg.dao.UserMapper;
import net.koreate.msg.vo.MessageVO;
import net.koreate.msg.vo.UserVO;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

	private final UserMapper userMapper;
	private final MessageMapper mapper;
	
	@Transactional 
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		log.info("service addMessage : " + vo);
		// 발신자의 포인트 +10
		UserVO user = new UserVO();
		user.setUserid(vo.getSender());
		user.setUpoint(10);
		// 포인트 증가 처리
		userMapper.updatePoint(user);
		
		// 메세지 등록
		mapper.create(vo);
		log.info("service addMessage 등록 완료");
	}

	@Override
	public List<MessageVO> list() throws Exception {
		log.info("------------------------------ service list method");
		return mapper.list();
	}

	@Override
	public MessageVO readMessage(String userid, int mno) throws Exception {
		// 수신 시간 update
		mapper.updateMessage(mno);
		
		// 수신자 포인트 증가 +5
		UserVO user = new UserVO();
		user.setUserid(userid);
		user.setUpoint(5);
		
		userMapper.updatePoint(user);
		
		MessageVO msg = mapper.readMessage(mno);
		
		return msg;
	}

}
