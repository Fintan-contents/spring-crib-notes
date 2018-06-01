package keel.thymeleaf;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/default")
public class DefaultValueSampleController {

    @ModelAttribute
    public Form form() {
        return new Form();
    }

    @GetMapping
    public String get() {
        return "default/input";
    }

    @PostMapping
    public String post(Form form, Model model) {
        model.addAttribute("form", form);
        return "default/completion";
    }

    public enum Options {
        NO1,
        NO2,
        NO3,
        NO4
    }

    public static class Form {

        // form-start
        private Boolean checkbox;

        private List<Options> checkboxes;

        private List<Options> selectOptions;
        // form-end

        public Boolean getCheckbox() {
            return checkbox;
        }

        public void setCheckbox(final Boolean checkbox) {
            this.checkbox = checkbox;
        }

        public List<Options> getCheckboxes() {
            return checkboxes;
        }

        public void setCheckboxes(final List<Options> checkboxes) {
            this.checkboxes = checkboxes;
        }

        public List<Options> getSelectOptions() {
            return selectOptions;
        }

        public void setSelectOptions(final List<Options> selectOptions) {
            this.selectOptions = selectOptions;
        }
    }
}
