package com.taobao.zeus.web;

import com.taobao.zeus.web.platform.client.util.GwtException;
import com.taobao.zeus.web.platform.shared.rpc.JobService;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JobManualRecoverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static Logger log= LogManager.getLogger(JobManualRecoverServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jobId = req.getParameter("jobID");

        Map<String,Object> res = new HashMap();
        if(jobId==null){
            log.error("未提供jobID参数！");
            res.put("code",-1);
            res.put("msg","你必须提供jobID参数！");
        }else{
            //恢复job
            WebApplicationContext webApplicationContext = getWebContext();
            JobService jobService = (JobService) webApplicationContext.getBean("job.rpc");
            try {
                jobService.run(jobId,2);
                res.put("code",0);
                res.put("msg","任务已经重新启动！");
            } catch (GwtException e) {
                log.error(e.getMessage());
                res.put("code",-2);
                res.put("msg","任务启动发生异常#"+e.getMessage());
            }
        }
        String outMsg = JSONObject.fromObject(res).toString();
        resp.getWriter().write(outMsg);
    }

    private WebApplicationContext getWebContext(){
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }

}