package com.order.loadbalancer;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自己写的一个轮询算法
 */
@Component
public class LoadBalancerImpl implements LoadBalancer {

    private AtomicInteger nextServer = new AtomicInteger(0);

    // 从服务列表中选择一个服务进行使用
    @Override
    public ServiceInstance loadBalace(List<ServiceInstance> serviceInstances) {

        return serviceInstances.get(nextServerIndex(serviceInstances.size()));
    }


    /**
     * 从服务端选择服务 负载均衡算法：rest接口第几次请求数 % 服务器集群总数 = 实际调用服务器位置下标，
     * 每次服务重启动后rest计数接口从1开始
     */
    private int nextServerIndex(int serverCount) {
        // C ! A ! S
        while (true) {
            int current = nextServer.get();
            int next = current == Integer.MAX_VALUE ? 0 : current + 1;
            if (nextServer.compareAndSet(current, next)) {
                return next % serverCount;
            }
        }
    }
}
