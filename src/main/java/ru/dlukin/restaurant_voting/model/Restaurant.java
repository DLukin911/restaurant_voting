package ru.dlukin.restaurant_voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.dishes, r.votes);
    }

    public Restaurant(Integer id, String name, List<Dish> dishes, List<Vote> votes) {
        super(id, name);
        this.dishes = dishes;
        this.votes = votes;
    }
}
