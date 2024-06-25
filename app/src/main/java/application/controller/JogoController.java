package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.repository.GeneroRepository;
import application.repository.JogoRepository;

import application.model.Jogo;
import application.model.Genero;;

@Controller
@RequestMapping("/jogos")
public class JogoController {
    @Autowired
    private JogoRepository jogoRepo;
    @Autowired
    private GeneroRepository generoRepo;

    @RequestMapping("/list")
    public String list(Model ui) {
        ui.addAttribute("jogos", jogoRepo.findAll());
        return "/jogos/list";
    }

    @RequestMapping("/insert")
    public String insert(Model ui) {
        ui.addAttribute("generos", generoRepo.findAll());
        return "/jogos/insert";
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public String insert(
            @RequestParam("titulo") String titulo,
            @RequestParam("genero") long generoId) {
        Optional<Genero> resultGenero = generoRepo.findById(generoId);
        if (resultGenero.isPresent()) {
            Jogo jogo = new Jogo();
            jogo.setTitulo(titulo);
            jogo.setGenero(resultGenero.get());

            jogoRepo.save(jogo);
        }
        return "redirect:/jogos/list";
    }

    @RequestMapping("/update")
    public String update(Model ui, @RequestParam("id") long id) {
        Optional<Jogo> resultJogos = jogoRepo.findById(id);
        if (resultJogos.isPresent()) {
            ui.addAttribute("jogo", resultJogos.get());
            ui.addAttribute("generos", generoRepo.findAll());
            return "/jogos/update";
        }
        return "redirect:/jogos/list";
    }
    @RequestMapping (value = "/update", method = RequestMethod.POST)
    public String update(
            @RequestParam("id") long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("genero") long generoId) {
        Optional<Jogo> resultJogo = jogoRepo.findById(id);
        if (resultJogo.isPresent()) {
            Optional<Genero> resultGenero = generoRepo.findById(generoId);
            if (resultGenero.isPresent()) {
                resultJogo.get().setTitulo(titulo);
                resultJogo.get().setGenero(resultGenero.get());

                jogoRepo.save(resultJogo.get());
            }
        }
        return "redirect:/jogos/list";
    }

    @RequestMapping("/delete")
    public String delete( Model ui, @RequestParam("id") long id){
        Optional<Jogo> resultJogo = jogoRepo.findById(id);
        if(resultJogo.isPresent()){
            ui.addAttribute("jogo", resultJogo.get());
            return "/jogos/delete";
        }
        return "redirect:/jogos/list";
    }
@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete( @RequestParam("id") long id){
        jogoRepo.deleteById(id);
        return "redirect:/jogos/list";
    }
}
