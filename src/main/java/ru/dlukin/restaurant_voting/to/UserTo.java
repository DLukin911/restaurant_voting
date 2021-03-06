package ru.dlukin.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.dlukin.restaurant_voting.HasIdAndEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail {

    @Email
    @NotBlank
    @Size(max = 128)
    String email;

    @NotBlank
    @Size(min = 5, max = 100)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
