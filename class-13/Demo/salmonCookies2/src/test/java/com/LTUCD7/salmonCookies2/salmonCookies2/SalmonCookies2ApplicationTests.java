package com.LTUCD7.salmonCookies2.salmonCookies2;

import com.LTUCD7.salmonCookies2.models.SalmonCookiesStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SalmonCookies2ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void Test_salmon_cookies_store_unit_test() {
        SalmonCookiesStore sut = new SalmonCookiesStore("Test Store Name", 10L);

        assertEquals("Test Store Name", sut.getName());
        assertEquals(10, sut.getAverageCookiesPerDay());
    }


    @Test
    void testSalmonStorePostCreation() throws Exception {
        /*
          * mockMvc.perform(...): This line initiates a request to your application using Spring's MockMvc framework.
            In this case, it's simulating a POST request to the root URL ("/") of your application.

          * post("/"): Specifies that it's a POST request to the root URL ("/") of your application.
            This is where the form for creating a new salmon cookie store is likely located.

          * .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE): Sets the content type of the request to
            "application/x-www-form-urlencoded." This is a common content type for form submissions.

          * .param("name", "Zork's"): Adds a parameter named "name" with the value "Zork's" to the request.
            This simulates the user entering "Zork's" as the name of the new salmon cookie store.

          * .param("averageCookiesPerDay", String.valueOf(200)): Adds another parameter named "averageCookiesPerDay"
            with the value 200 to the request. This simulates the user entering 200 as the average cookies per day for
            the new store.

          * .andExpect(redirectedUrl("/")): After performing the request, this line expects that the response will have
            a redirect URL of "/". In other words, it's checking if, after submitting the form, the user is
            redirected back to the root URL.

          * .andExpect(status().isFound()): Finally, this line expects that the HTTP status code of the response is
            "302 Found." This is a common HTTP status code used for redirections.
         */
        mockMvc.perform(
                        post("/")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .param("name", "Zork's")
                                .param("averageCookiesPerDay", String.valueOf(200))
                )
                .andExpect(redirectedUrl("/"))
                .andExpect(status().isFound());
    }

    @Test
    void testHomePage() throws Exception {
        /*
          * mockMvc.perform(MockMvcRequestBuilders.get("/")): This line initiates a GET request to the root URL ("/") of
            your application using Spring's MockMvc framework. It simulates a user accessing the homepage.

          * .andExpect(MockMvcResultMatchers.status().isOk()): After performing the GET request, this line expects that
            the HTTP status code of the response is "200 OK." This status code indicates a successful response,
            meaning that the homepage was accessed without errors.

          * .andExpect(MockMvcResultMatchers.content().string(containsString
            ("<form action=\"/add-employee\" method=\"post\">"))): This line checks the content of the response.
            It verifies that the response body contains a <form> element with the specified action and method attributes.
            In this case, it's looking for a form with action="/add-employee" and method="post".
            This ensures that the homepage contains a form element for adding employees.

          * .andExpect(MockMvcResultMatchers.content().string(containsString("<h1>Salmon Cookie Store Manager</h1>"))):
            Similarly, this line checks the content of the response and verifies that it contains an <h1> element with
            the text "Salmon Cookie Store Manager." This ensures that the homepage contains the expected title or header.
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print()) // Print the response content to the console for debugging
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("<form action=\"/add-employee\" method=\"post\">")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("<h1>Salmon Cookie Store Manager</h1>")));
    }


}
