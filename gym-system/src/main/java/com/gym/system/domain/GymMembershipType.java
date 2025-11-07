package com.gym.system.domain;

import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gym.common.core.domain.BaseEntity;
import java.math.BigDecimal;

/**
 * 会籍套餐类型对象 gym_membership_type
 * 
 * @author gym
 */
public class GymMembershipType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会籍类型ID */
    private Long id;

    /** 类型编码 */
    @NotBlank(message = "类型编码不能为空")
    @Size(min = 0, max = 50, message = "类型编码不能超过50个字符")
    private String typeCode;

    /** 类型名称 */
    @NotBlank(message = "类型名称不能为空")
    @Size(min = 0, max = 100, message = "类型名称不能超过100个字符")
    private String typeName;

    /** 有效期天数 */
    @NotNull(message = "有效期天数不能为空")
    @Min(value = 1, message = "有效期天数不能小于1天")
    @Max(value = 3650, message = "有效期天数不能超过3650天")
    private Integer durationDays;

    /** 价格（元） */
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    @DecimalMax(value = "999999.99", message = "价格不能超过999999.99")
    private BigDecimal price;

    /** 原价（元） */
    @DecimalMin(value = "0.01", message = "原价必须大于0")
    @DecimalMax(value = "999999.99", message = "原价不能超过999999.99")
    private BigDecimal originalPrice;

    /** 类型描述 */
    @Size(min = 0, max = 1000, message = "类型描述不能超过1000个字符")
    private String description;

    /** 会员权益，JSON格式存储 */
    private String benefits;

    /** 是否无限次：0-有限次，1-无限次 */
    @NotNull(message = "是否无限次不能为空")
    private Integer isUnlimited;

    /** 是否可退款：0-不可退款，1-可退款 */
    @NotNull(message = "是否可退款不能为空")
    private Integer isRefundable;

    /** 退款政策说明 */
    @Size(min = 0, max = 1000, message = "退款政策说明不能超过1000个字符")
    private String refundPolicy;

    /** 状态：active-启用，inactive-停用 */
    @NotBlank(message = "状态不能为空")
    private String status;

    /** 排序顺序 */
    @NotNull(message = "排序顺序不能为空")
    @Min(value = 0, message = "排序顺序不能小于0")
    private Integer sortOrder;

    /** 备注 */
    @Size(min = 0, max = 500, message = "备注不能超过500个字符")
    private String remark;

    /** 是否删除：0-未删除，1-已删除 */
    private Integer isDeleted;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private java.util.Date deleteTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTypeCode()
    {
        return typeCode;
    }

    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public Integer getDurationDays()
    {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays)
    {
        this.durationDays = durationDays;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getOriginalPrice()
    {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice)
    {
        this.originalPrice = originalPrice;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBenefits()
    {
        return benefits;
    }

    public void setBenefits(String benefits)
    {
        this.benefits = benefits;
    }

    public Integer getIsUnlimited()
    {
        return isUnlimited;
    }

    public void setIsUnlimited(Integer isUnlimited)
    {
        this.isUnlimited = isUnlimited;
    }

    public Integer getIsRefundable()
    {
        return isRefundable;
    }

    public void setIsRefundable(Integer isRefundable)
    {
        this.isRefundable = isRefundable;
    }

    public String getRefundPolicy()
    {
        return refundPolicy;
    }

    public void setRefundPolicy(String refundPolicy)
    {
        this.refundPolicy = refundPolicy;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public java.util.Date getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(java.util.Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeCode", getTypeCode())
            .append("typeName", getTypeName())
            .append("durationDays", getDurationDays())
            .append("price", getPrice())
            .append("originalPrice", getOriginalPrice())
            .append("description", getDescription())
            .append("benefits", getBenefits())
            .append("isUnlimited", getIsUnlimited())
            .append("isRefundable", getIsRefundable())
            .append("refundPolicy", getRefundPolicy())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("deleteTime", getDeleteTime())
            .toString();
    }
}

