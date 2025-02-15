/*
 * Copyright © 2017-2023 Knife4j(xiaoymin@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.xiaoymin.knife4j.spring.gateway.discover;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.event.EventListener;

/**
 * @author <a href="milo.xiaomeng@gmail.com">milo.xiaomeng@gmail.com</a>
 * 23/02/26 20:43
 * @since gateway-spring-boot-starter v4.1.0
 */
@AllArgsConstructor
public class ServiceChangeListener {
    
    final DiscoveryClient discoveryClient;
    final ServiceDiscoverHandler serviceDiscoverHandler;
    
    @ConditionalOnProperty(name = "knife4j.gateway.strategy", havingValue = "discover")
    @EventListener(classes = {ApplicationReadyEvent.class, HeartbeatEvent.class, RefreshRoutesEvent.class})
    public void discover() {
        this.serviceDiscoverHandler.discover(discoveryClient.getServices());
    }
    
    @ConditionalOnProperty(name = "knife4j.gateway.strategy", havingValue = "DISCOVER_CONTEXT")
    @EventListener(classes = {ApplicationReadyEvent.class, HeartbeatEvent.class, RefreshRoutesEvent.class})
    public void discoverDefault() {
        this.serviceDiscoverHandler.discoverDefault(discoveryClient.getServices());
    }
}
