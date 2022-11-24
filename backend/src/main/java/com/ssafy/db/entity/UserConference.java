package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserConference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ucid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    Users user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid")
    Conferences conference;
}
