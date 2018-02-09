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
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;

import java.io.IOException;

@JsonSerialize(using = InputParameters.Serializer.class)
public class InputParameters {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonSchema parameters;

    public InputParameters() {
    }

    public InputParameters(JsonSchema parameters) {
        this.parameters = parameters;
    }

    public JsonSchema getParameters() {
        return parameters;
    }

    protected static class Serializer extends JsonSerializer<InputParameters> {
        @Override
        public void serialize(InputParameters value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            ObjectNode node = mapper.createObjectNode();
            node.set("parameters", mapper.valueToTree(value.getParameters()));
            mapper.writeValue(gen, node);
        }
    }
}
