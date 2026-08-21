package proy.inventario.servicio;

import java.util.List;
import proy.inventario.modelo.Cliente;

public interface IClienteServicio {

    List<Cliente> listarClientes();

    void guardarCliente(Cliente cliente);

    Cliente buscarPorId(Integer id);

    void eliminarCliente(Integer id);
    
    void actualizarCliente(Cliente cliente);

}
