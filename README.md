## Assumptions
* All images are created and with the correct repository

## Instructions
* First run our created operator :)
* The application will fail because the CRD doesn't exist.
* Execute `kubectl apply -f .\standard-service-operator\crds\standard_service_custom_resource_definition.yaml`
* Check what happened with `kubectl get crds`
* Run again and check logs :)
* In the repository `https://github.com/angel9484/bk8s-demo-decompose-legacy` execute your chart with: `helm install perfumerias-loli perfumeriaslolioperator`
* Check what happened with `kubectl get all` and note your POD_NAME
* Check your service working with `kubectl port-forward POD_NAME 8081:8080`
* Browse `http://localhost:8081/findProducts` Oh! your service is still running
* Stop the operator in your local machine and browse again
* Delete your service with `helm delete perfumerias-loli` Oh! your service is still running. Check `kubectl get all`
* Run again the operator. Are your service running? :P.
* Stop the operator
* Delete the crd `kubectl delete crd standardservices.com.perfumeriasloli`
* Install the operator with `helm install standard-service-operator standard-service-operator`
* Check `kubectl get all`
* Install again the chart of the service: `helm install perfumerias-loli perfumeriaslolioperator`
* Check `kubectl get all` and note your POD_NAME
* Check your service working with `kubectl port-forward POD_NAME 8081:8080`

## Other stuff
* Do you want your dockerfile to be spring native? (<100ms startup) `mvn clean package spring-boot:build-image`
* Configuracion local no necesario si tienes un kubeconfig y sabe leerlo.

## Backlog
* hacer un manage refresh automagico cuando cambie un configmap para enseñar la utilidad del operador.