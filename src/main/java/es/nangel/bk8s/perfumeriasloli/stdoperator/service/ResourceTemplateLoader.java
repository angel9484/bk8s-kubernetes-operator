package es.nangel.bk8s.perfumeriasloli.stdoperator.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import io.fabric8.kubernetes.client.utils.Serialization;

@Component
public class ResourceTemplateLoader {
    public <T> T loadYaml(Class<T> clazz, String yaml) {
        try (InputStream is = getClass().getResourceAsStream(yaml)) {
            return Serialization.unmarshal(is, clazz);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot find yaml on classpath: " + yaml);
        }
    }
}
