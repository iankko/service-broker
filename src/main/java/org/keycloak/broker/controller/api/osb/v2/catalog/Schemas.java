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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

@JsonSerialize(using = Schemas.Serializer.class)
public class Schemas {
    private static final ObjectMapper mapper = new ObjectMapper();

    private ServiceInstanceSchema serviceInstance;
    private ServiceBindingSchema serviceBinding;

    public Schemas() {
    }

    public Schemas(ServiceInstanceSchema serviceInstance, ServiceBindingSchema serviceBinding) {
        this.serviceInstance = serviceInstance;
        this.serviceBinding = serviceBinding;
    }

    public ServiceInstanceSchema getServiceInstance() {
        return serviceInstance;
    }

    public ServiceBindingSchema getServiceBinding() {
        return serviceBinding;
    }

    protected static class Serializer extends JsonSerializer<Schemas> {
        @Override
        public void serialize(Schemas value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();
            node.set("service_instance", mapper.valueToTree(value.getServiceInstance()));
            node.set("service_binding", mapper.valueToTree(value.getServiceBinding()));
            mapper.writeValue(gen, node);
        }
    }
}
