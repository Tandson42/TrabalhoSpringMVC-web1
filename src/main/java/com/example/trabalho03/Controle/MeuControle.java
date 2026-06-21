package com.example.trabalho03.Controle;

import com.example.trabalho03.DAO.*;
import com.example.trabalho03.Modelo.Categoria;
import com.example.trabalho03.Modelo.Tarefa;
import com.example.trabalho03.Modelo.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("logar")
    public String logar(@ModelAttribute Usuario u, HttpSession session, RedirectAttributes atributos){
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
        } else {
            atributos.addAttribute("mensagem", "Precisa informar login e senha");
        }
        return "redirect:/";
    }

    @PostMapping("cadastrarusuario")
    public String cadastrarUsuario(@ModelAttribute Usuario u, BindingResult result, RedirectAttributes atributos) {
        if (u.getNome() != null && !u.getNome().isBlank() && u.getLogin() != null && !u.getLogin().isBlank() && u.getSenha() != null && !u.getSenha().isBlank()) {
            try {
                u.setAdmin(false);
                UsuarioDaoInterface dao = new UsuarioDaoJdbc();
                dao.salvar(u);
                dao.sair();
                atributos.addAttribute("mensagem","Cadastrado com sucesso");
            } catch (ErroDao e) {
                atributos.addAttribute("mensagem","Usuário Já Existe");
            }
        } else {
            atributos.addAttribute("mensagem","Preencha o formulário inteiro");
        }
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, RedirectAttributes atributos) {
        session.invalidate();
        atributos.addAttribute("mensagem", "Deslogado com sucesso.");
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
            TarefaDaoInterface dao = new TarefaDaoJdbc();
            List<Tarefa> tarefas = dao.buscar(u);
            dao.sair();
            model.addAttribute("tarefas", tarefas);
            return "vertarefas";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao carregar as tarefas");
            return "redirect:/menu";
        }
    }

    @GetMapping("cadastrartarefas")
    public String cadastrarTarefasForm(HttpSession session, Model model, RedirectAttributes atributos){
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
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

    @PostMapping("cadastrartarefas")
    public String cadastrartarefas(@ModelAttribute Tarefa t, BindingResult result, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
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
            atributos.addAttribute("mensagem", "Erro ao tentar salvar a tarefa");
            return "redirect:/cadastrartarefas";
        }
    }

    @GetMapping("deletartarefa")
    public String deletarTarefa(@ModelAttribute Tarefa t, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }

        try {
            TarefaDaoInterface dao = new TarefaDaoJdbc();
            dao.deletar(t);
            dao.sair();
            atributos.addAttribute("mensagem", "Tarefa excluída com sucesso.");
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao excluir tarefa.");
        }
        return "redirect:/vertarefas";
    }

    @GetMapping("editartarefa")
    public String editarTarefaForm(@ModelAttribute Tarefa t, HttpSession session, Model model, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }

        try {
            TarefaDaoInterface daoT = new TarefaDaoJdbc();
            Tarefa tarefa = daoT.buscar(t.getId(), u);
            daoT.sair();

            CategoriaDaoInterface daoC = new CategoriaDaoJdbc();
            List<Categoria> categorias = daoC.buscar();
            daoC.sair();

            model.addAttribute("tarefa", tarefa);
            model.addAttribute("categorias", categorias);
            return "editartarefa";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao buscar a tarefa para edição.");
            return "redirect:/vertarefas";
        }
    }

    @PostMapping("editartarefa")
    public String editarTarefaPost(@ModelAttribute Tarefa t, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }

        try {
            TarefaDaoInterface dao = new TarefaDaoJdbc();
            dao.editar(t);
            dao.sair();
            atributos.addAttribute("mensagem", "Tarefa atualizada com sucesso!");
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao atualizar a tarefa.");
        }
        return "redirect:/vertarefas";
    }

    @GetMapping("alterarcadastro")
    public String alterarCadastroForm(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        return "alterarcadastro";
    }

    @PostMapping("alterarcadastro")
    public String alterarCadastroPost(@ModelAttribute Usuario usuarioAtualizado, HttpSession session, RedirectAttributes atributos) {
        Usuario uSessao = (Usuario) session.getAttribute("usuario");
        if (uSessao == null) {
            return "redirect:/";
        }

        try {
            usuarioAtualizado.setId(uSessao.getId());
            usuarioAtualizado.setAdmin(uSessao.isAdmin());

            UsuarioDaoInterface dao = new UsuarioDaoJdbc();
            dao.editar(usuarioAtualizado.getId(), usuarioAtualizado.getNome(), usuarioAtualizado.getLogin(), usuarioAtualizado.getSenha());
            dao.sair();

            session.setAttribute("usuario", usuarioAtualizado);
            atributos.addAttribute("mensagem", "Cadastro alterado com sucesso!");
            return "redirect:/menu";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao alterar cadastro.");
            return "redirect:/alterarcadastro";
        }
    }

    @GetMapping("cadastraradmin")
    public String cadastrarAdminForm(HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }
        return "cadastraradmin";
    }

    @PostMapping("cadastraradmin")
    public String cadastrarAdminPost(@ModelAttribute Usuario novoAdmin, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }

        try {
            novoAdmin.setAdmin(true);
            UsuarioDaoInterface dao = new UsuarioDaoJdbc();
            dao.salvar(novoAdmin);
            dao.sair();
            atributos.addAttribute("mensagem", "Administrador cadastrado com sucesso!");
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro: O login já existe ou ocorreu um problema no banco.");
        }
        return "redirect:/cadastraradmin";
    }

    @GetMapping("vercategorias")
    public String verCategorias(HttpSession session, Model model, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }
        try {
            CategoriaDaoInterface dao = new CategoriaDaoJdbc();
            List<Categoria> categorias = dao.buscar();
            dao.sair();
            model.addAttribute("categorias", categorias);
            return "vercategorias";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao buscar categorias.");
            return "redirect:/menu";
        }
    }

    @GetMapping("cadastrarcategoria")
    public String cadastrarCategoriaForm(HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }
        return "cadastrarcategoria";
    }

    @PostMapping("adicionarcategoria")
    public String adicionarCategoria(@ModelAttribute Categoria c, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }

        if (c.getNome() != null && !c.getNome().isBlank()) {
            try {
                CategoriaDaoInterface dao = new CategoriaDaoJdbc();
                dao.adicionar(c.getNome());
                dao.sair();
                atributos.addAttribute("mensagem", "Categoria adicionada com sucesso!");
            } catch (ErroDao e) {
                atributos.addAttribute("mensagem", "Erro ao adicionar categoria.");
            }
        } else {
            atributos.addAttribute("mensagem", "O nome da categoria não pode estar vazio.");
            return "redirect:/vercategorias";
        }
        return "redirect:/vercategorias";
    }

    @GetMapping("deletarcategoria")
    public String deletarCategoria(@ModelAttribute Categoria c, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }

        try {
            CategoriaDaoInterface dao = new CategoriaDaoJdbc();
            dao.remover(c.getId());
            dao.sair();
            atributos.addAttribute("mensagem", "Categoria removida com sucesso.");
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao remover categoria.");
        }
        return "redirect:/vercategorias";
    }

    @GetMapping("editarcategoria")
    public String editarCategoriaForm(@ModelAttribute Categoria c, HttpSession session, Model model, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }

        try {
            CategoriaDaoInterface dao = new CategoriaDaoJdbc();
            Categoria categoriaEncontrada = dao.buscar(c.getId());
            dao.sair();

            if (categoriaEncontrada == null) {
                atributos.addAttribute("mensagem", "Categoria não encontrada.");
                return "redirect:/vercategorias";
            }

            model.addAttribute("categoria", categoriaEncontrada);
            return "editarcategoria";
        } catch (ErroDao e) {
            atributos.addAttribute("mensagem", "Erro ao buscar a categoria.");
            return "redirect:/vercategorias";
        }
    }

    @PostMapping("editarcategoria")
    public String editarCategoriaPost(@ModelAttribute Categoria c, HttpSession session, RedirectAttributes atributos) {
        Usuario u = (Usuario) session.getAttribute("usuario");
        if (u == null) {
            return "redirect:/";
        }
        if (!u.isAdmin()) {
            atributos.addAttribute("mensagem", "Acesso negado.");
            return "redirect:/menu";
        }

        if (c.getNome() != null && !c.getNome().isBlank()) {
            try {
                CategoriaDaoInterface dao = new CategoriaDaoJdbc();
                dao.editar(c.getId(), c.getNome());
                dao.sair();
                atributos.addAttribute("mensagem", "Categoria atualizada com sucesso!");
            } catch (ErroDao e) {
                atributos.addAttribute("mensagem", "Erro ao atualizar a categoria.");
            }
        } else {
            atributos.addAttribute("mensagem", "O nome da categoria não pode estar vazio.");
        }
        return "redirect:/vercategorias";
    }
}