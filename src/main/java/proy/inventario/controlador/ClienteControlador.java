package proy.inventario.controlador;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import proy.inventario.modelo.Cliente;
import proy.inventario.servicio.IClienteServicio;

@Controller
@RequestMapping("/clientes") // Equivale a la URL del Servlet @WebServlet("/clientes")
public class ClienteControlador {

    @Autowired
    private IClienteServicio service;

    // Equivalente al antiguo doGet para mostrar la página
    // 1. Muestra la página principal de bienvenida
    @GetMapping
    public String inicio() {
        return "index";
    }

    // 2. Muestra la página independiente para REGISTRAR
    @GetMapping("/registrar")
    public String vistaRegistrar(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-registrar";
    }

    // 3. Muestra la página independiente para CONSULTAR (Lista)
    @GetMapping("/consultar")
    public String vistaConsultar(Model model) {
        model.addAttribute("clientes", service.listarClientes());
        return "cliente-consultar";
    }

    @GetMapping("/consultar-id")
    public String vistaConsultarPorId() {

        return "cliente-consultar-id";
    }

    // BUSCAR CLIENTE POR ID
    @GetMapping("/consultar-id/buscar")
    public String consultarPorId(
            @RequestParam("id") Integer id,
            Model model) {

        Cliente cliente = service.buscarPorId(id);

        if (cliente == null) {

            model.addAttribute(
                    "mensaje",
                    "No se encontró un cliente con la identificación indicada."
            );

        } else {

            model.addAttribute("cliente", cliente);
        }

        return "cliente-consultar-id";
    }

    // Procesa el guardado y redirecciona según corresponda
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            Model model) {

        // Validaciones del formulario
        if (result.hasErrors()) {
            return "cliente-registrar";
        }

        try {

            service.guardarCliente(cliente);

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());

            return "cliente-registrar";
        }

        return "redirect:/clientes/consultar";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "cliente-actualizar";
        }

        service.actualizarCliente(cliente);

        return "redirect:/clientes/consultar";
    }

    // 4. Muestra la página independiente para ACTUALIZAR (Edición)
    @GetMapping("/editar/{id}")
    public String vistaEditar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("cliente", service.buscarPorId(id));
        return "cliente-actualizar";
    }

    // 1. Mostrar formulario para ingresar el ID
    @GetMapping("/eliminar")
    public String vistaEliminar() {
        return "cliente-eliminar-id";
    }

// 2. Buscar el cliente por ID
    @PostMapping("/eliminar/buscar")
    public String buscarParaEliminar(@RequestParam("idCliente") Integer id,
            Model model) {

        Cliente cliente = service.buscarPorId(id);

        if (cliente == null) {
            model.addAttribute("mensaje",
                    "No existe un cliente con el ID ingresado.");

            return "cliente-eliminar-id";
        }

        model.addAttribute("cliente", cliente);

        return "cliente-eliminar";
    }

// 3. Confirmar y ejecutar la eliminación
    @PostMapping("/eliminar/confirmar")
    public String confirmarEliminar(@RequestParam("idCliente") Integer id,
            Model model) {

        try {

            service.eliminarCliente(id);
            return "redirect:/clientes/consultar";

        } catch (Exception e) {
            Cliente cliente = service.buscarPorId(id);
            model.addAttribute("cliente", cliente);
            model.addAttribute("mensaje",
                    "No se puede eliminar este cliente porque tiene registros relacionados.");

            return "cliente-eliminar";
        }
    }
}
