package guru.springframework.controller;

import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final UnitOfMeasureRepo unitOfMeasureRepo;

    public IndexController(UnitOfMeasureRepo unitOfMeasureRepo) {
        this.unitOfMeasureRepo = unitOfMeasureRepo;
    }

    @RequestMapping({"","/","/index","/index.html"})
    public String index(){
        Optional<UnitOfMeasure> unitOfMeasure= unitOfMeasureRepo.findByDescription("spoon");

        System.out.println("unitOfMeasure:"+unitOfMeasure.get().getDescription());

        return "index";
    }

}
