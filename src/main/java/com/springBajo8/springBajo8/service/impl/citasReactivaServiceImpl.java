package com.springBajo8.springBajo8.service.impl;

//import com.yoandypv.reactivestack.messages.domain.Message;
//import com.yoandypv.reactivestack.messages.repository.MessageRepository;
//import com.yoandypv.reactivestack.messages.service.MessageService;
import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.repository.IcitasReactivaRepository;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
public class citasReactivaServiceImpl implements IcitasReactivaService {

    @Autowired
    private IcitasReactivaRepository IcitasReactivaRepository;

    @Override
    public Mono<citasDTOReactiva> save(citasDTOReactiva citasDTOReactiva) {
        return this.IcitasReactivaRepository.save(citasDTOReactiva);
    }

    @Override
    public Mono<citasDTOReactiva> delete(String id) {
        return this.IcitasReactivaRepository
                .findById(id)
                .flatMap(p -> this.IcitasReactivaRepository.deleteById(p.getId()).thenReturn(p));

    }

    @Override
    public Mono<citasDTOReactiva> update(String id, citasDTOReactiva citasDTOReactiva) {
        return this.IcitasReactivaRepository.findById(id)
                .flatMap(citasDTOReactiva1 -> {
                    citasDTOReactiva.setId(id);
                    return save(citasDTOReactiva);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<citasDTOReactiva> cancelCita(String id){
         return this.IcitasReactivaRepository.findById(id)
                .flatMap(citaMono -> {
                    citaMono.setEstadoReservaCita("Cancelado");
                    return save(citaMono);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<citasDTOReactiva> findByIdPaciente(String idPaciente) {
        return this.IcitasReactivaRepository.findByIdPaciente(idPaciente);
    }

    @Override
    public Mono<String> findMedicoById(String id){
        return this.IcitasReactivaRepository.findById(id)
                .map(monocitas -> {
                    return (monocitas.getApellidosMedico() + " " + monocitas.getNombreMedico());
                });
    }

    @Override
    public Flux<citasDTOReactiva> findCitaByFecha(LocalDate fecha){
        return this.IcitasReactivaRepository.findAll()
                .filter(cita -> cita.getFechaReservaCita().equals(fecha));
    }

    @Override
    public Mono<String> getPadecimientoByUsuario(String id){
        return this.IcitasReactivaRepository.findById(id)
                .map(monocitas -> {
                    return (monocitas.getPadecimientos());
                });
    }


    @Override
    public Flux<citasDTOReactiva> findAll() {
        return this.IcitasReactivaRepository.findAll();
    }

    @Override
    public Mono<citasDTOReactiva> findById(String id) {
        return this.IcitasReactivaRepository.findById(id);
    }
}
