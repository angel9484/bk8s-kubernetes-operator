package es.nangel.bk8s.perfumeriasloli.stdoperator.service;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.RollableScalableResource;
import io.fabric8.kubernetes.client.dsl.ServiceResource;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KubernetesClientHelper {
    private final KubernetesClient kubernetesClient;

    public Pair<ServiceResource<Service>, Optional<Service>> getService(String namespace, String name) {
        ServiceResource<Service> objectResource =
                kubernetesClient
                        .services()
                        .inNamespace(namespace)
                        .withName(name);
        return Pair.of(objectResource, Optional.ofNullable(objectResource.get()));
    }

    public Pair<Resource<ConfigMap>, Optional<ConfigMap>> getConfigMap(final String namespace, final String name) {
        Resource<ConfigMap> objectResource =
                kubernetesClient
                        .configMaps()
                        .inNamespace(namespace)
                        .withName(name);
        return Pair.of(objectResource, Optional.ofNullable(objectResource.get()));
    }

    public Pair<RollableScalableResource<Deployment>, Optional<Deployment>> getDeployment(final String namespace, final String name) {
        RollableScalableResource<Deployment> objectResource =
                kubernetesClient
                        .apps()
                        .deployments()
                        .inNamespace(namespace)
                        .withName(name);
        return Pair.of(objectResource, Optional.ofNullable(objectResource.get()));
    }

    public Service createService(final String namespace, final String name, final Service objectToCreate) {
        Pair<ServiceResource<Service>, Optional<Service>> service = getService(namespace, name);
        if (service.getRight().isEmpty()) {
            return kubernetesClient.services().inNamespace(namespace).createOrReplace(objectToCreate);
        }
        return null;
    }

    public Deployment createDeployment(final String namespace, final String name, final Deployment objectToCreate) {
        Pair<RollableScalableResource<Deployment>, Optional<Deployment>> service = getDeployment(namespace, name);
        if (service.getRight().isEmpty()) {
            return kubernetesClient.apps().deployments().inNamespace(namespace).createOrReplace(objectToCreate);
        }
        return null;
    }


    public ConfigMap createConfigMap(final String namespace, final String name, final ConfigMap objectToCreate) {
        Pair<Resource<ConfigMap>, Optional<ConfigMap>> service = getConfigMap(namespace, name);
        if (service.getRight().isEmpty()) {
            return kubernetesClient.configMaps().inNamespace(namespace).createOrReplace(objectToCreate);
        }
        return null;
    }

    public void restartPods(final String namespace, final String objectName) {
        //Obviamente esto es una burrada :)
        kubernetesClient
                .pods()
                .inNamespace(namespace)
                .withLabel("app", objectName)
                .delete();

    }
}
