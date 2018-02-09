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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.keycloak.broker.controller.api.osb.v2.catalog.Service;


@Path("/osbapi/v2/catalog")
@Produces({MediaType.APPLICATION_JSON})
public class CatalogService {
    private static final String ssoServiceUuid = "714fd91f-a615-4684-8a56-8fe1dc4cb68e";
    private static final String defaultPlanUuid = "a6b152f8-c7a6-4a10-8f9e-ba46c56797e2";
    private static final String postgresqlPlanUuid = "236939a9-1ba5-43ee-b7ce-3a510dba9c02";
    private static final String mysqlPlanUuid = "9a837d3a-2530-4c3c-b8f2-71b431be5584";
    private static final Plan defaultPlan = new Plan(UUID.fromString(defaultPlanUuid), "default", "A RH-SSO server with ephemeral storage.", true, true);
    private static final Plan postgresqlPlan = new Plan(UUID.fromString(postgresqlPlanUuid), "postgresql-persistent", "A RH-SSO server with PostgreSQL persistent storage.", true, true);
    private static final Plan mysqlPlan = new Plan(UUID.fromString(mysqlPlanUuid), "mysql-persistent", "A RH-SSO server with MySQL persistent storage.", true, true);

    @GET
    public Response getCatalog() {
       List<Service> services = new ArrayList<>(4);
       Service ssoService = new Service(UUID.fromString(ssoServiceUuid), "rh-sso-service", "RH-SSO service implementation.", true);
       List<Plan> ssoPlans = new ArrayList<>(4);
       ssoPlans.add(defaultPlan);
       ssoPlans.add(postgresqlPlan);
       ssoPlans.add(mysqlPlan);
       ssoService.setPlans(ssoPlans);
       addService(services, ssoService);
       return Response.ok(new CatalogResponse(services)).build();
    }

    private void addService(List<Service> serviceList, Service service) {
       service.getTags().add("identity management");
       service.getTags().add("access management");
       service.getTags().add("sso");
       service.getTags().add("sso7");
       service.getTags().add("keycloak");
       service.getMetadata().put("console.openshift.io/iconClass", "icon-sso");
       service.getMetadata().put("displayName", "Red Hat Single Sign-On 7.1");
       service.getMetadata().put("documentationUrl", "http://www.keycloak.org/documentation.html");
       service.getMetadata().put("longDescription", "A service that deploys Red Hat Single Sign-On 7.1");
       service.getMetadata().put("providerDisplayName", "Keycloak community");
       serviceList.add(service);
    }
}
