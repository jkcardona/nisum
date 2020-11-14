package com.nisum.service.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nisum.config.Constants;
import com.nisum.domain.User;

/**
 *
 * @author Juan Cardona
 * @since 14 nov. 2020
 */
public class RequestUserDTO implements Serializable {

    /**serialVersionUID**/
	private static final long serialVersionUID = 1093479412719146293L;

	@NotBlank(message = "name is mandatory")
    @Size(max = 50)
    @Size(min = 1, max = 50)
    private String name;

	@NotBlank(message = "email is mandatory")
    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = Constants.PASSWORD_REGEX,message = " El password debe tener Una Mayúscula, letras minúsculas, y dos números")
    @Size(min = 1, max = 50)
    private String password;

    @NotNull(message = "phones is mandatory")
    @Valid
    private List<PhoneUserDTO> phones;


    public RequestUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public RequestUserDTO(User user) {
        this.name = user.getFirstName();
        this.email = user.getEmail();
    }


    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phones
	 */
	public List<PhoneUserDTO> getPhones() {
		return phones;
	}

	/**
	 * @param phones the phones to set
	 */
	public void setPhones(List<PhoneUserDTO> phones) {
		this.phones = phones;
	}
	
	
	@Override
    public String toString() {
        return "UserDTO{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            "}";
    }


}
