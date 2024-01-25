package com.goodee.library.book;

import java.util.List;

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
	
	public List<BookVo> selectBookList(BookVo vo){
		LOGGER.info("[BookService] selectBookList();");
		
		return bookDao.selectBookList(vo);
		
		
	}
	
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookService] selectBookCount();");
		int totalCount = bookDao.selectBookCount(b_name);
		return totalCount;
	}
	
	public BookVo bookDetail(int b_no) {
		LOGGER.info("[BookService] bookDetail();");
		return bookDao.selectBookOne(b_no);
		
	}
	
	public int modifyConfirm(BookVo vo) {
		LOGGER.info("[BookService] modifyBookConfirm();");
		return bookDao.updateBook(vo);

	}
	
	public int deleteBook(int b_no) {
		LOGGER.info("[BookService] deleteBook();");
		
		return bookDao.deleteBook(b_no);

		
	}
}
