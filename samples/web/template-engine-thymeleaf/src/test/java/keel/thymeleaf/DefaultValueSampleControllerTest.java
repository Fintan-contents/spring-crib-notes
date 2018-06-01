package keel.thymeleaf;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import keel.thymeleaf.DefaultValueSampleController.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@WebMvcTest(DefaultValueSampleController.class)
public class DefaultValueSampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 選択しなかった場合Formにはデフォルト値が設定されます() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/default")
                            .param("_checkbox", "on")
                            .param("_checkboxes", "on")
                            .param("_selectOptions", "on"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("form", 
                       allOf(
                               hasProperty("checkbox", is(false)),
                               hasProperty("checkboxes", empty()),
                               hasProperty("selectOptions", empty())
                       )));
        // @formatter:on
    }

    @Test
    public void 選択した場合はその値がFormに設定されること() throws Exception {
        // @formatter:off
        mockMvc.perform(post("/default")
                            .param("checkbox", "true")
                            .param("_checkbox", "on")
                            .param("checkboxes", "NO1", "NO4")
                            .param("_checkboxes", "on")
                            .param("selectOptions", "NO2", "NO3")
                            .param("_selectOptions", "on"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("form", 
                       allOf(
                               hasProperty("checkbox", is(true)),
                               hasProperty("checkboxes", contains(Options.NO1, Options.NO4)),
                               hasProperty("selectOptions", contains(Options.NO2, Options.NO3))
                       )));
        // @formatter:on
    }
}