package id.holigo.services.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ContactPersonDto implements Serializable {

    private String name;

    private String phoneNumber;

    private String email;
}
