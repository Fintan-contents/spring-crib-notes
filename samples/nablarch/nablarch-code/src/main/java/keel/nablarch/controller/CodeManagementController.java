package keel.nablarch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class CodeManagementController {

    @GetMapping
    public String index(CodeManagementForm form) {
        return "index";
    }

    @PostMapping
    public ModelAndView add(@Valid @ModelAttribute CodeManagementForm form, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("index", HttpStatus.BAD_REQUEST);
        }

        redirectAttributes.addFlashAttribute("successMessage", "入力値のチェックが完了しました。");
        return new ModelAndView("redirect:/");
    }
}
