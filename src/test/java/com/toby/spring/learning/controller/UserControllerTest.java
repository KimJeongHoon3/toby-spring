package com.toby.spring.learning.controller;

import com.toby.spring.config.WebConfig;
import com.toby.spring.controller.UserController;
import com.toby.spring.domain.Level;
import com.toby.spring.formatter.LevelFormatter;
import com.toby.spring.service.UserService;
import org.hamcrest.beans.HasProperty;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest({UserController.class, /*LevelFormatter.class,*/ WebConfig.class})
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    void testHello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hi"))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    void testHello2() throws Exception {
        mockMvc.perform(get("/hello2"))
                .andExpect(status().isOk())
                .andExpect(view().name("hi2"))
                //.andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/user").param("name","kim")
                                                .param("age","16")
                                                .param("hobby","basketball")
                                                .param("level","1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user","list"))
                .andExpect(model().attribute("user",hasProperty("name",is("kimm"))))
                .andExpect(model().attribute("user",hasProperty("age",is(16))))
                .andExpect(model().attribute("user",hasProperty("hobby",is("basketball"))))
                .andExpect(model().attribute("user",hasProperty("level",is(Level.BASIC))))
                .andExpect(view().name("hello"))
                .andDo(print());

    }

    @Test
    void testRegisterUser_redirect_queryParam_flashMap() throws Exception {
        mockMvc.perform(post("/user2").param("name","kim")
                .param("age","16")
                .param("hobby","basketball")
                .param("level","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("user","list"))
                .andExpect(flash().attribute("flashMap","val2"))
                .andExpect(redirectedUrl("/hello?queryParam=val"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_validation_error_bindingResult() throws Exception {
        mockMvc.perform(post("/user").param("name","kim")
                .param("age","16")
                .param("hobby","basketball")
                .param("level","?????????"))
                .andExpect(status().isOk()) //hnadlerException ????????? ????????? ?????? ???????????? ?????????
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","level"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_validation_error_noBindingResult_handlerException() throws Exception {
        //bindingResult??? ????????????????????? BindException ???????????????.. ????????? BindExcepction??? @ExceptionHandler??? ??????????????? ?????????????????????
        mockMvc.perform(post("/user3").param("name","kim")
                .param("age","16")
                .param("hobby","basketball")
                .param("level","?????????"))
                .andExpect(status().isOk()) //hnadlerException ????????? ????????? ?????? ???????????? ?????????
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("error"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_checkedException_handlerException() throws Exception {
        mockMvc.perform(post("/user4").param("name","kim")
                .param("age","16")
                .param("hobby","basketball")
                .param("level","1"))
                .andExpect(status().isBadGateway())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("{\"code\":\"E999\",\"message\":\"test error\"}"))
                .andDo(print());
    }


    @Test
    void testRegisterUser_requestBody_?????????????????????BindingResult????????????_Model?????????????????????_??????????????????() throws Exception {
        mockMvc.perform(post("/user5").content("{\n" +
//                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"GOLD\"\n" + //formatter??? ??????????????? ?????????, jackson ???????????? json ????????? ????????? enum??? ????????????., jackson ?????? ???????????? BindingResult??? ?????? ???????????? HttpMessageNotReadableException ?????? ??????..
//                "    \"level\":1" + //????????? ????????? ????????? enum??? ????????? ?????????????????? ????????????????????????????????? ?????? ????????? ?????????..
                "}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(view().name("hello"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_param_body_??????????????????() throws Exception {
        mockMvc.perform(post("/user6").param("name","kim")
                    .param("age","16")
                    .param("hobby","basketball")
                    .param("level","1")
                    .content("hhahahah"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_body_validation_handlerException() throws Exception {
        mockMvc.perform(post("/user7").content("{\n" +
                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"?????????\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadGateway()) //Exception
                .andExpect(content().json("{\"code\":\"E999\"}"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_body_validation_??????() throws Exception {
        mockMvc.perform(post("/user7").content("{\n" +
                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"1\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()) //Exception
                .andExpect(content().json("{\n" +
                        "    \"name\":\"kim\",\n" +
                        "    \"age\":16,\n" +
                        "    \"hobby\":\"basketball\",\n" +
                        "    \"level\":\"SILVER\"\n" + //formatter??? ????????? ??????????????? ??????????????? ?????????????????????????????? ?????????...
                        "}"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_????????????_ResponseStatusException_contollerAdvice() throws Exception {
        mockMvc.perform(post("/user8").content("{\n" +
                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"1\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadGateway())
                .andExpect(content().json("{\"code\":\"E999\"}"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_produces_text() throws Exception {
        mockMvc.perform(post("/user9").content("{\n" +
                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"1\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"bye\":\"??????\"}"))
                .andDo(print());
    }

    //RequesetBody??? param ????????? ????????? ???????????? ?????? (????????? ??????????????????????????? stirng?????? ????????????) o
        //??????????????? ????????????.. param??? body??? ??????..

    // restful ??????????????? ?????????????????? ????????? ????????? ???????????????..
}
