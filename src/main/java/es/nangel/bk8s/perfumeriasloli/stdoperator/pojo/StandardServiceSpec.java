package es.nangel.bk8s.perfumeriasloli.stdoperator.pojo;

import lombok.Data;

@Data
public class StandardServiceSpec {
    private StandardServiceImage image;
    private StandardServiceService service;
    private StandardServiceOperation operation;
}