package com.ssafy.db.repository;

import com.ssafy.db.entity.Conferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConferencesRepository extends JpaRepository<Conferences, Long> {
//    Optional<Conferences> findConferencesBySessionNameAndConfStartTime(String sessionName, LocalDateTime startTime);
    Optional<Conferences> findConferencesBySessionNameAndConfStartTimeIs(String sessionName, LocalDateTime confStartTime);;
    Optional<Conferences> findConferencesBySessionName(String sessionName);;
    Optional<Conferences> findConferencesBySessionNameAndActivation(String sessionName, boolean activattion);;
    Optional<Conferences> findConferencesByUniqueSessionName(String uniqueSessionName);
}
