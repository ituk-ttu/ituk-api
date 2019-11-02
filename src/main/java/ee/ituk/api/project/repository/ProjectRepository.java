package ee.ituk.api.project.repository;

import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
