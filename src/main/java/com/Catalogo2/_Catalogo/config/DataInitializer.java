package com.Catalogo2._Catalogo.config;

import com.Catalogo2._Catalogo.model.Especificacion;
import com.Catalogo2._Catalogo.model.Producto;
import com.Catalogo2._Catalogo.repository.EspecificacionesRepository;
import com.Catalogo2._Catalogo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final EspecificacionesRepository especificacionesRepository;

    @Override
    public void run(String ... args){
        if (especificacionesRepository.count() > 0 ){
            log.info(">>>DataInitializer: la BD ya tiene datos, se omite la carga inicial");
            return;
        }
        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        // NOTEBOOK
        Producto notebook = new Producto(null, "Notebook Gamer TUF", "Computadores", 950000, "ASUS", null);
        notebook = productoRepository.save(notebook);
        Especificacion esp1 = new Especificacion(null, "Memoria RAM", "16GB DDR4 3200MHz", notebook);
        Especificacion esp2 = new Especificacion(null, "Almacenamiento", "1TB SSD NVMe M.2", notebook);
        especificacionesRepository.saveAll(List.of(esp1, esp2));
        notebook.setEspecificaciones(List.of(esp1, esp2));
        productoRepository.save(notebook);

        //MONITOR
        Producto monitor = new Producto(null, "Monitor UltraGear 24 pulg", "Periféricos", 150000, "LG", null);
        Especificacion esp3 = new Especificacion(null, "Resolución", "1920x1080 Full HD, 144Hz", monitor);
        monitor.setEspecificaciones(List.of(esp3));
        productoRepository.save(monitor);

        //FUENTE DE PODER
        Producto fuente = new Producto(null, "Fuente de Poder 650W", "Componentes", 85000, "Corsair", null);
        Especificacion esp4 = new Especificacion(null, "Potencia", "650W 80 Plus Bronze", fuente);
        fuente.setEspecificaciones(List.of(esp4));
        productoRepository.save(fuente);

        //ROUTER
        Producto router = new Producto(null, "Router Wi-Fi 6 Mesh", "Redes", 120000, "TP-Link", null);
        Especificacion esp5 = new Especificacion(null, "Conectividad", "Wi-Fi 6 y Bluetooth 5.2", router);
        router.setEspecificaciones(List.of(esp5));
        productoRepository.save(router);

        //RYZEN 5
        Producto ryzen = new Producto(null, "Ryzen 5 5600X", "Componentes", 180000, "AMD", null);
        Especificacion esp6 = new Especificacion(null, "Procesador", "6-Core 12-Thread", ryzen);
        ryzen.setEspecificaciones(List.of(esp6));
        productoRepository.save(ryzen);

        //PLACA MADRE
        Producto placa = new Producto(null, "Micro ATX B550M", "Componentes", 110000, "Gigabyte", null);
        Especificacion esp7 = new Especificacion(null, "Formato", "Micro ATX", placa);
        Especificacion esp8 = new Especificacion(null, "Puertos", "2x USB 3.0, Audio 3.5mm", placa);
        placa.setEspecificaciones(List.of(esp7, esp8));
        productoRepository.save(placa);

        //PC DESKTOP
        Producto desktop = new Producto(null, "PC Desktop Pro G1", "Computadores", 650000, "HP", null);
        Especificacion esp9 = new Especificacion(null, "Procesador", "AMD Ryzen 5 5600X 6-Core", desktop);
        Especificacion esp10 = new Especificacion(null, "Memoria RAM", "16GB DDR4 3200MHz", desktop);
        desktop.setEspecificaciones(List.of(esp9, esp10));
        productoRepository.save(desktop);

        log.info(">>> DataInitializer: Productos y sus especificaciones insertados correctamente.");

    }
}
