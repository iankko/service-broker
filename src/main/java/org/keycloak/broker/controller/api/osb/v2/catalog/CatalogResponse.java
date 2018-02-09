/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.broker.controller.api.osb.v2.catalog;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonSerialize(using = CatalogResponse.Serializer.class)
public class CatalogResponse {
    private static final ObjectMapper mapper = new ObjectMapper();

    private List<Service> services;

    public CatalogResponse(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    protected static class Serializer extends JsonSerializer<CatalogResponse> {
        @Override
        public void serialize(CatalogResponse value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();

            ArrayNode services = node.putArray("services");

            value.getServices().stream().forEach(service -> {
                JsonNode jsonNode = mapper.valueToTree(service);
                services.add(jsonNode);
            });

            mapper.writeValue(gen, node);
        }
    }
}
