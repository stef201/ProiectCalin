package ro.sci.studentad.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ro.sci.studentad.domain.Message;
import ro.sci.studentad.domain.SiteUser;

public interface MessageDAO extends PagingAndSortingRepository<Message, Long>{

	Page<Message> findByReciver(SiteUser owner, Pageable pageable);
	
	
}
