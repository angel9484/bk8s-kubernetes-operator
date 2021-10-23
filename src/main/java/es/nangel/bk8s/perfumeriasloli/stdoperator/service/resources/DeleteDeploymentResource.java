package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import io.fabric8.kubernetes.api.model.DeletionPropagation;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.dsl.RollableScalableResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteDeploymentResource {
    private final KubernetesClientHelper kubernetesClientHelper;
    private final DeleteConfigMapResource deleteConfigMapResource;

    public void delete(String namespace, String standardServiceName) {
        deleteConfigMapResource.delete(namespace, standardServiceName);
        Pair<RollableScalableResource<Deployment>, Optional<Deployment>> object =
                kubernetesClientHelper.getDeployment(namespace, standardServiceName);
        log.info("Deleting Deployment {}", standardServiceName);
        object.getValue().ifPresent(s -> object.getLeft().withPropagationPolicy(DeletionPropagation.FOREGROUND).delete());
    }
}
