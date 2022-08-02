package com.hystrix.controller;

import com.hystrix.service.HystrixOrderService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
/*@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod", commandProperties = {
        @HystrixProperty(
                name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,
                value = "2000")
})*/
public class HystrixOrderController {

    @Resource
    private HystrixOrderService hystrixOrderService;

    // 正常服务
    @SneakyThrows
    @GetMapping("/consumer/hystrix/ok/{id}")
    /*@HystrixCommand*/
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        // TimeUnit.SECONDS.sleep(3);
        // int i = 1 / 0;
        return hystrixOrderService.paymentInfo_OK(id);
    }

    // 全局处理超时/异常
    public String paymentGlobalFallbackMethod() {
        return "Global全局超时/异常处理，请稍后再重试！";
    }


    /**
     * HystrixProperty属性 {@link com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager}
     */
    /*@HystrixCommand(fallbackMethod = "paymentTimeOutFallBackMethod", commandProperties = {
            @HystrixProperty(
                    name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,
                    value = "1500")
    })*/
    // 超时服务
    @GetMapping("/consumer/hystrix/timeOut/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        // int i = 1 / 0;
        return hystrixOrderService.paymentInfo_TimeOut(id);
    }

    // 降级后的方法
    public String paymentTimeOutFallBackMethod(@PathVariable("id") Integer id) {
        return "支付业务繁忙或者客户端自身运行出错，请检查后再重试！";
    }


}
