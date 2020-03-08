package ee.ituk.api.project.service;

import ee.ituk.api.common.exception.ErrorMessage;
import ee.ituk.api.common.exception.NotFoundException;
import ee.ituk.api.common.exception.ValidationException;
import ee.ituk.api.common.validation.ValidationUtil;
import ee.ituk.api.project.domain.Project;
import ee.ituk.api.project.domain.ProjectSummary;
import ee.ituk.api.project.repository.ProjectRepository;
import ee.ituk.api.project.repository.ProjectSummaryRepository;
import ee.ituk.api.project.validation.ProjectValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static ee.ituk.api.common.validation.ValidationUtil.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectSummaryRepository summaryRepository;

    private final ProjectValidator validator = new ProjectValidator();

    public Project findById(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(Project.class))));
    }

    public ProjectSummary findSummaryById(Long projectId) {
        var project = findById(projectId);
        return summaryRepository.findByProject(project)
                .orElseThrow(() -> new NotFoundException(Collections.singletonList(ValidationUtil.getNotFoundError(ProjectSummary.class))));
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public ProjectSummary addSummary(Long projectId, ProjectSummary projectSummary) {
        var project = findById(projectId);
        if (project.getSummary().isPresent()) {
            throw new ValidationException(ErrorMessage.builder().code(PROJECT_SUMMARY_ALREADY_EXISTS).build());
        }

        return saveProjectSummary(project, projectSummary);
    }

    public Project update(Project project, Long id) {
        checkForErrors(validator.validateUpdate(project, id));
        return projectRepository.save(project);
    }

    public ProjectSummary updateSummary(Long projectId, ProjectSummary projectSummary) {
        var project = findById(projectId);
        return saveProjectSummary(project, projectSummary);
    }

    public void delete(long id) {
        projectRepository.deleteById(id);
    }

    public void deleteSummary(Long projectId) {
        var project = findById(projectId);
        summaryRepository.deleteByProject(project);
    }

    private ProjectSummary selfMapProjectSummary(ProjectSummary summary) {
        summary.getBudgetRows().forEach(row -> row.setProjectSummary(summary));
        summary.getMembers().forEach(member -> member.setProjectSummary(summary));

        // force confirmedBy to be null if confirmedById is null (cannot be done with MapStruct because of https://github.com/mapstruct/mapstruct/issues/1166
        if (summary.getConfirmedBy() != null && summary.getConfirmedBy().getId() == null) {
            summary.setConfirmedBy(null);
        }

        return summary;
    }

    private ProjectSummary saveProjectSummary(Project project, ProjectSummary projectSummary) {
        projectSummary.setProject(project);
        checkForErrors(validator.validateSummaryData(projectSummary));

        var selfMappedProjectSummary = selfMapProjectSummary(projectSummary);
        return summaryRepository.save(selfMappedProjectSummary);
    }
}
