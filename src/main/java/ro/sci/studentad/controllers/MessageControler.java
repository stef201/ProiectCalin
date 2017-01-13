package ro.sci.studentad.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ro.sci.studentad.domain.Ad;
import ro.sci.studentad.domain.Message;
import ro.sci.studentad.service.MessageService;

@Controller
public class MessageControler {
	
	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/newmessage", method = RequestMethod.GET)
	public String newMessage(Model model, @RequestParam(name = "id") Long id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String senderUserName = auth.getName();
		
		Message message = messageService.newMessage(senderUserName, id);		
		
		model.addAttribute("template", "newmessage");
		
		model.addAttribute("message", message);

		return "index";
	}

	@RequestMapping(value = "/newmessage", method = RequestMethod.POST)
	public String addAd(Model model, @Valid Message message, BindingResult result) {

		model.addAttribute("template", "newmessage");

		

		if (!result.hasErrors()) {
			messageService.sendMessage(message);
			model.addAttribute("template", "home");
		}

		return "index";
	}
	
	@RequestMapping(value = "/inbox", method = RequestMethod.GET)
	public String viewAds(Model model, @RequestParam(name = "p", defaultValue = "1") int pageNumber) {

		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		
		Page<Message> page = messageService.getUserInbox(pageNumber, username);

		model.addAttribute("page", page);

		model.addAttribute("template", "messagebox");

		return "index";
	}
}
