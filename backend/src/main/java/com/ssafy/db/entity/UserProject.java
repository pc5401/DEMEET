package com.ssafy.db.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long upid;

    @ManyToOne
    @JoinColumn(name = "uid")
    Users users;

    @ManyToOne
    @JoinColumn(name = "pid")
    Projects projects;

}
