package ru.travin.springapp4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.travin.springapp4.dao.PersonDAO;
import ru.travin.springapp4.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        // Получаем всех людей из DAO и передаем на отображение в представление
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }
    @GetMapping("/{id}") // получаем id и передаем в контроллер для дальнейшей обработки
    public String show(@PathVariable("id") int id, Model model){
        //Получаем одного человека по id из DAO и передаем на отображение в представление
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }
    @GetMapping("/new") // создаем нового пустого человека
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping // передаем данные для нового человека
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people"; // с помощью redirect возвращаемся на страницу people после добавления нового человека
    }
    @GetMapping("/{id}/edit") // контроллер редактирования
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}") // контроллер обновления данных человека
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }
@DeleteMapping("/{id}") // контроллер удаления человека
    public String delete(@PathVariable("id") int id){
    personDAO.delete(id);
    return "redirect:/people";
    }

}
