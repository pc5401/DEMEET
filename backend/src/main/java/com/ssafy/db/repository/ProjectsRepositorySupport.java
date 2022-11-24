package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.QProjects;
import com.ssafy.db.entity.QUserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProjectsRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUserProject qUserProject = QUserProject.userProject;
    QProjects qProjects = QProjects.projects;


    public Optional<Projects> getProject(Long pid) {
       Projects project = jpaQueryFactory.select(qProjects)
               .from(qProjects).where(qProjects.pid.eq(pid))
               .fetchOne();
        if (project == null) return Optional.empty();
        return Optional.ofNullable(project);
    }
}
