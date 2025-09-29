package net.koreate.msg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.koreate.msg.dao.MessageMapper;
import net.koreate.msg.dao.UserMapper;
import net.koreate.msg.vo.MessageVO;
import net.koreate.msg.vo.UserVO;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageMapper mp;
	private final UserMapper um;
	
	@Override
	public void addMessage(MessageVO vo) throws Exception {
		mp.create(vo);
		UserVO uvo = new UserVO();
		uvo.setUserid(vo.getSender());
		uvo.setUpoint(10);
		um.updatePoint(uvo);
	}

	@Override
	public List<MessageVO> list() throws Exception {
		return mp.list();
	}

	@Override
	public MessageVO readMessage(String userid, int mno) throws Exception {
		MessageVO message = mp.readMessage(mno);
		mp.updateMessage(mno);
		
		UserVO uvo = new UserVO();
		uvo.setUserid(userid);
		uvo.setUpoint(5);
		um.updatePoint(uvo);
		
		return message;
	}

}
