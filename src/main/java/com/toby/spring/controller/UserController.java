package com.toby.spring.controller;

import com.toby.spring.domain.User;
import com.toby.spring.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String test(){
        return "hi";
    }

    @RequestMapping(value="/hello2", method = RequestMethod.GET)
    public String test2(){
        return "hi2";
    }

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult/*, RedirectAttributes redirectAttributes*/){
        user.setName("kimm");
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for(ObjectError objectError:allErrors){
            System.out.println(objectError.toString());
        }
//        redirectAttributes.addAttribute("queryParam","val");
//        redirectAttributes.addFlashAttribute("flashMap","val2");
        return "hello";
    }

    @RequestMapping(value="/user2", method = RequestMethod.POST)
    public String registerUser_redirect(@ModelAttribute @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        user.setName("kimm");
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for(ObjectError objectError:allErrors){
            System.out.println(objectError.toString());
        }
        redirectAttributes.addAttribute("queryParam","val");
        redirectAttributes.addFlashAttribute("flashMap","val2");
        return "redirect:/hello";
    }

    @RequestMapping(value="/user3", method = RequestMethod.POST)
    public String registerUser_No_Binding(@ModelAttribute @Valid User user, RedirectAttributes redirectAttributes){
        user.setName("kimm");
        return "hello";
    }

    @RequestMapping(value="/user4", method = RequestMethod.POST)
    public String registerUser_occur_checkedException(@ModelAttribute @Valid User user) throws Exception {
        user.setName("kimm");
        if(user.getName().equals("kimm")){
            throw new Exception("test error");
        }
        return "hello";
    }

    @RequestMapping(value="/user5", method = RequestMethod.POST)
    public String registerUser_requestBody(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception {

        return "hello";
    }

    @RequestMapping(value="/user6", method = RequestMethod.POST)
    public String registerUser_postParam_requestBody_string(@ModelAttribute @Valid User user, @RequestBody String body) throws Exception {

        return "hello";
    }

    @RequestMapping(value="/user7", method = RequestMethod.POST)
    @ResponseBody
    public User registerUser_body(@RequestBody @Valid User user) throws Exception {

        return user;
    }

    @RequestMapping(value="/user8", method = RequestMethod.POST)
    @ResponseBody
    public User registerUser_Exception_ResponseStatusException(@RequestBody @Valid User user)  {

        try{
            if(user!=null){
                throw new Exception("test checked exception");
            }
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"reason",e);
        }

        return user;
    }


    /**
     * 내가 임의로 json만들어서 전달하면 content type을 application/json으로 명시해야할 뿐 아니라, 꼭 charset을 utf8로 맞춰줄것 (한글포함일떄)
     * content type은 produces로 지정할수있음(produces는 client의 Accept 헤더와 관련있는데, 어떤 content type을 클라가 받을수있는지를 알려주는것)
     * */
    @RequestMapping(value="/user9", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String registerUser_produces_string_to_json(@RequestBody @Valid User user)  {
        return "{\"bye\":\"안녕\"}";
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView bindExceptionHandler(Exception e){
        return new ModelAndView("view",new HashMap<String,String>(){{this.put("error",e.getMessage());}});
    }



    @ModelAttribute
    public List<String> autoRegModel(){
        return new ArrayList<String> (){{this.add("abc"); this.add("bbb");}};
    }

    @ModelAttribute
    public void autoRegModel2(Model model){
        ArrayList<String> list=new ArrayList<String> (){{this.add("ccc"); this.add("ddd");}};
        model.addAttribute("list",list);
        model.addAttribute("key1","value1");
        model.addAttribute("key2","value2");
        model.addAttribute("key3","value3");
        model.addAttribute("key4","value4");

    }
}
