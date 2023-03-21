package com.ikikyou.practice.service;

import com.ikikyou.practice.dto.UserInfoDTO;
import com.ikikyou.practice.utils.Result;

/**
 * @author hongx
 * @date 2023/03/21 13:41
 */
public interface UserInfoService {

    Result<UserInfoDTO> getInfoByUserName(String username);
}
