package es.nangel.bk8s.perfumeriasloli.stdoperator.controller;

import es.nangel.bk8s.perfumeriasloli.stdoperator.pojo.StandardService;
import es.nangel.bk8s.perfumeriasloli.stdoperator.pojo.StandardServiceStatus;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources.CreateOrUpdateDeploymentResource;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources.CreateOrUpdateServiceResource;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources.DeleteDeploymentResource;
import es.nangel.bk8s.perfumeriasloli.stdoperator.service.resources.DeleteServiceResource;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.javaoperatorsdk.operator.api.Context;
import io.javaoperatorsdk.operator.api.Controller;
import io.javaoperatorsdk.operator.api.DeleteControl;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.api.UpdateControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class StandardServiceController implements ResourceController<StandardService> {
    private final CreateOrUpdateDeploymentResource createOrUpdateDeploymentResource;
    private final DeleteDeploymentResource deleteDeploymentResource;
    private final CreateOrUpdateServiceResource createOrUpdateServiceResource;
    private final DeleteServiceResource deleteServiceResource;

    @Override
    public UpdateControl<StandardService> createOrUpdateResource(
            StandardService standardService, Context<StandardService> context) {
        String namespace = standardService.getMetadata().getNamespace();
        String standardServiceName = standardService.getMetadata().getName();
        Deployment createdDeployment = createOrUpdateDeploymentResource.createOrUpdate(namespace, standardServiceName, standardService);
        Service createdService = createOrUpdateServiceResource.createOrUpdate(namespace, standardServiceName);

        StandardServiceStatus status = new StandardServiceStatus();
        status.setHealth("UP");
        standardService.setStatus(status);
        return UpdateControl.updateStatusSubResource(standardService);
    }

    @Override
    public DeleteControl deleteResource(
            StandardService nginx, io.javaoperatorsdk.operator.api.Context<StandardService> context) {
        String namespace = nginx.getMetadata().getNamespace();
        String standardServiceName = nginx.getMetadata().getName();
        log.info("Execution deleteResource for: {}", standardServiceName);
        deleteDeploymentResource.delete(namespace, standardServiceName);
        deleteServiceResource.delete(namespace, standardServiceName);
        return DeleteControl.DEFAULT_DELETE;
    }
}