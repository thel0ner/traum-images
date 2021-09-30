package metaroa.traumimages;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name="APi-keys")
public class APIKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="api_id")
    private Long id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private Date generationDate;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public Date getGenerationDate() {
        return generationDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setGenerationDate(Date generationDate) {
        this.generationDate = generationDate;
    }
}
