package learn.carbackend.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
//public interface CarRepository extends CrudRepository <CarEntity, Long> {
//public interface CarRepository extends PagingAndSortingRepository <CarEntity, Long> {
public interface CarRepository extends JpaRepository<CarEntity, Long> {

	List<CarEntity> findByBrand(@Param("brand") String brand);
	List<CarEntity> findByColor(@Param("color") String color);
	List<CarEntity> findByYear1(@Param("year") int year);
	List<CarEntity> findByBrandAndModel(@Param("brand") String brand, @Param("model") String model);
	List<CarEntity> findByBrandOrColor(@Param("brand") String brand, @Param("color") String color);
	List<CarEntity> findByBrandOrderByYear1Asc(@Param("brand") String brand);
	
	// Fetch cars by brand using SQL
	//@Query("select c from Car c where c.brand = ?1")
	//List<Car> findByBrand(String brand);
	
	//@Query("select c from Car c where c.brand like %?1")
	//List<Car> findByBrandEndsWith(String brand);

}
