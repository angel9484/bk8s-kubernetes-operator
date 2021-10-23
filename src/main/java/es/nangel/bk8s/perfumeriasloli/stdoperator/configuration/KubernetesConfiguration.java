package es.nangel.bk8s.perfumeriasloli.stdoperator.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.config.runtime.DefaultConfigurationService;

@Configuration
public class KubernetesConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "kubernetes.client")
    public Config createKubernetesClientConfiguration() {
        return new Config();
    }

    @Bean
    public KubernetesClient createKubernetesClient(Config config) {
        return new DefaultKubernetesClient(config);
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    public Operator defineOperator(KubernetesClient kubernetesClient, List<ResourceController> controllers) {
        Operator operator = new Operator(kubernetesClient, DefaultConfigurationService.instance());
        controllers.forEach(operator::register);
        return operator;
    }
}