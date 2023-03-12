package com.ling.common.core.domain.base;

import com.ling.common.core.validate.ValidationGroups;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公共DTO.
 *
 * @author 钟舒艺
 * @since 2022-10-22 14:01
 **/
@Data
@Accessors(chain = true)
public class BaseDTO {

    /**
     * id.
     */
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;
}
