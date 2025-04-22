package tn.elearning.entities;





import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "participations")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime created_at;

    public Participation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   public LocalDateTime getCreated_at() {
        return created_at;
   }
   public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
   }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", created_at=" + created_at +
                '}';
    }
}