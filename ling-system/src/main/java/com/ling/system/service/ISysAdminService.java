package com.ling.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.common.core.mybatisplus.core.PagePlus;
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.vo.SysAdminVO;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-24 14:36
 **/
public interface ISysAdminService extends IService<SysAdmin> {

    /**
     * 根据用户名查询系统用户信息.
     *
     * @param userName 用户名.
     * @return 用户消息
     */
    SysAdmin getSysAdminByUsername(String userName);

    /**
     * 根据id获取用户信息.
     *
     * @param userId 用户id
     * @return 用户信息
     */
    SysAdmin getSysAdminById(Long userId);

    /**
     * 根据角色id获取用户列表.
     *
     * @param roleId 角色id
     * @return 用户列表
     */
    List<SysAdmin> listSysAdminListByRoleId(Long roleId);


    /**
     * 条件分页查询用户列表.
     *
     * @param pagePlus 分页信息
     * @param dto      前端传来的条件
     * @return 分页后信息
     */
    PagePlus<SysAdmin, SysAdminVO> listSysAdminPage(
            PagePlus<SysAdmin, SysAdminVO> pagePlus,
            SysAdminDTO dto
    );

    /**
     * 上传头像.
     *
     * @param id         用户id
     * @param file       头像文件
     * @param uploadById 上传人id
     * @return 是否成功
     */
    Boolean setSysAdminAvatar(
            Long id,
            MultipartFile file,
            final Long uploadById
    );

    /**
     * 获取用户头像地址.
     *
     * @param userId 用户id
     * @return 头像地址
     */
    String getSysAdminAvatarUrl(Long userId);

    /**
     * 检查用户名是否重复.
     *
     * @param sysAdminDTO 用户信息
     * @return 是否重复
     */
    Boolean checkUsernameUnique(SysAdminDTO sysAdminDTO);


    /**
     * 新增系统用户.
     *
     * @param sysAdminDTO 用户信息.
     * @return 是否成功
     */
    Boolean saveSysAdmin(SysAdminDTO sysAdminDTO);

    /**
     * 根据id删除系统用户.
     *
     * @param sysAdminId 系统用户id
     * @return 是否成功
     */
    Boolean removeSysAdminById(Long sysAdminId);

    /**
     * 根据id修改系统用户.
     *
     * @param sysAdminDTO 数据传输对象
     * @return 是否更新成功
     */
    Boolean updateSysAdminById(SysAdminDTO sysAdminDTO);
}
