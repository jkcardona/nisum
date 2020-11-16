package com.nisum.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Juan Cardona
 * @since 13 nov. 2020
 */
public class PhoneUserDTO {

	@NotNull
	private Long number;
	
	@NotNull
	private Integer citycode;
	 
	@NotNull
	private Integer contrycode;

	/**
	 * 
	 */
	public PhoneUserDTO() {
		super();
	}

	/**
	 * @param number
	 * @param citycode
	 * @param contrycode
	 */
	public PhoneUserDTO(@NotBlank Long number, @NotBlank Integer citycode, @NotBlank Integer contrycode) {
		super();
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
	}

	/**
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * @return the citycode
	 */
	public Integer getCitycode() {
		return citycode;
	}

	/**
	 * @param citycode the citycode to set
	 */
	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}

	/**
	 * @return the contrycode
	 */
	public Integer getContrycode() {
		return contrycode;
	}

	/**
	 * @param contrycode the contrycode to set
	 */
	public void setContrycode(Integer contrycode) {
		this.contrycode = contrycode;
	}
	
	
	
	
	
	
	
}
