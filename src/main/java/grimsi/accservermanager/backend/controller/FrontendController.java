package grimsi.accservermanager.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = "/**/{path:[^\\.]*}")
    public String gui() {
        return "forward:/index.html";
    }
}
