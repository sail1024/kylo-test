package com.stella.test.jcr.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * log controller
 *
 * @author sail
 * @date 15:11 2019-11-11.
 * @since 1.0
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/add")
    public Log addLog(Log log){
        logService.addLog(log);
        return log;
    }

    @GetMapping("/get")
    public Log getLog(Long id){
        return logService.getLog(id);
    }

    @GetMapping("/query")
    public List<Log> queryLog(Log log){
        return logService.queryLog(log);
    }
}
