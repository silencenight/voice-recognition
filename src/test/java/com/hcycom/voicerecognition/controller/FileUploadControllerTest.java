package com.hcycom.voicerecognition.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    /**
     * 定制文档中出现的 REST API 的 HTTP Scheme、主机名和端口
     */
    @Before
    public void setUp() {
        //默认生成的文档片段
        Snippet[] defaultSnippets = new Snippet[]{CliDocumentation.curlRequest(), CliDocumentation.httpieRequest(), HttpDocumentation.httpRequest(), HttpDocumentation.httpResponse(), PayloadDocumentation.requestBody(), PayloadDocumentation.responseBody()};
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation)
                        //此处也支持生成markdown文档片段，但不能生成html
                        .snippets().withTemplateFormat(TemplateFormats.asciidoctor()).withEncoding("UTF-8")
                        .withDefaults(defaultSnippets)
                        .and()
                        .uris().withScheme("http").withHost("101.201.150.7").withPort(8010)
                        .and()
                )
                .build();
    }

    @Test
    public void testTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/speech/test").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("speech-test"));
    }

    @Test
    public void testUpload() throws Exception{
        File file = new File("E:/16k.pcm");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "16k.pcm",
                MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/speech/upload")
                .file(multipartFile)
                .param("name","zlg123"))
                .andExpect(status().isOk())
                .andDo(document("speech-upload"));
    }

    @Test
    public void uploads() {
    }
}