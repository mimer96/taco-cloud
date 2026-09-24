package tacos.repository;

import org.springframework.data.repository.CrudRepository;

import tacos.models.TacoOrder;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long>{

}
