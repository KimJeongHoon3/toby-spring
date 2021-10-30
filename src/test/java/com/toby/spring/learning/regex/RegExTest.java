package com.toby.spring.learning.regex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class RegExTest {
    @Test
    void testRegEx(){
        String regEx="(?:https?:\\/\\/)?(?:www\\.)?youtu.be\\/([-\\w]+)";

        Pattern compile = Pattern.compile(regEx);

        String target1="https://www.youtu.be/-ZClicWm0zM";
        String target2="https://youtu.be/-ZClicWm0zM";
        String target3="youtu.be/-ZClicWm0zM";
        String multi="https://www.youtu.be/-ZClicWm0zM https://youtu.be/-sdfzM youtu.be/-ccccM";

        Matcher matcher = compile.matcher(multi);
        assertTrue(matcher.lookingAt()); //matcher.lookingAt() => "^[Pattern]" , 앞에가 일치해야하기 때문에 하나만나옴!
        System.out.println(matcher.group(1));

        Matcher matcher2 = compile.matcher(multi);
        while(matcher2.find()){ //matcher.find() => "[Pattern]" , 이렇기때문에 find는 패턴이 일치하는게 여러개 나올수있음! 그래서 find 호출하면 다음 일치하는걸로 넘어간다!
            System.out.println(matcher2.group(1));
        }

        Matcher matcher3 = compile.matcher(target1);
        assertFalse(matcher3.matches());//matcher.matches() => "^[Pattern]$" , 앞에서 뒤까지 모두 일치해야하니깐 하나밖에 안나온다고 생각해서 그런지 다음 일치하는걸로 넘어가는 find와 같이 while문 돌리면 무한루프..(위 lookingAt도 마찬가지)
    }

    @Test
    void test2(){
        String stringToSearch = "Four score and seven years ago our fathers ...";

        Pattern p = Pattern.compile(" (\\S+or\\S+) ");   // the pattern to search for
        Matcher m = p.matcher(stringToSearch);

        // if we find a match, get the group
        if (m.find())
        {
            // we're only looking for one group, so get it
            String theGroup = m.group(1);

            // print the group out for verification
            System.out.format("'%s'\n", theGroup);
        }
    }
}
