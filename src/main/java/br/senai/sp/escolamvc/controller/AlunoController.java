package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    AlunoRepository alunoRepository;

    @PostMapping("/buscar")
    public String buscar(@Param("nome") String nome, Model model) {
        if(nome == null){
            model.addAttribute("aluno", alunoRepository.findAll());
            return "aluno/listagem";
        }
        List<Aluno> alunosBuscadosNoBanco =
                alunoRepository.findAlunosByNomeContaining(nome);
        model.addAttribute("alunos", alunosBuscadosNoBanco);
        return "aluno/listagem";
    }

    /*
     * Método que direciona para templates/alunos/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de alunos no banco de dados
        List<Aluno> listaAlunos = alunoRepository.findAll();

        // Adiciona a lista de alunos no objeto model para ser carregado no template
        model.addAttribute("alunos", listaAlunos);

        // Retorna o template aluno/listagem.html
        return "aluno/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("aluno", new Aluno());
        // templates/aluno/inserir.html
        return "aluno/inserir";
    }

    /*
     * Método para excluir um aluno
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        alunoRepository.delete(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }
    @PostMapping("/salvar")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result,
                              RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/inserir.html
        if (result.hasErrors()) {
            return "aluno/inserir";
        }

        // Salva o aluno no banco de dados
        alunoRepository.save(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno/form-inserir";
    }

    /*
     * Método que direciona para templates/alunos/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("aluno", aluno);

        // Retorna o template aluno/alterar.html
        return "aluno/alterar";
    }
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, @Valid Aluno aluno,
                          BindingResult result, RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/alterar.html
        if (result.hasErrors()) {
            return "aluno/alterar";
        }

        // Busca o aluno no banco de dados
        Aluno alunoAtualizado = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));


        // Seta os dados do aluno
        alunoAtualizado.setMatricula(aluno.getMatricula());
        alunoAtualizado.setNome(aluno.getNome());
        alunoAtualizado.setEmail(aluno.getEmail());

        // Salva o aluno no banco de dados
        alunoRepository.save(alunoAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno atualizado com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }
}