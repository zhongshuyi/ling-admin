package com.ling.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 密码修改DTO.
 *
 * @author 钟舒艺
 * @since 2022-06-02-0:46
 **/
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordDTO {

    /**
     * 旧密码.
     */
    @NotNull(message = "旧密码不能为空")
    private String passwordOld;

    /**
     * 新密码.
     */
    @NotNull(message = "新密码不能为空")
    private String passwordNew;

}
