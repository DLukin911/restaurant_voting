package ru.dlukin.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"dishes"})
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("name ASC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Dish> dishes;

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.dishes);
    }

    public Restaurant(Integer id, String name, List<Dish> dishes) {
        super(id, name);
        this.dishes = dishes;
    }
}
