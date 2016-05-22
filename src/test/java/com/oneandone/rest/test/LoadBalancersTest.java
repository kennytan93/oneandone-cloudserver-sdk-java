/*
 * Copyright 2016 aliba.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneandone.rest.test;

import com.oneandone.rest.POJO.Requests.CreateLoadBalancerRequest;
import com.oneandone.rest.POJO.Requests.LoadBalancerRuleRequest;
import com.oneandone.rest.POJO.Requests.UpdateLoadBalancerRequest;
import com.oneandone.rest.POJO.Response.LoadBalancerResponse;
import com.oneandone.rest.POJO.Response.Types;
import com.oneandone.rest.POJO.Response.Types.HealthCheckTestTypes;
import com.oneandone.rest.POJO.Response.Types.LoadBalancerMethod;
import com.oneandone.rest.client.RestClientException;
import com.oneandone.sdk.OneAndOneApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author aliba
 */
public class LoadBalancersTest {

    static OneAndOneApi oneandoneApi = new OneAndOneApi();
    static Random rand = new Random();
    static List<LoadBalancerResponse> loadBalancers;

    @BeforeClass
    public static void getAllLoadBalancers() throws RestClientException, IOException {
        oneandoneApi.setToken("apiToken");
        List<LoadBalancerResponse> result = oneandoneApi.getLoadBalancerApi().getLoadBalancers(0, 0, null, null, null);
        loadBalancers = result;
        assertNotNull(result);
    }

    @Test
    public void getLoadBalancer() throws RestClientException, IOException {
        LoadBalancerResponse result = oneandoneApi.getLoadBalancerApi().getLoadBalancer(loadBalancers.get(0).getId());

        assertNotNull(result);
        assertNotNull(result.getId());

    }

    @Test
    public void createLoadBalancer() throws RestClientException, IOException {
        CreateLoadBalancerRequest request = new CreateLoadBalancerRequest();

        request.setDescription("javaLBDesc");
        request.setName("javaLBTest" + rand.nextInt(300));
        request.setHealthCheckInterval(1);
        request.setPersistence(true);
        request.setPersistenceTime(30);
        request.setHealthCheckTest(Types.HealthCheckTestTypes.NONE);
        request.setMethod(Types.LoadBalancerMethod.ROUND_ROBIN);

        List<LoadBalancerRuleRequest> rules = new ArrayList<LoadBalancerRuleRequest>();
        LoadBalancerRuleRequest ruleA = new LoadBalancerRuleRequest();

        ruleA.setPortBalancer(80);
        ruleA.setProtocol(Types.LBRuleProtocol.TCP);
        ruleA.setSource("0.0.0.0");
        ruleA.setPortServer(80);
        rules.add(ruleA);

        request.setRules(rules);
        LoadBalancerResponse result = oneandoneApi.getLoadBalancerApi().createLoadBalancer(request);
        assertNotNull(result);

    }

    @Test
    public void updateLoadBalancer() throws RestClientException, IOException, InterruptedException {
        if (loadBalancers.isEmpty()) {
            return;
        }
        LoadBalancerResponse loadBalancer = null;
        for (LoadBalancerResponse balancer : loadBalancers) {
            if (!"ACTIVE".equals(balancer.getState())) {
                continue;
            }
            if (!balancer.getName().contains("java")) {
                continue;
            } else {
                loadBalancer = balancer;
                break;
            }
        }

        UpdateLoadBalancerRequest request = new UpdateLoadBalancerRequest();
        request.setHealthCheckInterval(100);
        request.setHealthCheckTest(Types.HealthCheckTestTypes.TCP);
        request.setMethod(Types.LoadBalancerMethod.ROUND_ROBIN);
        request.setPersistence(false);
        request.setName("updatedLB" + loadBalancer.getName());
        request.setHealthCheckPathParser("regex");
        request.setHealthCheckPath("google.com");

        LoadBalancerResponse result = oneandoneApi.getLoadBalancerApi().updateLoadBalancer(loadBalancer.getId(), request);

        assertNotNull(result);
        LoadBalancerResponse updated = oneandoneApi.getLoadBalancerApi().getLoadBalancer(result.getId());
        assertNotNull(updated.getId());
        Assert.assertEquals(updated.getName(), result.getName());
        Assert.assertEquals(100, updated.getHealthCheckInterval());
        Assert.assertEquals(updated.getHealthCheckTest(), HealthCheckTestTypes.TCP);
        Assert.assertEquals(updated.getMethod(), LoadBalancerMethod.ROUND_ROBIN);
    }

    @AfterClass
    public static void deleteLoadBalancer() throws RestClientException, IOException, InterruptedException {
        if (loadBalancers.isEmpty()) {
            return;
        }
        LoadBalancerResponse loadBalancer = null;
        for (LoadBalancerResponse balancer : loadBalancers) {
            if (!balancer.getName().contains("java")) {
                continue;
            } else {
                loadBalancer = balancer;
                break;
            }
        }
        if (loadBalancer != null) {
            LoadBalancerResponse result = oneandoneApi.getLoadBalancerApi().deleteLoadBalancer(loadBalancer.getId());

            assertNotNull(result);
        }
    }

}
