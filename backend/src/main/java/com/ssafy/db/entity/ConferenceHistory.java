package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ConferenceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="uid")
    Users user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid")
    Conferences conferences;

    @Column(nullable = false)
    int actType;

    @Column(nullable = false)
    LocalDateTime historyCreateTime;
}
