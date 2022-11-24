package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DrawingImgPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long dipid;

    @ManyToOne
    @JoinColumn(name="uid")
    Users user;

    @ManyToOne
    @JoinColumn(name="cid")
    Conferences conference;

    @Column(nullable = false, unique = true)
    String path;
}
