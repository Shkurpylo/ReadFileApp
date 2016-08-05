package com.globallogic.servlet;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name ="ReadFileServlet")
public class ReadFileServlet extends HttpServlet {

    private static final Path FILE_PATH;
    static {
        try {
            URI uri = ReadFileServlet.class.getResource("/file.txt").toURI();
            FILE_PATH = Paths.get(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Text file is not found!");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reading queries
        String query = request.getParameter("q");
        boolean includeMetaData = Boolean.parseBoolean(request.getParameter("includeMetaData"));
        int limit = 10000;
        int length = Integer.MAX_VALUE;

        try {
            limit = Integer.parseInt(request.getParameter("limit"));
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.toString());
        }
        try {
            length = Integer.parseInt(request.getParameter("length"));
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.toString());
        }

        Map jsonResult = search(response, query, limit, length, includeMetaData);
        jsonResponse(response, jsonResult);

    }

    private static Map<String, Object> search(HttpServletResponse response, String query, int limit, int length, boolean includeMetaData){
        // Creating object for collecting results
        Map<String, Object> json = new HashMap<>();
        // Matching text to queries
        try {
            if (query.equals("")) {
                String string = new String(Files.readAllBytes(FILE_PATH), StandardCharsets.UTF_8);
                json.put("text", string);
            } else {
                List<Object> matchesStrings = new ArrayList<>();
                BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH.toString()));
                String currentString;
                int charsCounting = 0;
                while ((currentString = reader.readLine()) != null) {
                    if (currentString.contains(query) && currentString.length() <= length) {
                        if ((charsCounting + currentString.length()) <= limit) {
                            matchesStrings.add(currentString);
                            charsCounting += currentString.length();
                            if((charsCounting + query.length()) > limit){  //If the amount of characters in already
                                break;                                      //found strings is bigger than the amount of
                            }                                               //query length and limit then the loop breaks
                        }
                    }
                }
                json.put("text", matchesStrings);
            }
            // this block will be execute only if the user chooses includeMetaData
            if (includeMetaData) {
                BasicFileAttributes attributes = Files.readAttributes(FILE_PATH, BasicFileAttributes.class);
                Map<String, String> attributesMap = new HashMap<>();
                attributesMap.put("fileName", FILE_PATH.getFileName().toString());
                attributesMap.put("fileSize", attributes.size() + "KB");
                attributesMap.put("fileCreationDate", attributes.creationTime().toString());
                json.put("metaData", attributesMap);
            }
        } catch (IOException ioException){
            System.out.println(ioException.toString());
        }

        return json;
    }

    private static void jsonResponse(HttpServletResponse response, Map result){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        try {
            response.getWriter().append(mapper.writeValueAsString(result));
        } catch (IOException ioException) {
            System.out.println(ioException.toString());
        }
    }
}
