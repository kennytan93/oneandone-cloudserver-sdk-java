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

package com.oneandone.sdk;

import com.oneandone.rest.POJO.Requests.CreatePrivateNetworkRequest;
import com.oneandone.rest.POJO.Requests.UpdatePrivateNetworkRequest;
import com.oneandone.rest.POJO.Response.PrivateNetworksResponse;
import com.oneandone.rest.client.RestClientException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aliba
 */
public class PrivateNetworksApi extends OneAndOneAPIBase {
    
     public PrivateNetworksApi() {
        super("private_networks", "");
    }
     
    /**
     * Returns a list of your private networks.
     * @param page Allows to use pagination. Sets the number of servers that will be shown in each page.
     * @param perPage Current page to show.
     * @param sort Allows to sort the result by priority:sort=name retrieves a list of elements ordered by their names.sort=-creation_date retrieves a list of elements ordered according to their creation date in descending order of priority.
     * @param query Allows to search one string in the response and return the elements that contain it. In order to specify the string use parameter q:    q=My server
     * @param fields Returns only the parameters requested: fields=id,name,description,hardware.ram
     * @return PrivateNetworksResponse[]
     * @throws RestClientException
     * @throws IOException
     */
    public List<PrivateNetworksResponse> getPrivateNetworks(int page, int perPage, String sort, String query, String fields) throws RestClientException, IOException {
        String queryUrl = getUrlBase().concat(resource).concat("?");
        boolean firstParameter = true;

        if (page != 0) {
            if (!firstParameter) {
                queryUrl = queryUrl.concat("&");
            }
            queryUrl = queryUrl.concat("page=").concat(Integer.toString(page));
            firstParameter = false;
        }
        if (perPage != 0) {
            if (!firstParameter) {
                queryUrl = queryUrl.concat("&");
            }
            queryUrl = queryUrl.concat("per_page=").concat(Integer.toString(perPage));
            firstParameter = false;
        }
        if (sort != null && !sort.isEmpty()) {
            if (!firstParameter) {
                queryUrl = queryUrl.concat("&");
            }
            queryUrl = queryUrl.concat("sort=").concat(sort);
            firstParameter = false;
        }
        if (query != null && !query.isEmpty()) {
            if (!firstParameter) {
                queryUrl = queryUrl.concat("&");
            }
            queryUrl = queryUrl.concat("q=").concat(query);
            firstParameter = false;
        }
        if (fields != null && !fields.isEmpty()) {
            if (!firstParameter) {
                queryUrl = queryUrl.concat("&");
            }
            queryUrl = queryUrl.concat("fields=").concat(fields);
        }
        PrivateNetworksResponse[] result = client.get(queryUrl, null, PrivateNetworksResponse[].class);
        return Arrays.asList(result);
    }

    /**
     * Returns information about a private network.
     * @param networkId Unique private network's identifier.
     * @return PrivateNetworksResponse
     * @throws RestClientException
     * @throws IOException
     */
    public PrivateNetworksResponse getPrivateNetwork(String networkId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(networkId), null, PrivateNetworksResponse.class);
    }

    /**
     * Creates a new private network.
     * @param object CreatePrivateNetworkRequest
     * @return PrivateNetworksResponse
     * @throws RestClientException
     * @throws IOException
     
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public PrivateNetworksResponse createPrivateNetwork(CreatePrivateNetworkRequest object) throws RestClientException, IOException {
        return client.create(getUrlBase().concat(resource), object, PrivateNetworksResponse.class, 202);
    }

    /**
     * Removes a private network.
     * @param networkId Unique private network's identifier.
     * @return PrivateNetworksResponse
     * @throws RestClientException
     * @throws IOException
     */
    public PrivateNetworksResponse deletePrivateNetwork(String networkId) throws RestClientException, IOException {
        return client.delete(getUrlBase().concat(resource).concat("/").concat(networkId), PrivateNetworksResponse.class);
    }
    
    /**
     * Modifies a private network.
     * @param networkId Unique private network's identifier.
     * @param object UpdatePrivateNetworkRequest
     * @return PrivateNetworksResponse
     * @throws RestClientException
     * @throws IOException
     */
    public PrivateNetworksResponse updatePrivateNetwork(String networkId, UpdatePrivateNetworkRequest object) throws RestClientException, IOException {
        return client.update(getUrlBase().concat(resource).concat("/").concat(networkId), object, PrivateNetworksResponse.class, 200);
    }

}
