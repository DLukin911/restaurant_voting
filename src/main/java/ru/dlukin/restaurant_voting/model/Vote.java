package ru.dlukin.restaurant_voting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
    private User user;
}
