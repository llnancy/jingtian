package org.sunchaser.lombok.spring.service.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunchaser.lombok.spring.mapper.AMapper;
import org.sunchaser.lombok.spring.mapper.BMapper;
import org.sunchaser.lombok.spring.service.IAService;
import org.sunchaser.lombok.spring.service.IBService;
import org.sunchaser.lombok.spring.service.ICService;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2020/12/13
 */
@Service
public class ConstructorService {
    private final IAService iaService;
    private final IBService ibService;
    private final ICService icService;
    private final AMapper aMapper;
    private final BMapper bMapper;

    @Autowired
    public ConstructorService(IAService iaService, IBService ibService, ICService icService, AMapper aMapper, BMapper bMapper) {
        this.iaService = iaService;
        this.ibService = ibService;
        this.icService = icService;
        this.aMapper = aMapper;
        this.bMapper = bMapper;
    }

    // do business
}
