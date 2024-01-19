package com.proyecto.panaderia;

import com.proyecto.panaderia.entity.Empresa;
import com.proyecto.panaderia.entity.Perfiles;
import com.proyecto.panaderia.repository.EmpresaRepositorio;
import com.proyecto.panaderia.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class PanaderiaApplication {
	private final PerfilRepository perfilRepository;
	private final EmpresaRepositorio empresaRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(PanaderiaApplication.class, args);
	}


}
