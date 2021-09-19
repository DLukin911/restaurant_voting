package ru.dlukin.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", indexes = {@Index(name = "one_vote_by_day_idx", columnList = "date_vote, user_id ",
        unique = true)})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_vote", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate dateVote = LocalDate.now();

    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public Vote(Vote v) {
        this(v.id, v.dateVote, v.restaurant, v.user);
    }

    public Vote(Integer id, Restaurant restaurant, User user) {
        this(id, LocalDate.now(), restaurant, user);
    }

    public Vote(Integer id, LocalDate dateVote, Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.restaurant = restaurant;
        this.user = user;
    }
}
