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
package com.oneandone.rest.POJO.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author aliba
 */
public class ServerHardware extends HardwareBase {

    @JsonProperty("fixed_instance_size_id")
    private String fixedInstanceSizeId;
    private int vcore;
    @JsonProperty("baremetal_model_id")
    private String baremetalModelId;

    /**
     * @return the fixed_instance_size_id
     */
    public String getFixedInstanceSizeId() {
        return fixedInstanceSizeId;
    }

    /**
     * @param fixed_instance_size_id the fixed_instance_size_id to set
     */
    public void setFixedInstanceSizeId(String fixed_instance_size_id) {
        this.fixedInstanceSizeId = fixed_instance_size_id;
    }

    /**
     * @return the vcore
     */
    public int getVcore() {
        return vcore;
    }

    /**
     * @param vcore the vcore to set
     */
    public void setVcore(int vcore) {
        this.vcore = vcore;
    }

    /**
     * @return the baremetal_model_id
     */
    public String getBaremetalModelId() {
        return baremetalModelId;
    }

    /**
     * @param baremetalModelId the baremetal_model_id to set
     */
    public void setBaremetalModelId(String baremetalModelId) {
        this.baremetalModelId = baremetalModelId;
    }

}
