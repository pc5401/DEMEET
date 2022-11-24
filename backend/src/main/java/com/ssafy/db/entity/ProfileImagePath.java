package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(of = {"pipid","path"})
public class ProfileImagePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pipid;


    @OneToOne(mappedBy = "profileImagePath")
    @JoinColumn(name = "uid")
    Users user;

    @Column(unique = true)
    String path;
}
