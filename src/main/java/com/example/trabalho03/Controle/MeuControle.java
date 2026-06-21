package com.example.trabalho03.Controle;

import com.example.trabalho03.DAO.*;
import com.example.trabalho03.Modelo.Categoria;
import com.example.trabalho03.Modelo.Tarefa;
import com.example.trabalho03.Modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MeuControle {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("menu")
    public String menu(HttpSession session, RedirectAttributes atributos){
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            atributos.addAttribute("mensagem", "Você precisa se logar para acessar o menu.");
            return "redirect:/";
        }
        return "menu";
    }

    @PostMapping("cadastrarusuario")
    public String cadastrarUsuario(@ModelAttribute Usuario u, BindingResult result, RedirectAttributes atributos) {
        if (u.getNome() != null && !u.getNome().isBlank() && u.getLogin() != null && !u.getLogin().isBlank() && u.getSenha() != null && !u.getSenha().isBlank()) {
            try {
                UsuarioDaoInterface dao = new UsuarioDaoJdbc();
                dao.salvar(u);
                dao.sair();

                atributos.addAttribute("mensagem","Cadastrado com sucesso");
            } catch (ErroDao e) {
                atributos.addAttribute("mensagem","Usuário Já Existe");
            }
        }
        else {
            atributos.addAttribute("mensagem","Preencha o formulário inteiro");
        }
        return "redirect:/";
    }

    @GetMapping("cadastrartarefas")
    public String cadastrarTarefas(HttpSession session, Model model, RedirectAttributes atributos){
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            atributos.addAttribute("mensagem", "Você precisa se logar para acessar o menu.");
            return "redirect:/";
        }
        try {
            CategoriaDaoInterface dao = new CategoriaDaoJdbc();
            List<Categoria> categorias = dao.buscar();
            dao.sair();
            model.addAttribute("categorias", categorias);
            return "cadastrartarefas";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao carregar categorias");
            return "redirect:/menu";
        }
    }

    @PostMapping("logar")
    public String logar(Usuario u, HttpSession session, RedirectAttributes atributos){
        if (u.getLogin() != null && !u.getLogin().isBlank() && u.getSenha() != null && !u.getSenha().isBlank()) {
            try {
                UsuarioDaoInterface dao = new UsuarioDaoJdbc();
                Usuario uAutenticado = dao.buscar(u.getLogin(), u.getSenha());
                dao.sair();

                if (uAutenticado != null) {
                    session.setAttribute("usuario", uAutenticado);
                    return "redirect:/menu";
                } else {
                    atributos.addAttribute("mensagem", "Login/Senha Incorretos");
                }
            } catch (ErroDao e) {
                atributos.addAttribute("mensagem", "Erro ao efetuar login");
            }
        }
        else {
            atributos.addAttribute("mensagem", "Precisa informar login e senha");
            return "redirect:/";
        }
        return "redirect:/";
    }

    @GetMapping("vertarefas")
    public String verTarefas(HttpSession session, Model model, RedirectAttributes atributos){
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            atributos.addAttribute("mensagem", "Você precisa se logar para acessar o menu.");
            return "redirect:/";
        }
        try {
            TarefaDaoInterface dao=new TarefaDaoJdbc();
            List<Tarefa> tarefas= dao.buscar(u);
            dao.sair();
            System.out.println("Quantidade de tarefas encontradas: " + tarefas.size());
            model.addAttribute("tarefas",tarefas);
            return "vertarefas";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao carregar as tarefas");
            return "redirect:/menu";
        }
    }
    @PostMapping("/cadastrartarefas") // Boa prática: colocar a "/"
    public String cadastrartarefas(@ModelAttribute Tarefa t, BindingResult result, HttpSession session, RedirectAttributes atributos) {

        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            atributos.addAttribute("mensagem", "Você precisa se logar para acessar o menu.");
            return "redirect:/";
        }

        if (result.hasErrors()) {
            atributos.addAttribute("mensagem", "Erro no formato dos dados enviados.");
            return "redirect:/cadastrartarefas";
        }

        if (t.getTitulo() == null || t.getTitulo().isBlank() ||
                t.getDescricao() == null || t.getDescricao().isBlank() ||
                t.getCategoria() == null || t.getCategoria().getId() <= 0) {

            atributos.addAttribute("mensagem", "Preencha todos os campos, incluindo a categoria");
            return "redirect:/cadastrartarefas";
        }

        try {
            TarefaDaoInterface dao = new TarefaDaoJdbc();
            dao.salvar(t, u);
            dao.sair();

            atributos.addAttribute("mensagem", "Tarefa adicionada com sucesso");
            return "redirect:/vertarefas";

        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao tentar salvar a tarefa: " + e.getMessage());
            return "redirect:/cadastrartarefas";
        }
    }
}