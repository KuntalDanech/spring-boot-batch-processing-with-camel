package com.danech.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author dev77
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "producttab")
public class Product {
	private String productCode;
	@Id
	private String variantSKU;
	private String productName;
	private String parentSKU;
	private String category;
	private String  subCategory;
	private String productLine;
	private Integer totalQuantity;
	private Double mrp;
	private Double webMrp;
	private String color;
	private String size;
	private String productDescription;
}
