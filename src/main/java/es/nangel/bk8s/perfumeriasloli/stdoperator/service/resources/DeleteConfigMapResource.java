package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.client.dsl.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteConfigMapResource {
    private final KubernetesClientHelper kubernetesClientHelper;

    public void delete(String namespace, String standardServiceName) {
        Pair<Resource<ConfigMap>, Optional<ConfigMap>> object = kubernetesClientHelper.getConfigMap(namespace, standardServiceName);
        log.info("Deleting ConfigMap {}", standardServiceName);
        object.getValue().ifPresent(s -> object.getLeft().delete());
    }
}
