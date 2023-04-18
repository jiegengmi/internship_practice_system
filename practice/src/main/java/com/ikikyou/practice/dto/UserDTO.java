package com.ikikyou.practice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


/**
 * @author ikikyou
 * @date 2023/03/24 09:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     *
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;

    private String nickName;
    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 邮箱
     */
    @Email(message = "邮箱类型错误")
    private String email;

    /**
     *
     */
    @Min(8)
    private String password;

    /**
     * 状态（-1：注销；0，停用；1：启用）
     */
    private Integer status;
}
