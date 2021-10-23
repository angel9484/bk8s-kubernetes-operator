package es.nangel.bk8s.perfumeriasloli.stdoperator.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LabelGeneratorComponent {
    public Map<String, String> getLabels(String name) {
        return Map.of("app", name);
    }
}
