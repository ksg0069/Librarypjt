package com.goodee.library.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	@Autowired
	private BookDao bookDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

	public int createBookConfirm(BookVo vo) {
		LOGGER.info("[BookService] createBookConfirm();");
		return bookDao.insertBook(vo);
	}
}
