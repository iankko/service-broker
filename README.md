# OSB API-based service broker for Red Hat Single Sign-On 7.1 server
Implementation of the [OpenServiceBrokerAPI v2.13](https://github.com/openservicebrokerapi/servicebroker) based service broker for Red Hat Single Sign-On 7.1 server.

## Deploying the broker on OpenShift

*Prerequisite*: The following deployment steps follow on from and assume an OpenShift instance similar to that created in the [OpenShift Primer](https://access.redhat.com/documentation/en/red-hat-xpaas/0/single/openshift-primer).

### Deploying the broker via OpenShift S2I

*Prerequisites*:
* Clone the [service-broker](https://github.com/iankko/service-broker) repository:
```
$ git clone https://github.com/iankko/service-broker
```
* Create dedicated OpenShift project:
```
$ oc new-project rh-sso-broker
```

#### Using oc CLI tools to deploy the broker

* Process and deploy the provided `templates/rh-sso-broker-s2i.yaml` S2I template using the default values
```
$ cd templates/
```
```
$ oc process -f rh-sso-broker-s2i.yaml | oc create -f -
imagestream "rh-sso-broker" created
buildconfig "rh-sso-broker" created
deploymentconfig "rh-sso-broker" created
service "rh-sso-broker" created
clusterservicebroker "rh-sso-broker" created
```

#### Using the OpenShift Web Console to deploy the broker

* In the OpenShift web console, select the `rh-sso-broker` project created in previous step.
* Click `Add to Project`, then select `Import YAML/JSON` option. Click `Browse` button, and point the `File Upload` selection dialog to the location of the `rh-sso-broker-s2i.yaml` application template.
* Click the `Create` button. Ensure the `Process the template` checkbox is checked in, click `Continue` button.
* Click the `Create` button. This will issue the following warning to appear:

*This will create resources outside of the project, which might impact all users of the cluster.Typically only cluster administrators can create these resources. The cluster-level resources being created are: cluster service broker*

Since the aforementioned `rh-sso-broker-s2i.yaml` application template also contains definition of the `ClusterServiceBroker` object, which is expected to be created solely by OpenShift cluster administrator, the warning is expected. We want to deploy a cluster-wide service broker for the RH-SSO server.
* Click the `Create Anyway` button.
* A `RH-SSO OSB API-based service broker has been created.` message dialog with message like:

*A new RH-SSO OSB API-based service broker (without TLS support) has been created in your project. It is reachable at: 'http://rh-sso-broker.rh-sso-broker.svc.cluster.local:8080/osbapi' internal cluster URL.*

is displayed.
* [Verify proper work of broker](#-verifying-proper-work-of-broker-service-catalog-endpoint)

## Verifying proper work of broker service catalog endpoint

* Verify the `rh-sso-broker` Clusterservicebroker object exists in the cluster:
```
$ oc get clusterservicebroker
NAME                             KIND
ansible-service-broker           ClusterServiceBroker.v1beta1.servicecatalog.k8s.io
rh-sso-broker                    ClusterServiceBroker.v1beta1.servicecatalog.k8s.io
template-service-broker          ClusterServiceBroker.v1beta1.servicecatalog.k8s.io
```

* Verify the catalog entries can be successfully retrieved from the broker:
```
$ oc get clusterservicebroker rh-sso-broker -o yaml
apiVersion: servicecatalog.k8s.io/v1beta1
kind: ClusterServiceBroker
metadata:
  creationTimestamp: 2018-02-21T18:22:01Z
  finalizers:
  - kubernetes-incubator/service-catalog
  generation: 1
  labels:
    template: rh-sso-broker-s2i
  name: rh-sso-broker
  resourceVersion: "461275"
  selfLink: /apis/servicecatalog.k8s.io/v1beta1/clusterservicebrokers/rh-sso-broker
  uid: 1c58548b-1734-11e8-86b7-0a580a800032
spec:
  relistBehavior: Duration
  relistDuration: 15m0s
  relistRequests: 0
  url: http://rh-sso-broker.rh-sso-broker.svc.cluster.local:8080/osbapi
status:
  conditions:
  - lastTransitionTime: 2018-02-21T18:28:06Z
    message: Successfully fetched catalog entries from broker.
    reason: FetchedCatalog
    status: "True"
    type: Ready
  lastCatalogRetrievalTime: 2018-02-21T18:28:06Z
  reconciledGeneration: 1
```
