package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.dsl.ServiceResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteServiceResource {
    private final KubernetesClientHelper kubernetesClientHelper;

    public void delete(final String namespace, final String standardServiceName) {
        log.info("Deleting Service {}", standardServiceName);
        Pair<ServiceResource<Service>, Optional<Service>> service = kubernetesClientHelper.getService(namespace, standardServiceName);
        service.getValue().ifPresent(s -> service.getLeft().delete());
    }
}
