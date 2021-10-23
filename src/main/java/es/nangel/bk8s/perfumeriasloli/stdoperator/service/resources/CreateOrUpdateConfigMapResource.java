package es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import es.nangel.bk8s.perfumeriasloli.stdoperator.pojo.StandardService;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.KubernetesClientHelper;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.LabelGeneratorComponent;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.ResourceTemplateLoader;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.RestartPodsEvent;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.javaoperatorsdk.operator.api.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrUpdateConfigMapResource {
    private final KubernetesClientHelper kubernetesClientHelper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ConfigMap createOrUpdate(final String namespace, String objectName, final StandardService context) {
        Map<String, String> data = new HashMap<>();

        ConfigMap configMapToCreate =
                new ConfigMapBuilder()
                        .withMetadata(
                                new ObjectMetaBuilder()
                                        .withName(objectName)
                                        .withNamespace(namespace)
                                        .build())
                        .withData(data)
                        .build();

        Pair<Resource<ConfigMap>, Optional<ConfigMap>> existingConfigMap = kubernetesClientHelper.getConfigMap(namespace, objectName);

        log.info("Creating or updating ConfigMap {} in {}", configMapToCreate.getMetadata().getName(), namespace);
        ConfigMap configMap = kubernetesClientHelper.createConfigMap(namespace, objectName, configMapToCreate);
        if (existingConfigMap.getRight().isPresent() && configMap != null) {
            if (!Objects.equals(existingConfigMap.getRight().get().getData(),configMap.getData())) {
                log.info("Restarting pods because ConfigMap has changed in {}", namespace);
                applicationEventPublisher.publishEvent(new RestartPodsEvent(namespace, objectName, this));
            }
        }
        return configMapToCreate;
    }
}
