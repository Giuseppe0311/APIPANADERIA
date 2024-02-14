package com.proyecto.panaderia.services.impl;

import com.proyecto.panaderia.dto.ProductosDTO;
import com.proyecto.panaderia.entity.Productos;
import com.proyecto.panaderia.exceptions.CategoriaNotFoundException;
import com.proyecto.panaderia.exceptions.EmpresaNotFoundException;
import com.proyecto.panaderia.exceptions.ProductoExisteneException;
import com.proyecto.panaderia.exceptions.ProductoNotFoundException;
import com.proyecto.panaderia.mapper.ProductosDTOMapper;
import com.proyecto.panaderia.repository.CategoriaRepositorio;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.ProductosRepositorio;
import com.proyecto.panaderia.repository.UnidadMedidaRepositorio;
import com.proyecto.panaderia.request.ProductosRequest;
import com.proyecto.panaderia.services.ProductosServicio;
import com.proyecto.panaderia.utils.cloudinary.CloudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductosServicioImpl implements ProductosServicio {
    private final ProductosRepositorio productosRepositorio;
    private final ProductosDTOMapper productosDTOMapper;
    private final CategoriaRepositorio categoriaRepositorio;
    private  final CloudService cloudService;
    private final EmpresaRepositorio empresaRepositorio;
    private final UnidadMedidaRepositorio unidadMedidaRepositorio;
    @Override
    public List<ProductosDTO> getProductos() {
        return productosRepositorio.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(productosDTOMapper)
                .toList();

    }

    @Override
    public Optional<ProductosDTO> getProductoById(Integer id) {
        return productosRepositorio.findById(id)
                .map(productosDTOMapper)
                .map(Optional::of)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public List<ProductosDTO> getProductosByEmpresa(Integer idempresa) {
        return productosRepositorio.getProductosByEmpresa(idempresa)
                .stream()
                .map(productosDTOMapper)
                .toList();
    }

    @Override
    @Transactional
    //TODO: SE INCLUIRA EL SERVICIO DE CLOUDINARY PARA SUBIR IMAGENES
    //AQUI RECIBIRA LOS DATOS USANDO  @ModelAttribute
    public void saveProducto(ProductosRequest productosRequest) {
        String nombreNormalizado = productosRequest.getNombre().toLowerCase().replace(" ", "");
        Optional<Productos> productoExistente = productosRepositorio.findByNombreIgnoreCaseAndTrimAndEmpresaId(nombreNormalizado,Integer.parseInt(productosRequest.getIdempresa()));
        if (productoExistente.isPresent()) {
            throw new ProductoExisteneException("Producto ya Existe");
        }
        Productos producto = new Productos();
        producto.setNombre(productosRequest.getNombre());
        producto.setDescripcion(productosRequest.getDescripcion());
        producto.setImagen(procesaImagen(productosRequest.getImagen()));
        producto.setPrecioBase(productosRequest.getPrecioBase());
        producto.setStock(productosRequest.getStock());
        producto.setStatus(true);
        producto.setCategoria(categoriaRepositorio.findById(Integer.parseInt(productosRequest.getIdcategoria()))
                .orElseThrow(()->
                        new CategoriaNotFoundException("Categoria no encontrada con id: " + productosRequest.getIdcategoria())));
        producto.setUnidadesMedida(unidadMedidaRepositorio.findById(Integer.parseInt(productosRequest.getIdunidadMedida()))
                .orElseThrow(()->new RuntimeException("Unidad de medida no encontrada con id: " + productosRequest.getIdunidadMedida())));
        producto.setEmpresa(empresaRepositorio.findById(Integer.parseInt(productosRequest.getIdempresa()))
                .orElseThrow(()->
                        new EmpresaNotFoundException("Empresa no encontrada con id: " + productosRequest.getIdempresa())));
        productosRepositorio.save(producto);
    }

    @Override
    @Transactional
    //TODO: SE INCLUIRA EL SERVICIO DE CLOUDINARY PARA SUBIR IMAGENES Y
    // SE IMPLEMTARA LA SOLCITUD PATCH
    public void updateProducto(ProductosRequest productosRequest, Integer id) {
        Productos producto = productosRepositorio.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        // Normalizar el nombre del producto a actualizar para la comprobación
        String nombreNormalizado = productosRequest.getNombre().trim().toLowerCase();
        Integer empresaId =Integer.parseInt(productosRequest.getIdempresa());

        // Verificar si ya existe un producto con el mismo nombre en la misma empresa, excluyendo el producto actual
        Optional<Productos> productoExistente = productosRepositorio.findByNombreIgnoreCaseAndTrimAndEmpresaIdExcludingId(nombreNormalizado, empresaId, id);
        if (productoExistente.isPresent()) {
            throw new ProductoExisteneException("Producto ya Existe en la sucursal");
        }
        producto.setNombre(productosRequest.getNombre());
        producto.setDescripcion(productosRequest.getDescripcion());
        producto.setImagen(productosRequest.getImagen() != null && !productosRequest.getImagen().isEmpty()
                ? procesaImagen(productosRequest.getImagen())
                : producto.getImagen());
        producto.setPrecioBase(productosRequest.getPrecioBase());
        producto.setStock(productosRequest.getStock());
        producto.setStatus(true);
        producto.setCategoria(categoriaRepositorio.findById(Integer.parseInt(productosRequest.getIdcategoria()))
                .orElseThrow(()->
                        new CategoriaNotFoundException("Categoria no encontrada con id: " + productosRequest.getIdcategoria())));
        producto.setUnidadesMedida(unidadMedidaRepositorio.findById(Integer.parseInt(productosRequest.getIdunidadMedida()))
                .orElseThrow(()->
                        new RuntimeException("Unidad de medida no encontrada con id: " + productosRequest.getIdunidadMedida())));
        producto.setEmpresa(empresaRepositorio.findById(Integer.parseInt(productosRequest.getIdempresa()))
                .orElseThrow(()->
                        new EmpresaNotFoundException("Empresa no encontrada con id: " + productosRequest.getIdempresa())));
        productosRepositorio.save(producto);
    }

    @Override
    @Transactional
    public void deleteProductoById(Integer id) {
        Productos producto = productosRepositorio.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        producto.setStatus(false);
        productosRepositorio.save(producto);
    }
    private String procesaImagen(MultipartFile imagen){
        return cloudService.uploadimg(imagen);
    }
}
