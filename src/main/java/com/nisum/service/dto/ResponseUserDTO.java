package com.nisum.service.dto;

import java.io.Serializable;
import java.time.Instant;

import com.nisum.domain.User;


/**
 * 
 *
 * @author Juan Cardona
 * @since 14 nov. 2020
 */
public class ResponseUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4940745447961147976L;

	/**id del usuario (puede ser lo que se genera por el banco de datos, pero sería más deseable un UUID) * */
    private Long id;

    /**fecha de creación del usuario**/
    private Instant created;
    
    /**fecha de la última actualización de usuario*/
    private Instant modified;
    
    /**del último ingreso (en caso de nuevo usuario, va a coincidir con la fecha de creación)**/
    private Instant last_login;
    
    /**token de acceso de la API (puede ser UUID o JWT)**/
    private String token;
    
    /**Indica si el usuario sigue habilitado dentro del sistema**/
    private boolean isactive = false;
    
    
    public ResponseUserDTO() {
        // Empty constructor needed for Jackson.
    }

    public ResponseUserDTO(User user) {
        this.id = user.getId();
        this.token = user.getLogin();
        this.isactive = user.getActivated();
        this.created = user.getCreatedDate();
        this.modified = user.getLastModifiedDate();
        this.last_login = user.getLastModifiedDate();
    }


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the created
	 */
	public Instant getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Instant created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public Instant getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Instant modified) {
		this.modified = modified;
	}

	/**
	 * @return the last_login
	 */
	public Instant getLast_login() {
		return last_login;
	}

	/**
	 * @param last_login the last_login to set
	 */
	public void setLast_login(Instant last_login) {
		this.last_login = last_login;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the isactive
	 */
	public boolean isIsactive() {
		return isactive;
	}

	/**
	 * @param isactive the isactive to set
	 */
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", created='" + created + '\'' +
            ", modified='" + modified + '\'' +
            ", last_login='" + last_login + '\'' +
            ", isactive='" + isactive + '\'' +
            "}";
    }
    
    
    

}
