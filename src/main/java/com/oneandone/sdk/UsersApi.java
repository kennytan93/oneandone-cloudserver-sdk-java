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

import com.oneandone.rest.POJO.Requests.CreateUserRequest;
import com.oneandone.rest.POJO.Requests.UpdateUserRequest;
import com.oneandone.rest.POJO.Response.PermissionsResponse;
import com.oneandone.rest.POJO.Response.UserResponse;
import com.oneandone.rest.client.RestClientException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aliba
 */
public class UsersApi extends OneAndOneAPIBase {

    public UsersApi() {
        super("users", "");
    }

    /**
     * Returns a list with all users.
     *
     * @param page Allows to use pagination. Sets the number of servers that
     * will be shown in each page.
     * @param perPage Current page to show.
     * @param sort Allows to sort the result by priority:sort=name retrieves a
     * list of elements ordered by their names.sort=-creation_date retrieves a
     * list of elements ordered according to their creation date in descending
     * order of priority.
     * @param query Allows to search one string in the response and return the
     * elements that contain it. In order to specify the string use parameter q:
     * q=My server
     * @param fields Returns only the parameters requested:
     * fields=id,name,description,hardware.ram
     * @return UserResponse[]
     * @throws RestClientException
     * @throws IOException
     */
    public List<UserResponse> getUsers(int page, int perPage, String sort, String query, String fields) throws RestClientException, IOException {
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
        UserResponse[] result = client.get(queryUrl, null, UserResponse[].class);
        return Arrays.asList(result);
    }

    /**
     * Returns information about a user.
     *
     * @param userId Unique user's identifier.
     * @return UserResponse
     * @throws RestClientException
     * @throws IOException
     */
    public UserResponse getUser(String userId) throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat(userId), null, UserResponse.class);
    }
    
    /**
     * Returns a list with current user permissions
     *
     * @return PermissionsResponse
     * @throws RestClientException
     * @throws IOException
     */
    public PermissionsResponse getUserPermissions() throws RestClientException, IOException {
        return client.get(getUrlBase().concat(resource).concat("/").concat("current_user_permissions"), null, PermissionsResponse.class);
    }

    /**
     * Creates a new user.
     *
     * @param object CreateUserRequest
     * @return UserResponse
     * @throws RestClientException
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public UserResponse createUser(CreateUserRequest object) throws RestClientException, IOException {
        return client.create(getUrlBase().concat(resource), object, UserResponse.class, 201);
    }

    /**
     * Removes a user.
     *
     * @param userId Unique user's identifier.
     * @return UserResponse
     * @throws RestClientException
     * @throws IOException
     */
    public UserResponse deleteUser(String userId) throws RestClientException, IOException {
        return client.delete(getUrlBase().concat(resource).concat("/").concat(userId), UserResponse.class);
    }

    /**
     * Modifies user information.
     *
     * @param userId Unique user's identifier.
     * @param object UpdateUserRequest
     * @return UserResponse
     * @throws RestClientException
     * @throws IOException
     */
    public UserResponse updateUser(String userId, UpdateUserRequest object) throws RestClientException, IOException {
        return client.update(getUrlBase().concat(resource).concat("/").concat(userId), object, UserResponse.class, 200);
    }

}
