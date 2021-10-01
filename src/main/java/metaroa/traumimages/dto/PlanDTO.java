package metaroa.traumimages.dto;

public class PlanDTO {
    private String title;
    private String description;
    private Long duration;
    private Long capacity;
    private Long price;

    public String getTitle() {
        return title;
    }

    public Long getCapacity() {
        return capacity;
    }

    public Long getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
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
