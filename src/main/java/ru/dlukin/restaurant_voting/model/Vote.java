package ru.dlukin.restaurant_voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote", indexes = {@Index(name = "one_vote_by_day_idx", columnList = "date_vote, user_id ",
        unique = true)})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})    //TODO: StackowerflowError in tests
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_vote", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateVote = LocalDateTime.now();

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
        this(id, LocalDateTime.now(), restaurant, user);
    }

    public Vote(Integer id, LocalDateTime dateVote, Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.restaurant = restaurant;
        this.user = user;
    }
}
