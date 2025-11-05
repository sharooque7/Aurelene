package com.ainzson.usermanagementservice.entities;

import com.ainzson.usermanagementservice.enums.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Audited
public class Role extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Enumerated(EnumType.STRING)
    public RoleType role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    public Set<User> user;
}
