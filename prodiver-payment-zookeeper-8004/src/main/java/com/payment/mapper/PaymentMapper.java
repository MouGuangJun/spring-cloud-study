package com.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.service.dto.payment.Payment;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gjmou
 * @since 2022-07-02
 */
@Repository
public interface PaymentMapper extends BaseMapper<Payment> {

}
