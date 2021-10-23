package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.pojo.StandardService;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.LabelGeneratorComponent;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.ResourceTemplateLoader;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.RestartPodsEvent;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrUpdateDeploymentResource {
    private final ResourceTemplateLoader resourceTemplateLoader;
    private final KubernetesClientHelper kubernetesClientHelper;
    private final CreateOrUpdateConfigMapResource createOrUpdateConfigMapResource;
    private final LabelGeneratorComponent labelGeneratorComponent;

    public Deployment createOrUpdate(final String namespace, String objectName, final StandardService specs) {
        ConfigMap configMap = createOrUpdateConfigMapResource.createOrUpdate(namespace, objectName, specs);
        Deployment deploymentToCreate = resourceTemplateLoader.loadYaml(Deployment.class,
                "deployment.yaml");
        deploymentToCreate.getMetadata().setName(objectName);
        deploymentToCreate.getMetadata().setNamespace(namespace);
        deploymentToCreate.getSpec().getSelector().setMatchLabels(labelGeneratorComponent.getLabels(objectName));
        deploymentToCreate
                .getSpec()
                .getTemplate()
                .getMetadata()
                .setLabels(labelGeneratorComponent.getLabels(objectName));
        deploymentToCreate
                .getSpec()
                .setReplicas(specs.getSpec().getService().getReplicas());
        deploymentToCreate
                .getSpec()
                .getTemplate()
                .getSpec()
                .getContainers()
                .get(0)
                .setImage(String.format("%s:%s", specs.getSpec().getImage().getRepository(), specs.getSpec().getImage().getTag()));
        deploymentToCreate
                .getSpec()
                .getTemplate()
                .getSpec()
                .getContainers()
                .get(0)
                .setName(specs.getSpec().getService().getName());
        deploymentToCreate
                .getSpec()
                .getTemplate()
                .getSpec()
                .getVolumes()
                .get(0)
                .setConfigMap(
                        new ConfigMapVolumeSourceBuilder().withName(objectName).build());

        log.info("Creating or updating Deployment {} in {}", deploymentToCreate.getMetadata().getName(), namespace);
        return kubernetesClientHelper.createDeployment(namespace, objectName, deploymentToCreate);
    }

    @EventListener
    public void restartPods(RestartPodsEvent restartPodsEvent) {
        String namespace = restartPodsEvent.getNamespace();
        String objectName = restartPodsEvent.getObjectName();
        log.info("Restarting Pods for {} in {}. You will lose availability :D", objectName, namespace);
        kubernetesClientHelper.restartPods(namespace, objectName);
    }
}
