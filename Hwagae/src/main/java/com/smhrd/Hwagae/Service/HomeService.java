package com.smhrd.Hwagae.Service;

import java.util.List;
import java.util.Map;

import com.smhrd.Hwagae.VO.TestVO;

public interface HomeService {

	public TestVO retrieveUserInfo(Map<String, Object> commendMap) throws Exception;
	
}
