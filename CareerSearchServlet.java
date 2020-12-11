

package com.adobe.aem.guides.wknd.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

@Component(service=Servlet.class,
           property={
                   Constants.SERVICE_DESCRIPTION + "=Search Servlet",
                   "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                   "sling.servlet.paths="+ "/bin/careers/search"

           })
public class CareerSearchServlet extends SlingSafeMethodsServlet {



    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {

        String country= req.getParameter("country");
        String skills= req.getParameter("skills");
        String apiUrl="";
        String authenticationStr="";
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try{
            URL url=new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", "Basic " + authenticationStr);
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            resp.getWriter().write(stringBuilder.toString());

        }catch (Exception e){
            resp.getWriter().write("Exception occured:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
