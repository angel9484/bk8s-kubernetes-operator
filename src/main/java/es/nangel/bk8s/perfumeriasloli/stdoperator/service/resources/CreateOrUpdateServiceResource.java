package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.LabelGeneratorComponent;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.ResourceTemplateLoader;
import io.fabric8.kubernetes.api.model.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrUpdateServiceResource {
    private final ResourceTemplateLoader resourceTemplateLoader;
    private final KubernetesClientHelper kubernetesClientHelper;
    private final LabelGeneratorComponent labelGeneratorComponent;

    public Service createOrUpdate(final String namespace, final String standardServiceName) {
        Service service = resourceTemplateLoader.loadYaml(Service.class, "service.yaml");
        service.getMetadata().setName(standardServiceName);
        service.getMetadata().setNamespace(namespace);
        service.getSpec().setSelector(labelGeneratorComponent.getLabels(standardServiceName));

        log.info("Creating Service {} in {}", service.getMetadata().getName(), namespace);
        return kubernetesClientHelper.createService(namespace, standardServiceName, service);

    }
}
