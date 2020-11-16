package com.nisum.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidad que representa un telefono de un usuario
 * @author Juan Cardona
 * @since 13 nov. 2020
 */
@Entity
@Table(name = "phone_user")
public class PhoneUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @NotNull
    @Column(name = "city_code", nullable = false)
    private Integer cityCode;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private Integer countryCode;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties("phones")
    private User user;

    
    /**
	 * 
	 */
	public PhoneUser() {
		super();
	}

	/**
	 * @param phoneNumber
	 * @param cityCode
	 * @param countryCode
	 * @param user
	 */
	public PhoneUser(@NotNull @Min(6) @Max(20) Long phoneNumber, @NotNull Integer cityCode,
			@NotNull Integer countryCode, @NotNull User user) {
		super();
		this.phoneNumber = phoneNumber;
		this.cityCode = cityCode;
		this.countryCode = countryCode;
		this.user = user;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneUser phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public PhoneUser cityCode(Integer cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public PhoneUser countryCode(Integer countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public User getUser() {
        return user;
    }

    public PhoneUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneUser)) {
            return false;
        }
        return id != null && id.equals(((PhoneUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PhoneUser{" +
            "id=" + getId() +
            ", phoneNumber=" + getPhoneNumber() +
            ", cityCode=" + getCityCode() +
            ", countryCode=" + getCountryCode() +
            "}";
    }
}
