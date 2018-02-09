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

@JsonSerialize(using = ServiceBindingSchema.Serializer.class)
public class ServiceBindingSchema {
    private static final ObjectMapper mapper = new ObjectMapper();

    private InputParameters createParameters;

    public ServiceBindingSchema() {
    }

    public ServiceBindingSchema(InputParameters createParameters) {
        this.createParameters = createParameters;
    }

    public InputParameters getCreateParameters() {
        return createParameters;
    }

    protected static class Serializer extends JsonSerializer<ServiceBindingSchema> {
        @Override
        public void serialize(ServiceBindingSchema value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();
            node.set("create", mapper.valueToTree(value.getCreateParameters()));
            mapper.writeValue(gen, node);
        }
    }
}
