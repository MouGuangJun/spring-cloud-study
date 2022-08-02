package com.service.dto.payment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author gjmou
 * @since 2022-07-03
 */
@TableName("ezgo_payment")
public class Payment {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("serial_no")
    private String serialNo;

    @TableField("input_date")
    private String inputDate;

    @TableField("update_date")
    private String updateDate;

    @TableField("note")
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", serialNo=" + serialNo +
                ", inputDate=" + inputDate +
                ", updateDate=" + updateDate +
                ", note=" + note +
                "}";
    }
}
