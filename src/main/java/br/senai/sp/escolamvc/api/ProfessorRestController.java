package br.senai.sp.escolamvc.api;

import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
public class ProfessorRestController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/listar")
    public List<Professor> listar(){
        return professorRepository.findAll();
    }

    @PostMapping("/inserir")
    public void inserir(@RequestBody Professor professor){
        professorRepository.save(professor);
    }

    @PutMapping("/alterar")
    public void alterar(@RequestBody Professor aluno){
        professorRepository.save(aluno);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id){
        professorRepository.deleteById(id);
    }

    @PostMapping("/inserir-varios")
    public void inserirVarios(@RequestBody List<Professor> professors) {
        professorRepository.saveAll(professors);
    }

    @GetMapping("/buscar/{id}")
    public Professor buscar(@PathVariable Long id){
        return professorRepository.findById(id).get();
    }

    @GetMapping("/buscar-por-nome/{nome}")
    public List<Professor> buscarPorNome(@PathVariable String nome){
        return professorRepository.findProfessorsByNomeContaining(nome);
    }
}