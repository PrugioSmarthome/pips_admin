package com.daewooenc.pips.admin.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("readFile")
public class ReadFile {

    private static String[] args;

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {

        Connection con = null;
        PreparedStatement stmt =null;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://172.20.0.78:3306/pips3?useUniCode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true", "pipsuser", "!DlaTl#12");

        StringBuffer query = new StringBuffer();

        query.append("INSERT INTO accesslog VALUES (STR_TO_DATE(?, '%Y-%m-%d %H:%i:%S.%fff'),?,?,?,?,?,?,?,?)");
        stmt = con.prepareStatement(query.toString());

        BufferedReader reader = new BufferedReader(new FileReader("C:\\access_log.2021-10-05"));
        String str;
        int j = 0;
        int cnt = 1;

        con.setAutoCommit(false);

        while ((str = reader.readLine()) != null) {
            cnt++;
            if(cnt < 12953265){
                continue;
            }

            String[] file = str.split(" ");



                String datetime = file[0] + " " +file[1];


                stmt.setString(1, datetime);
                stmt.setString(2, file[0]);
                stmt.setString(3, file[1]);

                String method = file[2];
                method = method.replaceAll("\\[", "");
                method = method.replaceAll("\\]", "");


                stmt.setString(4, method);

                if(file[3].equals("-")){
                    stmt.setNull(5, Types.INTEGER);
                }else {
                    stmt.setInt(5, Integer.parseInt(file[3]));
                }

                stmt.setString(6, file[4]);
                stmt.setString(7, file[5]);
                stmt.setString(8, file[6]);


                int desidx = str.lastIndexOf(file[7]);
                String description = str.substring(desidx);


                int idx = description.indexOf("(");
                if(idx != -1){
                    String description2 = description.substring(0, idx-1);
                    stmt.setString(9, description2);
                }else {
                    stmt.setString(9, description);
                }

                stmt.addBatch();
                stmt.clearParameters();

                if(j == 10000){
                    stmt.executeBatch();
                    stmt.clearBatch();
                    con.commit();
                    j = 0;
                }

                j++;

        }
        stmt.executeBatch();
        con.commit();
        reader.close();

    }
// 괄호 제거. 날찌,시간 스트링값 컬럼 넣기


}
