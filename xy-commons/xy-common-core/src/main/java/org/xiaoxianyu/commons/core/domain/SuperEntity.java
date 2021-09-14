package org.xiaoxianyu.commons.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体父类
 *
 * @author rorschach
 * @date 2021/9/14 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SuperEntity<T extends Model<?>> extends Model<T> {

    /**
     * 主键ID
     */
    @TableId
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
