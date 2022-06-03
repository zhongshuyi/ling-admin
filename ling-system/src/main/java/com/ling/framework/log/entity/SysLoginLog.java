package com.ling.framework.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 系统登录访问记录.
 *
 * @author 钟舒艺
 */
@TableName(value = "sys_login_log")
@Data
public class SysLoginLog implements Serializable {
    /**
     * 记录id.
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id.
     */
    private Long userId;

    /**
     * 登录用户名.
     */
    private String username;

    /**
     * 登录IP地址.
     */
    private String ipaddr;

    /**
     * 登录地点.
     */
    private String loginLocation;

    /**
     * 浏览器类型.
     */
    private String browser;

    /**
     * 操作系统.
     */
    private String os;

    /**
     * 登录状态（1成功 0失败）.
     */
    private Byte status;

    /**
     * 提示消息.
     */
    private String msg;

    /**
     * 访问时间.
     */
    private Date loginTime;
}
