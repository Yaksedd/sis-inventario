package proy.inventario.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proy.inventario.modelo.Cliente;
import proy.inventario.repositorio.IClienteRepositorio;

@Service
public class ClienteServicioImpl implements IClienteServicio {

    @Autowired
    private IClienteRepositorio repository;

    @Override
    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    @Override
    public void guardarCliente(Cliente cliente) {

        // Verificar si ya existe un cliente con esa identificación
        if (repository.existsById(cliente.getIdCliente())) {

            throw new IllegalArgumentException(
                    "Ya existe un cliente registrado con la identificación: "
                    + cliente.getIdCliente()
            );
        }

        // Si no existe, se registra como nuevo cliente
        repository.save(cliente);
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        Cliente clienteExistente = repository.findById(cliente.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setEmail(cliente.getEmail());

        repository.save(clienteExistente);
    }

    @Override
    public void eliminarCliente(Integer id) {
        repository.deleteById(id);
    }
}
