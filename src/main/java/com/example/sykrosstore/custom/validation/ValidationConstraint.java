package com.example.sykrosstore.custom.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationConstraint {
    List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return this.violations;
    }

    public void addViolation(Violation violation) {
        this.violations.add(violation);
    }

    public Violation getViolationByFieldName(String name) {
        return this.violations.stream().filter(violation -> violation.getFieldName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

}
