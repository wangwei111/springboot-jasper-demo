/*********************************************
 *
 * Copyright (C) 2019 IBM All rights reserved.
 *
 ********* K*I*N*G ********** B*A*C*K *******/
package mosesboot.jasperreport.controller;
/**
 * @author wangwei
 * @Date 2020/1/20201214
 *  基于javabean数据源
 */

import mosesboot.jasperreport.util.DocType;
import mosesboot.jasperreport.util.JavaBeanJasperHelper;
import mosesboot.user.entity.ModelTableSource;
import mosesboot.user.entity.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javabean DataSet生成Table
 *
 */

@Controller
public class JavaBeanReportController {

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/bean/report")
    public String report() {
        return "html/report";
    }
    /**
     * 生成报表
     *
     * @param reportName 报表名称
     * @param whereSql   查询条件
     * @param inline     是否在线展示
     * @param type       文件类型
     * @param parameters 参数
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     */
    @GetMapping("/jasper/bean/{reportName}")
    public void getReportByParam(           @PathVariable("reportName") final String reportName,
            @RequestParam(required = false) String whereSql,
            @RequestParam(required = false, defaultValue = "false") String inline,
            @RequestParam(required = false, defaultValue = "pdf") String type,
            @RequestParam(required = false) Map<String, Object> parameters,
            HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException, JRException {
        parameters = parameters == null ? new HashMap<>() : parameters;
        ClassPathResource resource = new ClassPathResource("jaspers/"+  reportName + ".jasper");
        String path = this.getClass().getClassLoader().getResource("jaspers/" + reportName + ".jasper").getPath();
        String path1 = this.getClass().getClassLoader().getResource("jaspers/" + reportName + ".jrxml").getPath();

        JasperCompileManager.compileReportToFile(path1,path);
        List<User> list = new ArrayList<>();
        User user =new User();
        user.setAge(1);
        user.setEmail("aaaa");
        user.setId(1L);
        user.setName("ceshi");

        User user1 =new User();
        user1.setAge(2);
        user1.setEmail("bbb");
        user1.setId(2L);
        user1.setName("ceshi2");
        list.add(user);
        list.add(user1);

        ModelTableSource mts = new ModelTableSource();
        mts.setDataTable(new JRBeanCollectionDataSource(list));
        List<ModelTableSource> mlist = new ArrayList<ModelTableSource>();
        mlist.add(mts);
        //new JRBeanCollectionDataSource(list)  以javaBean为数据源注入报表数据
        InputStream jasperStream = resource.getInputStream();
        // parameters.put("whereSql", whereSql);
        JavaBeanJasperHelper.export(type, reportName, jasperStream, inline, request, response, parameters,  new JRBeanCollectionDataSource(mlist));
    }
}