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
                .param("level","가나다"))
                .andExpect(status().isOk()) //hnadlerException 확인차 때문에 정상 리턴으로 바꾼거
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","level"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_validation_error_noBindingResult_handlerException() throws Exception {
        //bindingResult로 잡아주지않으면 BindException 떨어지게됨.. 현재는 BindExcepction을 @ExceptionHandler가 잡아주어서 처리하도록해놧
        mockMvc.perform(post("/user3").param("name","kim")
                .param("age","16")
                .param("hobby","basketball")
                .param("level","가나다"))
                .andExpect(status().isOk()) //hnadlerException 확인차 때문에 정상 리턴으로 바꾼거
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
    void testRegisterUser_requestBody_검증시에러내용BindingResult에들어감_Model에데이터등록확_당연안들어감() throws Exception {
        mockMvc.perform(post("/user5").content("{\n" +
//                "    \"name\":\"kim\",\n" +
                "    \"age\":16,\n" +
                "    \"hobby\":\"basketball\",\n" +
                "    \"level\":\"GOLD\"\n" + //formatter가 변환해준게 아니라, jackson 컨버터가 json 변환시 알아서 enum은 변환해줌., jackson 에서 에러나면 BindingResult에 에러 안담기고 HttpMessageNotReadableException 이거 던짐..
//                "    \"level\":1" + //이렇게 숫자로 넣으면 enum의 값이랑 매칭되지않고 내부적으로등록되어있는 배열 순서랑 매칭됨..
                "}").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("user"))
                .andExpect(view().name("hello"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_param_body_모두받아보기() throws Exception {
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
                "    \"level\":\"가나다\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadGateway()) //Exception
                .andExpect(content().json("{\"code\":\"E999\"}"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_body_validation_정상() throws Exception {
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
                        "    \"level\":\"SILVER\"\n" + //formatter를 통해서 정상적으로 숫자값으로 변경되어야할거같은데 안되네...
                        "}"))
                .andDo(print());
    }

    @Test
    void testRegisterUser_post_예외처리_ResponseStatusException_contollerAdvice() throws Exception {
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
                .andExpect(content().json("{\"bye\":\"안녕\"}"))
                .andDo(print());
    }

    //RequesetBody로 param 받으면 어떻게 보여질지 확인 (배열로 넘어오는거같긴한데 stirng으로 받아보기) o
        //질문자체가 말이안됨.. param과 body는 다름..

    // restful 방식일때는 컨트롤러단을 어떻게 하는게 제일좋을지..
}
