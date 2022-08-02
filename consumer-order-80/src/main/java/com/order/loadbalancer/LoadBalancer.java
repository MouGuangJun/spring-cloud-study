package com.order.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义负载均衡接口
 */
public interface LoadBalancer {

    // 从服务列表中选择一个服务进行使用
    ServiceInstance loadBalace(List<ServiceInstance> serviceInstances);
}
