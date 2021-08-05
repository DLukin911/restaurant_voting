package ru.dlukin.restaurant_voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_vote", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date dateVote = new Date();

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ManyToOne
    private Restaurant restaurant;

    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote(Vote v) {
        this(v.id, v.dateVote, v.restaurant, v.user);
    }

    public Vote(Integer id, Restaurant restaurant, User user) {
        this(id, new Date(), restaurant, user);
    }

    public Vote(Integer id, Date dateVote, Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.restaurant = restaurant;
        this.user = user;
    }
}
