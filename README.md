# Project Name

The FileReadApp is a web aplication, with the simple interface, which allows to query and filter data from text file over web.

To create the FileReadApp I used the next technologies:
- Java 7;
- Servlets 3.1;
- Apatche Maven 3.0.5

deployed and tested on 
- Apache Tomcat 8.0.36

Project contains:
ReadFileApp.war - Maven project package
ReadFileApp.zip - Sourse files package
READ.ME - Documentation: Description the app, Instruction how to use.
file.txt - test file (contains 50000 different strings), which allows to test all features. Original test file has already been included in ReadFileApp.war, and this one in base directory is just for example.

## Installation

To run this App you need:
- Java 7;
- Apache Tomcat 8 or 7

Steps:
1) Run Apache Tomcat server on your system;
2) Go to Tomcat Web Application Manager -> War file to deploy and choose a ReadFileApp.war;
3) Then select it in "Aplication" table on Tomcat Web Application Manager page;

## Usage

If your system has correct configuration and you did everything right, you should see the GUI of FileReadApp, which contains:
1)Input field "Input search word" - input string which represents text to search in file. API will return all
strings which equals to imputed string or containing it. If imputed string is blank or missing - API return all text from file;

2)Input field "Limit" -imput integer which represents max number of chars in text that API should return. If parameter is blank or
 missing API will return max 10000 chars.

3)Input field "Max string length" - imput integer which represents max string length. API will return string which doesn’t exceed
 that number or if there is no such strings empty response. If parameter is blank or missing API will ignore it.
4)Checkbox "includeMetaData" - select it and API will return meta data of the file.
5)Button "Submit" - submit queries to server.

If you don't input query in field "Input search word" API will return all text in JSON format, ignored other input fields.
Checkbox will never be ignored;

## License

Done by Anatolii Shkurpylo
anatolii.shkurpylo@gmail.com
