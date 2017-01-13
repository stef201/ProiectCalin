package ro.sci.studentad.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ro.sci.studentad.dao.AdDAO;
import ro.sci.studentad.dao.MessageDAO;
import ro.sci.studentad.dao.SiteUserDAO;
import ro.sci.studentad.domain.Ad;
import ro.sci.studentad.domain.Gender;
import ro.sci.studentad.domain.Message;
import ro.sci.studentad.domain.SiteUser;

@Service
public class MessageService {

	
	private final static int PAGESIZE = 10;
	
	
	@Autowired
	private MessageDAO messageDAO;
	@Autowired
	SiteUserDAO siteUserDAO;

	@Autowired
	AdDAO adDAO;

	public Page<Message> getPage(int pageNumber) {
		PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.DESC, "addDate");

		return messageDAO.findAll(request);
	}


	public Message newMessage(String senderUserName, Long id){
		
		Message newMessage = new Message();
		newMessage.setReciver(adDAO.findById(id).getOwner());
		newMessage.setSender(siteUserDAO.findByEmail(senderUserName));
		return newMessage;
	}

	public void sendMessage(Message message) {
		messageDAO.save(message);
		
	}
	
	public Page<Message> getUserInbox(int pageNumber, String siteUserName) {

		SiteUser owner = siteUserDAO.findByEmail(siteUserName);

		PageRequest request = new PageRequest(pageNumber - 1, PAGESIZE, Sort.Direction.DESC, "messageDate");
		
		return messageDAO.findByReciver(owner, request);

	}
	
}
