
package proy.inventario.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proy.inventario.modelo.Cliente;

@Repository
public interface IClienteRepositorio extends JpaRepository<Cliente, Integer> {
    
}
