package tacos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import tacos.models.Ingredient;
import tacos.models.Ingredient.Type;
import tacos.models.Taco;
import tacos.models.TacoOrder;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;
    public DesignTacoController(
    		IngredientRepository ingredientRepository,
    		TacoRepository tacoRepository
    		) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository =  tacoRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        Type[] types = Ingredient.Type.values();

        for (Type type : types) {
            model.addAttribute( type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    private Iterable<Ingredient> filterByType(
            Iterable<Ingredient> ingredients,
            Type type) {

        List<Ingredient> filteredIngredients =
            new ArrayList<>();

        for (Ingredient ingredient : ingredients) {

            if (ingredient.getType().equals(type)) {
                filteredIngredients.add(ingredient);
            }
        }

        return filteredIngredients;
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {

        if (errors.hasErrors()) {
            return "design";
        }
        
        Taco savedTaco = tacoRepository.save(taco);

        tacoOrder.addTaco(savedTaco);

        log.info("Elaborazione del taco: {}", taco);

        return "redirect:/orders/current";
    }
}