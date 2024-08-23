package security.jwt.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import security.jwt.dto.JoinDTO;
import security.jwt.service.JoinService;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;


    @GetMapping("/join")
    public String join(Model model) {

        model.addAttribute("joinDTO", new JoinDTO());

        return "join";
    }


    @PostMapping("/join/new")
    public String joinProcess(@Valid JoinDTO joinDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "join";
        }

        joinService.join(joinDTO);

        return "redirect:/login";
    }
}
