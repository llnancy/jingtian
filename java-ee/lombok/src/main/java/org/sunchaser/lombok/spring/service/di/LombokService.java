package org.sunchaser.lombok.spring.service.di;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor = @________________________(@Autowired))
public class LombokService {
    private final IAService iaService;
    private final IBService ibService;
    private final ICService icService;
    private final AMapper aMapper;
    private final BMapper bMapper;

    // do business
}
