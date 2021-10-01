package metaroa.traumimages.Plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import metaroa.traumimages.User.User;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name="Plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long duration;

    @Column(nullable = false)
    private Long capacity;

    @Column(nullable = false)
    private Long price;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCapacity() {
        return capacity;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
