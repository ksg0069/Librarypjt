package com.goodee.library.book;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookDao.class);
	
	final private String NAMESPACE="com.goodee.library.book.BookDao.";
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insertBook(BookVo vo) {
		LOGGER.info("[BookDao] insertBook();");
		return sqlSession.insert(NAMESPACE+"insertBook",vo);
	}

	public List<BookVo> selectBookList(BookVo vo){
		LOGGER.info("[BookDao] selectBookList();");
		
		return sqlSession.selectList(NAMESPACE+"selectBookList", vo);

	}
	
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookDao] selectBookCount();");
		int totalCount = sqlSession.selectOne(NAMESPACE+"selectBookCount",b_name);
		return totalCount; 
				
		
	}
	
	public BookVo selectBookOne(int b_no) {
		LOGGER.info("[BookDao] selectBookOne();");
		
		return sqlSession.selectOne(NAMESPACE+"selectBookOne",b_no);
	}
	
	public int updateBook(BookVo vo) {
		LOGGER.info("[BookDao] updateBook();");
		
		return sqlSession.update(NAMESPACE+"updateBook",vo);

		
	}
}
