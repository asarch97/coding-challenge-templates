package io.lightfeather.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import org.json.*;

@SpringBootApplication
@RestController
@CrossOrigin
public class Application {

  @RequestMapping("/")
  public String home() {
    return "Hello World";
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @RequestMapping("/api/supervisors")
  public String getSupervisors()
  {
    URL                  url = null;
    URLConnection        connection = null;
    StringBuffer         response = new StringBuffer();
    OutputStreamWriter   writer = null;
    BufferedReader       reader = null;
    String               line = "";
    JSONArray            supvArr = null;
    JSONObject           mgr = null;
    List<String>         mgrList = new ArrayList<String>();
    String[]             mgrArr = null;
    String[]             mgrRec = null;
    String               jurisdiction = "";
    int                  i = 0;

    try
    {
      url = new URL("https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/managers");
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoOutput(true);

      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      while((line = reader.readLine()) != null)
        response.append(line);
      reader.close();

      supvArr = new JSONArray(response.toString());
      for(i=0; i<supvArr.length(); i++)
      {
        mgr = supvArr.getJSONObject(i);
        jurisdiction = mgr.getString("jurisdiction");
        if (!Character.isDigit(jurisdiction.charAt(0)))
          mgrList.add(jurisdiction + "_" + mgr.getString("lastName") + "_" + mgr.getString("firstName"));
      }
      mgrArr = mgrList.toArray(new String[mgrList.size()]);
      Arrays.sort(mgrArr);

      supvArr = new JSONArray();
      for(i=0; i<mgrArr.length; i++)
      {
        mgr = new JSONObject();
        mgrRec = mgrArr[i].split("_");
        mgr.put("jurisdiction", mgrRec[0]);
        mgr.put("lastName", mgrRec[1]);
        mgr.put("firstName", mgrRec[2]);
        supvArr.put(mgr);
      }

    }
    catch (Exception e)
    {
      //response.append(e.toString());
      System.out.println("getSupervisors: " + e.toString());
      //e.printStackTrace();
      return e.toString();
    }

    //return response.toString();
    return supvArr.toString();
  }


  @RequestMapping(value="/api/submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Employee> newEmployee(@RequestBody Employee emp)
  {
    try
    {
      if ((emp.getFirstName()).equals("") || (emp.getLastName()).equals("") || (emp.getSupervisor()).equals(""))
        return new ResponseEntity<Employee>(emp, HttpStatus.BAD_REQUEST);
      else
        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
    }
    catch (Exception e)
    {
      System.out.println("newEmployee: " + e.toString());
      return new ResponseEntity<Employee>(emp, HttpStatus.BAD_REQUEST);
    }
  }

}
