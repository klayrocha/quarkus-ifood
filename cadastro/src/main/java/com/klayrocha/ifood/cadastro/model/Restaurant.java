package com.klayrocha.ifood.cadastro.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name ="restaurant")
public class Restaurant extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String owner;
	
	public String identification;
	
	public String name;
	
	@OneToOne(cascade = CascadeType.ALL )
	public Localization localization;
	
	@CreationTimestamp
	public Date creationDate;
	
	@UpdateTimestamp
	public Date updateDate;
	
}
