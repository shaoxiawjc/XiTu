package com.teamtwo.xitu.service;

import com.teamtwo.xitu.dto.LoginDTO;
import com.teamtwo.xitu.dto.ResultResponse;
import org.springframework.stereotype.Service;

/**
 * @author shaoxiawjc
 * @ 2024/2/3
 * @ IntelliJ IDEA
 * @ xitu
 * @ com.teamtwo.xitu.service
 **/
@Service
public interface UserService {
	ResultResponse<LoginDTO> login(String username, String password);

	ResultResponse register(String username,String password);
}
