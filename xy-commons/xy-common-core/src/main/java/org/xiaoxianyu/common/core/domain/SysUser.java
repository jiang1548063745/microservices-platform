package org.xiaoxianyu.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户实体
 *
 * @author rorschach
 * @date 2021/9/14 11:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser extends SuperEntity<SysUser> {

    private static final long serialVersionUID = -5886012896705137070L;

    private String username;

    private String password;

    private String nickname;

    private String headImgUrl;

    private String mobile;

    private Integer sex;

    private Boolean enabled;

    private String type;

    private String openId;

    @TableLogic
    private boolean isDel;

//    @TableField(exist = false)
//    private List<SysRole> roles;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String oldPassword;

    @TableField(exist = false)
    private String newPassword;
}
