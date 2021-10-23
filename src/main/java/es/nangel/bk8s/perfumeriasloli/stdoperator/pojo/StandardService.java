package es.nangel.bk8s.perfumeriasloli.stdoperator.pojo;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("com.perfumeriasloli")
@Version("v1beta1")
public class StandardService extends CustomResource<StandardServiceSpec, StandardServiceStatus> implements Namespaced {

}