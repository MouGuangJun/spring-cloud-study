package com.hystrix.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    /**
     * 正常访问的方法
     */
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_OK, id:" + id;
    }

    /**
     * 超时的方法
     * <p>
     * HystrixProperty中的属性参见{@link com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager}
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            // 3秒超时后启动备用方案
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @SneakyThrows
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        int i = 1 / 0;
        int number = 3;
        TimeUnit.SECONDS.sleep(3);
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut, id:" + id + ",耗时(秒)" + number + "秒钟";
    }

    // 服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            // 是否开启断路器
            @HystrixProperty(
                    name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,
                    value = "true"
            ),
            // 请求次数（请求总阀值）
            @HystrixProperty(
                    name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,
                    value = "10"
            ),
            // 时间窗口期（快照时间窗）
            @HystrixProperty(
                    name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,
                    value = "10000"
            ),
            // 失败率达到多少后跳闸（错误百分比阀值）
            @HystrixProperty(
                    name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,
                    value = "60"
            )
    })

    @Override
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("-------------id不能为负数");
        }

        String serialNum = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNum;
    }


    private AtomicInteger index = new AtomicInteger(0);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    // 服务限流 通过信号量限流
    @SneakyThrows
    @HystrixCommand(fallbackMethod = "paymentFlowLimitFallBack", commandProperties = {
            // 通过信号量进行限流
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY,
                    value = "SEMAPHORE"),
            // 超时是否触发服务降级
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED,
                    value = "false"),
            // 并发请求信号量达到50触发降级
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,
                    value = "50"),
            // 是否允许进行服务熔断
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,
                    value = "false"),

    })
    @Override
    public String paymentFlowLimitSemaphore() {
        log.info("接口请求，请求次数，{}，当前时间：{} ", index.incrementAndGet(), formatter.format(LocalDateTime.now()));
        TimeUnit.SECONDS.sleep(3);

        return "请求执行成功！";
    }

    // 通过线程数进行限流
    @HystrixCommand(fallbackMethod = "paymentFlowLimitFallBack", commandProperties = {
            // 通过线程数进行限流
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY,
                    value = "THREAD"),
            // 超时是否触发服务降级
            @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED,
                    value = "false"),
            // 是否允许进行服务熔断
            @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED,
                    value = "false")
    }, threadPoolProperties = {

            // 设置线程池的最大队列大小，默认值 -1
            @HystrixProperty(name = HystrixPropertiesManager.MAX_QUEUE_SIZE,
                    value = "10"),
            // 用来为队列设置拒绝阈值，即使队列没有到达最大值也能拒绝请求，当 maxQueueSize 属性为 -1 时候，该属性无效
            @HystrixProperty(name = HystrixPropertiesManager.QUEUE_SIZE_REJECTION_THRESHOLD,
                    value = "20")
    })
    @SneakyThrows
    @Override
    public String paymentFlowLimitThread() {
        log.info("接口请求，请求次数，{}，当前时间：{} ", index.incrementAndGet(), formatter.format(LocalDateTime.now()));
        // 卡住线程，让并发量上去
        TimeUnit.SECONDS.sleep(3);

        return "请求执行成功！";
    }

    // 服务限流后备用的方法
    public String paymentFlowLimitFallBack() {
        return "请求执行失败！";
    }

    // 服务熔断降级后备用的方法
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能为负数，请稍后再重试，id：" + id;
    }

    // Hystrix服务降级后备选的方法
    private String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOutHandler, id:" + id + ",运行错误或者超时！";
    }
}
