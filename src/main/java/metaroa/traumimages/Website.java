package metaroa.traumimages;

import lombok.Data;
import lombok.NoArgsConstructor;
import metaroa.traumimages.User.User;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name="Websites")
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="website_id")
    private Long id;

    @OneToOne(mappedBy = "website")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date submissionDate;

    @Column(nullable = false)
    private Date modificationDate;

    @Column(nullable = false)
    private boolean isConfirmed;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public boolean getIsConfirmed(){  return isConfirmed; }

    public Date getModificationDate() {
        return modificationDate;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
