package metaroa.traumimages;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name="Images")
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="image_id")
    private Long id;

    @OneToMany(mappedBy = "image")
    private Collection<Website> website;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Date submissionDate;

    @Column(nullable = false)
    private Date modificationDate;

    @Column(nullable = false)
    private boolean isPrivate;

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public String getUrl() {
        return url;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public Collection<Website> getWebsite() {
        return website;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWebsite(Collection<Website> website) {
        this.website = website;
    }
}
