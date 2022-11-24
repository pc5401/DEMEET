package com.ssafy.db.repository;

import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
    UserProject getUserProjectByProjectsAndUsers(Projects project, Users user);

}
