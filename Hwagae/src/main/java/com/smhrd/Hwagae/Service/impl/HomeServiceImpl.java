package com.smhrd.Hwagae.Service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.Hwagae.Service.HomeService;
import com.smhrd.Hwagae.VO.TestVO;
import com.smhrd.Hwagae.util.BusinessException;

@Service
@Transactional(rollbackFor = {Exception.class, BusinessException.class})
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private SqlSession sqlSession;
	private final static String mapper = "com.smhrd.Hwagae.HomeMapper";
	
	@Override
	public TestVO retrieveUserInfo(Map<String, Object> commendMap) throws Exception {
		
        List<TestVO> userInfoList = sqlSession.selectList(mapper+".retrieveUserInfo",commendMap);
        
        TestVO testVO = new TestVO();
        
        if(userInfoList.size() > 0){
        	testVO = userInfoList.get(0);
        }else {
        	throw new BusinessException("회원정보가 존재하지 않습니다.");
        }
        
        return testVO;
		
	}

}
