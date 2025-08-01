package com.hakancivelek.user.service.domain;

import java.util.Set;
import java.util.Collections;

public class Role {
    private final Long id;
    private final String name;
    private final Set<Privilege> privileges;

    public Role(Long id, String name, Set<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Privilege> getPrivileges() {
        return Collections.unmodifiableSet(privileges);
    }
}
