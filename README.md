helm install standard-service-operator standard-service-operator

kubectl get crds
Configuracion local no necesario si tienes un kubeconfig y sabe leerlo.

helm upgrade standard-service-operator standard-service-operator
hacer un manage refresh automagico cuando cambie un configmap para enseñar la utilidad del operador.


mvn clean package spring-boot:build-image



helm install perfumerias-loli perfumeriaslolioperator

kubectl apply  -f .\perfumeriaslolioperator\templates\standard_service_operator.yaml
kubectl delete -f .\perfumeriaslolioperator\templates\standard_service_operator.yaml