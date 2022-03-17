package br.com.pb.exercicio.controller;

import br.com.pb.exercicio.controller.Dto.EstadoDto;
import br.com.pb.exercicio.controller.form.AtualizacaoEstadoForm;
import br.com.pb.exercicio.controller.form.EstadoForm;
import br.com.pb.exercicio.modelo.Estado;
import br.com.pb.exercicio.modelo.Regioes;
import br.com.pb.exercicio.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/states")
public class EstadosController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping//GET
    public Page<EstadoDto> listar(@RequestParam(required = false) Regioes regiao,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao){
    if(regiao == null){
        Page<Estado> estados = estadoRepository.findAll(paginacao);
        return EstadoDto.converter(estados);
    }else {
        Page<Estado> estados = estadoRepository.findByRegiao(regiao, paginacao);
        return EstadoDto.converter(estados);
        }
    }

    @PostMapping//POST
    @Transactional
    public ResponseEntity<EstadoDto> cadastrar(@RequestBody @Valid EstadoForm form, UriComponentsBuilder uriBuilder){
        Estado estado = form.converter();
        estadoRepository.save(estado);

        URI uri = uriBuilder.path("/api/states/{id}").buildAndExpand(estado.getId()).toUri();
        return ResponseEntity.created(uri).body(new EstadoDto(estado));
    }

    @GetMapping("/{id}")//GET
    public ResponseEntity<EstadoDto> detalhar(@PathVariable Long id){

        Optional<Estado> estado = estadoRepository.findById(id);
        if(estado.isPresent()){
            return ResponseEntity.ok(new EstadoDto(estado.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}") //PUT
    @Transactional
    public ResponseEntity<EstadoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoEstadoForm form ){
        Optional<Estado> optional = estadoRepository.findById(id);
        if(optional.isPresent()){
            Estado estado = form.atualizar(id, estadoRepository);
            return ResponseEntity.ok(new EstadoDto(estado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        Optional<Estado> optional = estadoRepository.findById(id);
        if(optional.isPresent()) {
            estadoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


