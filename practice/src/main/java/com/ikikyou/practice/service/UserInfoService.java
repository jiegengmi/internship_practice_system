package com.ikikyou.practice.service;

import com.ikikyou.practice.model.dto.UserInfoDTO;
import com.ikikyou.practice.utils.Result;

/**
 * @author ikikyou
 * @date 2023/03/21 13:41
 */
public interface UserInfoService {

    Result<UserInfoDTO> getInfoByUserName(String username);
}
