package com.LTUC.springSecurityIndustryWorld.entity;

import com.LTUC.springSecurityIndustryWorld.Enums.Roles;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    @Enumerated(value = EnumType.STRING)
    private Roles title;

    @Column(name = "description")
    private String description;
}
