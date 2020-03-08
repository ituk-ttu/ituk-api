package ee.ituk.api.project.domain;

import ee.ituk.api.user.domain.User;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@Entity(name = "Project")
@Table(name = "project", schema = "public")
@SQLDelete(sql = "UPDATE project SET deleted_at = now() WHERE id = ?")
@SQLDeleteAll(sql = "UPDATE project SET deleted_at = NOW() WHERE 1 = 1")
@Where(clause = "deleted_at IS null")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDate dateStart;

    private LocalDate dateEnd;

    private String description;

    @ManyToOne
    @JoinColumn(name = "project_lead_id")
    private User projectLead;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private ProjectSummary summary;

    public Optional<ProjectSummary> getSummary() {
        return Optional.ofNullable(summary);
    }
}
