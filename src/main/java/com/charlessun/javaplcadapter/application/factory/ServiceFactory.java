package com.charlessun.javaplcadapter.application.factory;

import com.charlessun.javaplcadapter.application.service.PlcDataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public ServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 透過服務名稱取得對應的服務
     */
    public PlcDataProcessingService getService(String serviceName) {
        return (PlcDataProcessingService) applicationContext.getBean(serviceName);
    }
}
