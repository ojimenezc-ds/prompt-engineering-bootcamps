package com.bootcamps.Prompt.engineering.bootcamps.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
class HomeController {

    @GetMapping("/")
    fun home(): RedirectView {
        return RedirectView("/swagger-ui/index.html")
    }
}
