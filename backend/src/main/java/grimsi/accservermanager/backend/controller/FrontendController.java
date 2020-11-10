package grimsi.accservermanager.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping(value = "/**/{path:[^\\.]*}")
    public String gui() {
        return "forward:/index.html";
    }
}
