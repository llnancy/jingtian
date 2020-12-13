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
public class AutowiredService {
    @Autowired
    private IAService iaService;
    @Autowired
    private IBService ibService;
    @Autowired
    private ICService icService;
    @Autowired
    private AMapper aMapper;
    @Autowired
    private BMapper bMapper;

    // do business
}
