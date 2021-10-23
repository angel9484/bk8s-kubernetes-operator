package es.nangel.bk8s.perfumeriasloli.stdoperator.service;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class RestartPodsEvent extends ApplicationEvent {
    private final String namespace;
    private final String objectName;

    public RestartPodsEvent(final String namespace, final String objectName, Object source) {
        super(source);
        this.namespace = namespace;
        this.objectName = objectName;
    }
}
