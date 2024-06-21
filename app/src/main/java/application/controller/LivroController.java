package application.controller;

public package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.repository.GeneroRepository;
import application.repository.LivroRepository;

import application.model.Livro;
import application.model.Genero;;

@Controller
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroRepository livroRepo;
    @Autowired
    private GeneroRepository generoRepo;

    @RequestMapping("/list")
    public String list(Model ui) {
        ui.addAttribute("livros", livroRepo.findAll());
        return "/livros/list";
    }

    @RequestMapping("/insert")
    public String insert(Model ui) {
        ui.addAttribute("generos", generoRepo.findAll());
        return "/livros/insert";
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(
            @RequestParam("titulo") String titulo,
            @RequestParam("genero") long generoId) {
        Optional<Genero> resultGenero = generoRepo.findById(generoId);
        if (resultGenero.isPresent()) {
            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setGenero(resultGenero.get());

            livroRepo.save(livro);
        }
        return "redirect:/livros/list";
    }

    @RequestMapping("/update")
    public String update(Model ui, @RequestParam("id") long id) {
        Optional<Livro> resultLivro = livroRepo.findById(id);
        if (resultLivro.isPresent()) {
            ui.addAttribute("livro", resultLivro.get());
            ui.addAttribute("generos", generoRepo.findAll());
            return "/livros/update";
        }
        return "redirect:/livros/list";
    }
    @RequestMapping (value = "/update", method = RequestMethod.POST)
    public String update(
            @RequestParam("id") long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("genero") long generoId) {
        Optional<Livro> resultLivro = livroRepo.findById(id);
        if (resultLivro.isPresent()) {
            Optional<Genero> resultGenero = generoRepo.findById(generoId);
            if (resultGenero.isPresent()) {
                resultLivro.get().setTitulo(titulo);
                resultLivro.get().setGenero(resultGenero.get());

                livroRepo.save(resultLivro.get());
            }
        }
        return "redirect:/livros/list";
    }

    @RequestMapping("/delete")
    public String delete( Model ui, @RequestParam("id") long id){
        Optional<Livro> resultLivro = livroRepo.findById(id);
        if(resultLivro.isPresent()){
            ui.addAttribute("livro", resultLivro.get());
            return "/livros/delete";
        }
        return "redirect:/livros/list";
    }
@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete( @RequestParam("id") long id){
        livroRepo.deleteById(id);
        return "redirect:/livros/list";
    }
}
 {
    
}
