package learn.carbackend.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import learn.carbackend.upload.model.FileDBEntity;

@Entity
@Table(name = "car",
	indexes = {@Index(name = "car_unique_idx", unique = false, columnList = "brand")})
public class CarEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String brand, model, color, number;

	private int year1, price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private OwnerEntity owner;
	
	public OwnerEntity getOwner() {
		return owner;
	}
	
	public void setOwner(OwnerEntity owner) {
		this.owner = owner;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "car_image",
			joinColumns = @JoinColumn(name = "car_id"),
			inverseJoinColumns = @JoinColumn(name = "image_id"))
	private Set<FileDBEntity> images = new HashSet<>();
	
	public CarEntity(String brand, String model, String color, String number, int year, int price, Set<FileDBEntity> images) {
		super();
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.number = number;
		this.year1 = year;
		this.price = price;
		this.images = images;
	}
	public CarEntity() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getYear() {
		return year1;
	}
	public void setYear(int year) {
		this.year1 = year;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	public Set<FileDBEntity> getImages() {
		return images;
	}

	public void setImageUuids(Set<FileDBEntity> images) {
		this.images = images;
	}	
}
